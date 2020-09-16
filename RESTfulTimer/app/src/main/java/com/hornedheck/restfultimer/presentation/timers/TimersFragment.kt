package com.hornedheck.restfultimer.presentation.timers

import android.os.Bundle
import android.view.View
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.presentation.list.ListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimersFragment : ListFragment<Timer>() {

    override val viewModel by viewModel<TimersViewModel>()

    private val adapter = TimersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
    }

    override fun submitData(data: List<Timer>) {
        adapter.items = data
    }

    override fun initObservers() {
        super.initObservers()
        adapter.deleteClicked.observe(this) { viewModel.deleteClicked(it, adapter.items) }
        adapter.editClicked.observe(this) {
//            childFragmentManager
//                .beginTransaction()
//                .add()
//                .commit()
        }
    }
}