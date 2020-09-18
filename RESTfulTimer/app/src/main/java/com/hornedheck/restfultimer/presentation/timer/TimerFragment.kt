package com.hornedheck.restfultimer.presentation.timer

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.entities.TimerStep
import com.hornedheck.restfultimer.presentation.list.ListFragment
import com.hornedheck.restfultimer.utils.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TimerFragment(id: Int) : ListFragment<TimerStep>() {

    override val viewModel: TimerViewModel by viewModel { parametersOf(id) }

    private val adapter = TimerAdapter()

    private lateinit var submit: MenuItem
    private lateinit var decline: MenuItem

    override fun submitData(data: List<TimerStep>) {
        adapter.items = data
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.timer_edit_menu, menu)
        submit = menu.findItem(R.id.timer_submit)
        decline = menu.findItem(R.id.timer_decline)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.timer_decline -> {
                requireActivity().onBackPressed()
                return true
            }
            R.id.timer_submit -> {
                viewModel.update()
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initUI() {
        super.initUI()
        setHasOptionsMenu(true)
        recyclerView.adapter = adapter
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.titleState.observe(this) {
            configureToolbar(it.second, it.first)
        }
        adapter.isChanged.observe(this) {
            submit.isVisible = it
            decline.isVisible = it
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.onStop()
        configureToolbar(
            ContextCompat.getColor(requireContext(), R.color.colorPrimary),
            getString(R.string.app_name)
        )
    }
}