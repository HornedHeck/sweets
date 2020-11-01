package com.hornedheck.echos.data

import com.hornedheck.echos.domain.models.Action
import com.hornedheck.echos.domain.models.ActionType
import com.hornedheck.firerx3.ChildAction
import com.hornedheck.firerx3.ChildAdd
import com.hornedheck.firerx3.ChildDelete
import com.hornedheck.firerx3.ChildUpdate

fun <T, R> ChildAction<T>.mapToAction(block: T.() -> R): Action<R> =
    when (this) {
        is ChildAdd<T> -> Action(data.block(), ActionType.INSERT)
        is ChildDelete<T> -> Action(data.block(), ActionType.DELETE)
        is ChildUpdate<T> -> Action(data.block(), ActionType.UPDATE)
    }