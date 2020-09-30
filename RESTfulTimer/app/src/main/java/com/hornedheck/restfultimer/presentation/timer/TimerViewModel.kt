package com.hornedheck.restfultimer.presentation.timer

import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import com.hornedheck.restfultimer.data.Repository
import com.hornedheck.restfultimer.entities.Response
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.entities.TimerStep
import com.hornedheck.restfultimer.framework.models.StepType
import com.hornedheck.restfultimer.presentation.list.ListViewModel
import com.hornedheck.restfultimer.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TimerViewModel(private val id: Long, repository: Repository) :
    ListViewModel<TimerStep>(repository) {

    private lateinit var timer: Timer

    val titleState = State<Pair<String, Int>>()

    override suspend fun loadData(): Response<List<TimerStep>> {
        val response = repository
            .getTimer(id)
        if (response.isSuccessful) {
            onTimerLoaded(response.data!!)
        }
        return response.map { it.steps!! }
    }

    private fun updateToolbar() {
        titleState.postValue(timer.name to timer.color)
    }

    private fun onTimerLoaded(timer: Timer) {
        this.timer = timer
        updateToolbar()
    }

    fun update() {
        CoroutineScope(Dispatchers.Main).launch {
            timer.duration = calcDuration()
            withContext(Dispatchers.IO) {
                repository.updateTimer(timer)
            }
        }
    }

    val menuVisibility = State(false)

    fun resetState() {
        menuVisibility.postValue(false)
        isChanged.setValue(false)
    }

    fun toggleMenu() {
        menuVisibility.postValue(menuVisibility.value?.not() ?: false)
    }

    fun add(type: StepType, title: String) {
        CoroutineScope(Dispatchers.Main).launch {

            timer.steps ?: return@launch

            menuVisibility.postValue(false)

            when (type) {
                StepType.PREPARE, StepType.SETS, StepType.SETS_REST, StepType.CALM_DOWN -> {
                    if (timer.steps!!.any { it.type == type.ordinal }) {
                        return@launch
                    }
                }
                else -> {
                }
            }
            timer.steps!!.add(TimerStep(null, title, null, type.min, type.ordinal))

            sortSteps()
            items.show(timer.steps!!)
            isChanged.postValue(true)
        }
    }

    private fun sortSteps() {
        timer.steps ?: return
        val timerSteps = timer.steps!!
        val steps = mutableListOf<TimerStep>()
        timerSteps.find { it.type == StepType.PREPARE.ordinal }?.let(steps::add)
        timerSteps.filter {
            it.type == StepType.WORK.ordinal ||
                    it.type == StepType.REST.ordinal ||
                    it.type == StepType.REPEAT.ordinal
        }.let { steps.addAll(it) }
        timerSteps.find { it.type == StepType.SETS.ordinal }?.let(steps::add)
        timerSteps.find { it.type == StepType.SETS_REST.ordinal }?.let(steps::add)
        timerSteps.find { it.type == StepType.CALM_DOWN.ordinal }?.let(steps::add)
        timer.steps!!.clear()
        timer.steps!!.addAll(steps)
        timer.steps!!.forEachIndexed { i, it -> it.position = i }
    }

    private fun calcDuration(): Int {

        if (timer.steps.isNullOrEmpty()) return 0

        val timerSteps = timer.steps!!
        var duration = 0
        var limit = timerSteps.size
        var i = 0
        if (timerSteps.first().type == StepType.PREPARE.ordinal) {
            i += 1
            duration += timerSteps.first().duration
        }
        val steps = timerSteps.findLast { it.type == StepType.SETS.ordinal }
        val stepRest = timerSteps.findLast { it.type == StepType.SETS_REST.ordinal }
        val calmDown = timerSteps.findLast { it.type == StepType.CALM_DOWN.ordinal }
        limit -= listOfNotNull(steps, stepRest, calmDown).size

        var workDuration = 0
        var cycleDuration = 0
        while (i < limit) {
            when (timerSteps[i].type) {
                StepType.REST.ordinal, StepType.WORK.ordinal -> {
                    cycleDuration += timerSteps[i].duration
                }
                StepType.REPEAT.ordinal -> {
                    workDuration += cycleDuration * timerSteps[i].duration
                    cycleDuration = 0
                }
            }
            i += 1
        }
        workDuration += cycleDuration
        val stepCount = steps?.duration ?: 1
        duration += workDuration * stepCount
        stepRest?.let {
            duration += it.duration * (stepCount - 1)
        }
        calmDown?.let { duration += it.duration }
        return duration
    }

    private val _colorPickerEvent = SingleLiveEvent<Int>()
    val colorPickerEvent: LiveData<Int> = _colorPickerEvent

    fun colorClicked() {
        _colorPickerEvent.postValue(timer.color)

    }

    fun changeColor(@ColorInt color: Int) {
        timer.color = color
        isChanged.setValue(true)
        updateToolbar()
    }

    val isChanged = State<Boolean>()

    fun trySetChanged(value: Boolean) {
        isChanged.setValue(isChanged.value ?: false || value)
    }

    private val _updateTitleEvent = SingleLiveEvent<String>()
    val updateTitleEvent: LiveData<String> = _updateTitleEvent

    fun updateTitleClicked() {
        _updateTitleEvent.postValue(timer.name)
    }

    fun updateTitle(title: String) {
        timer.name = title
        isChanged.postValue(true)
        updateToolbar()
    }

    fun removeStep(pos: Int) {
        timer.steps?.removeAt(pos)
        if (timer.steps.isNullOrEmpty()) {
            items.hide()
            isError.setValue(true)
        } else {
            items.show(timer.steps!!)
        }
        isChanged.postValue(true)
    }

    fun moveStep(from: Int, to: Int) {
        timer.steps ?: return
        timer.steps!![from] = timer.steps!![to].also { timer.steps!![to] = timer.steps!![from] }
        timer.steps!![from].position = from
        timer.steps!![to].position = to
        isChanged.postValue(true)
    }

}