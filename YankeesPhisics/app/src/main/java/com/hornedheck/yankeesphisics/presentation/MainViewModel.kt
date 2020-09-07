package com.hornedheck.yankeesphisics.presentation

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hornedheck.yankeesphisics.SingleLiveEvent
import com.hornedheck.yankeesphisics.data.Conversion
import com.hornedheck.yankeesphisics.data.ConversionGroup
import com.hornedheck.yankeesphisics.data.ConversionProvider
import com.hornedheck.yankeesphisics.data.ConversionProviderImpl

class MainViewModel : ViewModel() {

    private val provider: ConversionProvider = ConversionProviderImpl

    private var group = ConversionGroup.DISTANCE
    private var from: Conversion
    val fromSelection = ObservableInt(0)
    private var to: Conversion
    val toSelection = ObservableInt(0)

    val arg = ObservableField("0")
    val res = ObservableField("0")
    val groups = ObservableField<List<Int>>()
    val types = ObservableField<List<Int>>()

    private val _clipboardData = SingleLiveEvent<String>()
    val clipboardData: LiveData<String> = _clipboardData

    init {
        groups.set(
            provider.getGroups()
                .apply { types.set(provider.getConversions(first()).map(Conversion::title)) }
                .map(ConversionGroup::title)
        )
    }

    init {
        provider.getConversions(group).let {
            from = it.first()
            to = it.first()
        }
    }

    private fun convert() {
        val arg = arg.get()?.toFloat() ?: 0f
        res.set((arg * from.coefficient / to.coefficient).toString())
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
        convert()
    }

    fun swapValues() {
        arg.set(res.get())
        convert()
    }

    fun swapUnits() {
        from = to.also { to = from }
        fromSelection.get().let {
            fromSelection.set(toSelection.get())
            toSelection.set(it)
        }
    }

    fun groupSelected(number: Int) {
        group = provider.getGroups()[number]
        provider.getConversions(group)
            .also { types.set(it.map(Conversion::title)) }
            .first()
            .let {
                from = it
                to = it
                convert()
            }
    }

    fun fromSelected(number: Int) {
        from = provider.getConversions(group)[number]
        fromSelection.set(number)
        convert()
    }

    fun fromCopy() {
        _clipboardData.postValue(arg.get())
    }

    fun toSelected(number: Int) {
        to = provider.getConversions(group)[number]
        toSelection.set(number)
        convert()
    }


    fun toCopy() {
        _clipboardData.postValue(res.get())
    }

}