package com.hornedheck.restfultimer.framework.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hornedheck.restfultimer.R

enum class StepType(
    @DrawableRes
    val icon: Int,
    @StringRes
    val title: Int,
    val min: Int = 0
) {
    PREPARE(R.drawable.ic_prepare, R.string.step_prepare),
    WORK(R.drawable.ic_work, R.string.step_work, 1),
    REST(R.drawable.ic_rest_intermediate, R.string.step_rest_intermediate),
    REPEAT(R.drawable.ic_repeats, R.string.step_repeat, 1),
    SETS(R.drawable.ic_all_repeats, R.string.step_sets, 1),
    SETS_REST(R.drawable.ic_rest_set, R.string.step_rest_set),
    CALM_DOWN(R.drawable.ic_rest_final, R.string.step_rest_final)
}