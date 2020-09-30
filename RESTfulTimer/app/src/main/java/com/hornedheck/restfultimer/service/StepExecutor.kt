package com.hornedheck.restfultimer.service

import com.hornedheck.restfultimer.framework.models.StepType
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object StepsExecutor {

    private var steps: List<Step>? = null

    var notifyStep: ((Int, Step) -> Unit)? = null
    var notifyFinished: (() -> Unit)? = null
    var notifyStepFinished: (() -> Unit)? = null

    fun start(steps: List<Step>) {
        this.steps = steps
        stopJob = CoroutineScope(Dispatchers.Default).launch { action() }
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

    private var stopJob: Job? = null
    fun stop(withNotify: Boolean = true) {
        stopJob?.cancel()
        skipJob?.cancel()
        if (withNotify) {
            notifyFinished?.invoke()
        }
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
                @Suppress("NON_EXHAUSTIVE_WHEN")
                when (step?.type) {
                    StepType.WORK, StepType.REST -> {
                        proceedStep(step)
                    }
                    StepType.REPEAT -> {
                        if (repeats < step.duration - 1) {
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
        notifyFinished?.invoke()
    }

    private suspend fun proceedStep(step: Step) {
        skipJob = CoroutineScope(Dispatchers.Default).launch {
            var i = 0
            while (step.duration > i) {
                withContext(Dispatchers.Main) {
                    mutex.withLock { notifyStep?.invoke(i, step) }
                }
                repeat(1) { mutex.withLock { delay(1000L) } }
                i += 1
            }
        }
        skipJob?.invokeOnCompletion {
            if (it == null) {
                notifyStepFinished?.invoke()
            }
        }
        skipJob?.join()
    }


}