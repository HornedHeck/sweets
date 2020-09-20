package com.hornedheck.restfultimer.presentation.timers

import android.os.Bundle
import android.view.View
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.presentation.list.ListFragment
import com.hornedheck.restfultimer.presentation.timer.TimerFragment
import kotlinx.android.synthetic.main.fragment_timers.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimersFragment : ListFragment<Timer>() {

    override val viewModel by viewModel<TimersViewModel>()

    override val layoutRes = R.layout.fragment_timers

    private val adapter = TimersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
    }

    override fun submitData(data: List<Timer>) {
        adapter.items = data
    }

    override fun initUI() {
        super.initUI()
        fabAdd.setOnClickListener { viewModel.addClicked() }
    }

    override fun initObservers() {
        super.initObservers()
        adapter.deleteClicked.observe(this) { viewModel.deleteClicked(it, adapter.items) }
        adapter.editClicked.observe(this) {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, TimerFragment(it))
                .addToBackStack(null)
                .commit()
        }
    }
}