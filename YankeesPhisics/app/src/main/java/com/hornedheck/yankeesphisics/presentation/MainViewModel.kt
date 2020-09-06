package com.hornedheck.yankeesphisics.presentation

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.hornedheck.yankeesphisics.data.Conversion
import com.hornedheck.yankeesphisics.data.ConversionGroup
import com.hornedheck.yankeesphisics.data.ConversionProvider
import com.hornedheck.yankeesphisics.data.ConversionProviderImpl

class MainViewModel : ViewModel() {

    private val provider: ConversionProvider = ConversionProviderImpl

    private var group = ConversionGroup.DISTANCE
    private var from: Conversion
    private var to: Conversion

    val arg = ObservableField("0")
    val res = ObservableField("0")

    init {
        provider.getConversions(group).let {
            from = it.first()
            to = it.first()
        }
    }

    private fun convert() {
        val arg = arg.get()?.toFloat() ?: 0f
        res.set((arg / from.coefficient * to.coefficient).toString())
    }

    private fun clear() {
        arg.set("0")
        res.set("0")
    }

    fun delete() {
        arg.get()?.let { arg ->
            if (arg.length == 1) {
                clear()
            } else {
                this.arg.set(arg.substring(0, arg.length - 1).let {
                    if (it.endsWith('.')) {
                        it.substring(0, it.length - 1)
                    } else {
                        it
                    }
                })
                convert()
            }
        }
    }

    fun dot() {
        if (!arg.get()!!.contains('.')) {
            arg.set(arg.get()!! + '.')
        }
    }

    fun numeric(text: Char) {
        if (arg.get() == "0") {
            arg.set(text.toString())
        } else {
            arg.set(arg.get()!! + text)
        }
    }

    fun swapValues() {
        arg.set(res.get())
        convert()
    }

    fun swapUnits() {
        from = to.also { to = from }
        convert()
    }

}