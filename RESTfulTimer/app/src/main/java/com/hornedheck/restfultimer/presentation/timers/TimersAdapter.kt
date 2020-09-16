package com.hornedheck.restfultimer.presentation.timers

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.databinding.ItemTimerBinding
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.presentation.timers.TimersAdapter.TimersViewHolder
import com.hornedheck.restfultimer.utils.BaseAdapter
import com.hornedheck.restfultimer.utils.BaseViewHolder
import com.hornedheck.restfultimer.utils.SingleLiveEvent

class TimersAdapter : BaseAdapter<Timer, TimersViewHolder>() {

    private val _deleteClicked = SingleLiveEvent<Int>()
    val deleteClicked: LiveData<Int> = _deleteClicked

    private val _editClicked = SingleLiveEvent<Int>()
    val editClicked: LiveData<Int> = _editClicked

    private val _runClicked = SingleLiveEvent<Int>()
    val runClicked: LiveData<Int> = _runClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimersViewHolder {
        return TimersViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_timer,
                parent,
                false
            )
        )
    }

    inner class TimersViewHolder(binding: ItemTimerBinding) : BaseViewHolder<Timer>(binding.root) {

        private val model = TimerModel()

        private val delete = itemView.findViewById<View>(R.id.ibDelete)
        private val edit = itemView.findViewById<View>(R.id.ibEdit)
        private val run = itemView.findViewById<View>(R.id.ibRun)

        init {
            binding.model = model
        }

        override fun bind(item: Timer) {
            delete.setOnClickListener { _deleteClicked.postValue(item.id) }
            edit.setOnClickListener { _editClicked.postValue(item.id) }
            run.setOnClickListener { _runClicked.postValue(item.id) }

            model.title.set(item.name)
            model.color.set(item.color)
            model.textColor.set(getTextColor(item.color))
            model.duration.set(formatDuration(itemView.context, item.duration))
        }
    }
}

class TimerModel {

    val title = ObservableField<CharSequence>()
    val color = ObservableInt()
    val textColor = ObservableInt()
    val duration = ObservableField<CharSequence>()

}

private fun formatDuration(context: Context, duration: Int): CharSequence {
    val res = StringBuilder()
    (duration / 1440).let { days ->
        if (days > 0) {
            res.append(context.getString(R.string.duration_days, days))
            res.append(" ")
        }
    }
    ((duration % 1440) / 60).let { hours ->
        if (hours > 0) {
            res.append(context.getString(R.string.duration_hours, hours))
            res.append(" ")
        }
    }
    (duration % 60).let { minutes ->
        if (minutes > 0) {
            res.append(context.getString(R.string.duration_minutes, minutes))
            res.append(" ")
        }
    }
    return res.trim()
}

private fun getTextColor(src: Int): Int {
    return if (listOf(Color.red(src), Color.green(src), Color.blue(src)).any { it < 127 }) {
        Color.WHITE
    } else {
        Color.BLACK
    }

}