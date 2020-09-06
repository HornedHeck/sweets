package com.hornedheck.yankeesphisics.data

import androidx.annotation.StringRes
import com.hornedheck.yankeesphisics.R

enum class ConversionGroup(@StringRes val title: Int) {
    MASS(R.string.group_mass),
    VOLUME(R.string.group_volume),
    DISTANCE(R.string.group_length)
}