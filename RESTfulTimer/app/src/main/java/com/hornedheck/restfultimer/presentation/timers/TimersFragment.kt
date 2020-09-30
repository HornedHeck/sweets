package com.hornedheck.restfultimer.presentation.timers

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.presentation.list.ListFragment
import com.hornedheck.restfultimer.presentation.settings.SettingsFragment
import com.hornedheck.restfultimer.presentation.timer.TimerFragment
import com.hornedheck.restfultimer.service.TimerService
import kotlinx.android.synthetic.main.fragment_timers.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimersFragment : ListFragment<Timer>() {

    override val viewModel by viewModel<TimersViewModel>()

    override val layoutRes = R.layout.fragment_timers

    private val adapter = TimersAdapter()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, SettingsFragment())
                .addToBackStack(null)
                .commit()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
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
        adapter.runClicked.observe(this) {
            viewModel.runClicked(it)
        }
        viewModel.schedule.observe(this) {
            requireActivity().startService(TimerService.getStartIntent(requireContext(), it.id))
        }
    }
}