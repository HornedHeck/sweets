package com.hornedheck.restfultimer.presentation.timer

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.restfultimer.framework.models.StepType
import com.hornedheck.restfultimer.utils.SingleLiveEvent


class TimerTouchCallback : ItemTouchHelper.Callback() {

    private val _itemSwiped = SingleLiveEvent<Int>()
    val itemSwiped: LiveData<Int> = _itemSwiped

    /**First - src , second - destination*/
    private val _onItemMove = SingleLiveEvent<Pair<Int, Int>>()
    val onItemMove: LiveData<Pair<Int, Int>> = _onItemMove

    companion object {
        private const val swipeDirections = ItemTouchHelper.START or ItemTouchHelper.END
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragDirections = when ((viewHolder as TimerAdapter.TimerStepViewHolder).type) {
            StepType.REPEAT, StepType.WORK, StepType.REST -> {
                ItemTouchHelper.UP or ItemTouchHelper.DOWN
            }
            else -> {
                0
            }
        }
        return makeMovementFlags(dragDirections, swipeDirections)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val targetType = (target as TimerAdapter.TimerStepViewHolder).type
        val res =
            targetType == StepType.REPEAT || targetType == StepType.REST || targetType == StepType.WORK
        if (res) {
            _onItemMove.postValue(viewHolder.adapterPosition to target.adapterPosition)
        }
        return res
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        _itemSwiped.postValue(viewHolder.adapterPosition)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

}
