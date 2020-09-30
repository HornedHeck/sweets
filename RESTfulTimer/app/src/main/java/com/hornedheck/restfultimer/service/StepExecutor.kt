package com.hornedheck.restfultimer.service

import android.util.Log
import com.hornedheck.restfultimer.framework.models.StepType
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object StepsExecutor {

    private var steps: List<Step>? = null

    var notifyStep: ((Int, Step) -> Unit)? = null
    var notifyFinished: (() -> Unit)? = null

    fun start(steps: List<Step>) {
        this.steps = steps
        CoroutineScope(Dispatchers.Default).launch {
            action()
        }
    }

    private val mutex = Mutex(false)
    fun pause() {
        CoroutineScope(Dispatchers.Default).launch {
            mutex.lock()
        }
    }

    fun resume() {
        mutex.unlock()
    }

    private var skipJob: Job? = null
    fun skip() {
        skipJob?.cancel()
    }

    private suspend fun action() {

        val setRest = steps?.firstOrNull { it.type == StepType.SETS_REST }
        val sets = steps?.firstOrNull { it.type == StepType.SETS }?.duration ?: 1

        val filteredSteps = steps?.filter {
            it.type == StepType.WORK
                    || it.type == StepType.REST
                    || it.type == StepType.REPEAT
        }

        steps?.firstOrNull { it.type == StepType.PREPARE }?.let { proceedStep(it) }
        var allRepeats = 0
        while (allRepeats < sets) {
            var repeats = 0
            var pivot = 0
            var i = 0
            while (i < filteredSteps?.size ?: 0) {
                val step = filteredSteps?.get(i)
                when (step?.type) {
                    StepType.WORK, StepType.REST -> {
                        proceedStep(step)
                    }
                    StepType.REPEAT -> {
                        if (repeats < step.duration) {
                            i = pivot
                            repeats += 1
                            continue
                        } else {
                            pivot = i + 1
                            repeats = 0
                        }
                    }
                }
                i += 1
            }
            if (allRepeats < sets - 1) {
                setRest?.let { proceedStep(it) }
            }

            allRepeats += 1
        }
        steps?.firstOrNull { it.type == StepType.CALM_DOWN }?.let { proceedStep(it) }

    }

    private suspend fun proceedStep(step: Step) {
        skipJob = CoroutineScope(Dispatchers.Default).launch {
            var i = 0
            while (step.duration > i) {
                mutex.withLock {
                    withContext(Dispatchers.Main) {
                        notifyStep?.invoke(i, step)
                        Log.e("NOTIFICATION", "${step.name} $i/${step.duration}")
                    }
                    delay(1000L)
                    i += 1
                }
            }
        }

        skipJob?.join()
        Log.e("STEP", "Step ${step.position}. ${step.name} finished or skipped")
    }


}