package com.hornedheck.restfultimer.service

import com.hornedheck.restfultimer.framework.models.StepType

data class Step(
    val name: String,
    var description: String?,
    var duration: Int,
    val type: StepType,
    var position: Int = 0
)
