package com.hornedheck.restfultimer.presentation.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.databinding.ItemTimerStepBinding
import com.hornedheck.restfultimer.entities.TimerStep
import com.hornedheck.restfultimer.framework.models.StepType
import com.hornedheck.restfultimer.presentation.timer.TimerAdapter.TimerStepViewHolder
import com.hornedheck.restfultimer.utils.BaseAdapter
import com.hornedheck.restfultimer.utils.BaseViewHolder

class TimerAdapter : BaseAdapter<TimerStep, TimerStepViewHolder>() {

    private val _isChanged = MutableLiveData<Boolean>()
    val isChanged: LiveData<Boolean> = _isChanged

    fun onStop() {
        _isChanged.postValue(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerStepViewHolder {
        val binding = DataBindingUtil.inflate<ItemTimerStepBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_timer_step,
            parent,
            false
        )
        return TimerStepViewHolder(binding)
    }

    fun moveItem(from: Int, to: Int) {
        _items[from] = _items[to].also { _items[to] = _items[from] }
        notifyItemMoved(from, to)
    }

    inner class TimerStepViewHolder(binding: ItemTimerStepBinding) :
        BaseViewHolder<TimerStep>(binding.root) {

        private val icon: ImageView = binding.ivIcon
        private val title = binding.tvTitle
        private val duration = binding.etDuration
        private val add = binding.ibAdd
        private val sub = binding.ibSub
        lateinit var type: StepType
            private set

        override fun bind(item: TimerStep) {
            type = StepType.values()[item.type]
            icon.setImageResource(type.icon)
            title.text = item.name
            duration.setText(item.duration.toString())
            add.setOnClickListener { incDuration(item) }
            sub.setOnClickListener { decDuration(item, type) }
        }

        private fun incDuration(item: TimerStep) {
            item.duration += 1
            duration.setText(item.duration.toString())
            _isChanged.postValue(true)
        }

        private fun decDuration(item: TimerStep, type: StepType) {
            if (item.duration > type.min) {
                item.duration -= 1
                duration.setText(item.duration.toString())
                _isChanged.postValue(true)
            }
        }

    }

}