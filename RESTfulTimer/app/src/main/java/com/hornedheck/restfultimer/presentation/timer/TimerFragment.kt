package com.hornedheck.restfultimer.presentation.timer

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.entities.TimerStep
import com.hornedheck.restfultimer.framework.models.StepType
import com.hornedheck.restfultimer.presentation.list.ListFragment
import com.hornedheck.restfultimer.utils.observe
import com.hornedheck.restfultimer.utils.visible
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape
import kotlinx.android.synthetic.main.fragment_timer.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TimerFragment(id: Long) : ListFragment<TimerStep>() {

    override val viewModel: TimerViewModel by viewModel { parametersOf(id) }

    private val adapter = TimerAdapter()

    override val layoutRes = R.layout.fragment_timer

    private lateinit var submit: MenuItem
    private lateinit var decline: MenuItem

    private val touchCallback = TimerTouchCallback()

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
            R.id.timer_color -> {
                viewModel.colorClicked()
                return true
            }
            R.id.timer_edit -> {
                viewModel.updateTitleClicked()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initUI() {
        super.initUI()
        setHasOptionsMenu(true)
        swipeRefreshLayout.isEnabled = false
        recyclerView.adapter = adapter
        ItemTouchHelper(touchCallback).attachToRecyclerView(recyclerView)
        fabCalmDown.setOnClickListener { addStep(StepType.CALM_DOWN) }
        fabPrepare.setOnClickListener { addStep(StepType.PREPARE) }
        fabRepeats.setOnClickListener { addStep(StepType.REPEAT) }
        fabRest.setOnClickListener { addStep(StepType.REST) }
        fabSets.setOnClickListener { addStep(StepType.SETS) }
        fabSetsRest.setOnClickListener { addStep(StepType.SETS_REST) }
        fabWork.setOnClickListener { addStep(StepType.WORK) }
        fabAdd.setOnClickListener { viewModel.toggleMenu() }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.titleState.observe(this) { configureToolbar(it.second, it.first) }
        adapter.isChanged.observe(this, viewModel::trySetChanged)
        viewModel.isChanged.observe(this) {
            submit.isVisible = it
            decline.isVisible = it
        }
        viewModel.menuVisibility.observe(this) { llSubmenu.visible = it }
        viewModel.colorPickerEvent.observe(this) {
            val dialog = ColorPickerDialog.newBuilder()
                .setColor(it)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowCustom(true)
                .setAllowPresets(true)
                .setColorShape(ColorShape.SQUARE)
                .setDialogId(id)
                .create()
            dialog.setColorPickerDialogListener(object : ColorPickerDialogListener {
                override fun onColorSelected(dialogId: Int, color: Int) {
                    viewModel.changeColor(color)
                }

                override fun onDialogDismissed(dialogId: Int) {}
            })
            dialog.show(requireActivity().supportFragmentManager, null)
        }
        viewModel.updateTitleEvent.observe(this, this::createTitleDialog)
        touchCallback.itemSwiped.observe(this) {
            viewModel.removeStep(it)
        }
        touchCallback.onItemMove.observe(this) { (from, to) ->
            viewModel.moveStep(from, to)
            adapter.moveItem(from, to)
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.onStop()
        viewModel.resetState()
        configureToolbar(
            ContextCompat.getColor(requireContext(), R.color.colorPrimary),
            getString(R.string.app_name)
        )
    }

    private fun addStep(type: StepType) {
        viewModel.add(type, requireContext().getString(type.title))
    }

    private fun createTitleDialog(oldTitle: String) {

        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.layout_editor, null, false) as EditText
        view.setText(oldTitle)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton(R.string.submit) { _, _ ->
                viewModel.updateTitle(view.text.toString())
            }
            .setNegativeButton(R.string.decline) { _, _ -> }
            .create()

        view.doOnTextChanged { text, _, _, _ ->
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = !text.isNullOrBlank()
        }

        dialog.show()
    }
}