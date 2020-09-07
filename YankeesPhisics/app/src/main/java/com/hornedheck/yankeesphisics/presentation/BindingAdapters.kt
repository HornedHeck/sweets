package com.hornedheck.yankeesphisics.presentation

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter

interface IntAction {
    fun invoke(pos: Int)
}

@BindingAdapter("on_selected")
fun Spinner.onSelected(action: IntAction) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapter: AdapterView<*>?, view: View?, pos: Int, id: Long) {
            action.invoke(pos)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }
}

@BindingAdapter("items")
fun Spinner.items(items: List<Int>) {
    adapter = ArrayAdapter(
        context,
        R.layout.simple_spinner_item,
        items.map(context::getString)
    )
}

@BindingAdapter("selection")
fun Spinner.selection(pos: Int) {
    setSelection(pos, false)
}
