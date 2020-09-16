package com.hornedheck.restfultimer.utils

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.hornedheck.restfultimer.utils.BaseViewModel.DisplayableState
import com.hornedheck.restfultimer.utils.BaseViewModel.State

/** Let's imagine that it's defined in other module and functions with internal doesn't visible outside*/
abstract class BaseViewModel : ViewModel() {


    class State<T>(initial: T? = null) {

        private val _data = MutableLiveData<T>()
        val data: LiveData<T> = _data

        init {
            initial?.let { _data.value = it }
        }

        val value: T?
            get() = data.value

        internal fun postValueInternal(value: T) {
            _data.postValue(value)
        }

        internal fun setValueInternal(value: T) {
            _data.value = value
        }


    }

    class DisplayableState<T>(initial: T? = null, isVisible: Boolean = false) {
        private val _data = MutableLiveData<Pair<Boolean, T?>>()
        val data: LiveData<Pair<Boolean, T?>> = _data

        init {
            initial?.let { _data.value = isVisible to it }
        }

        val value: T? get() = data.value?.second

        internal fun showInternal(value: T) {
            _data.value = true to value
        }

        internal fun hideInternal() {
            _data.value = false to null
        }

    }

    @MainThread
    protected fun <T> DisplayableState<T>.show(value: T) = showInternal(value)

    @MainThread
    protected fun <T> DisplayableState<T>.hide() = hideInternal()

    @MainThread
    protected fun <T> State<T>.setValue(value: T) = setValueInternal(value)

    protected fun <T> State<T>.postValue(value: T) = postValueInternal(value)
}

typealias DisplayableObserver<T> = (isVisible: Boolean, data: T?) -> Unit

fun <T> State<T>.observe(owner: LifecycleOwner, observer: Observer<T>) =
    data.observe(owner, observer)

fun <T> DisplayableState<T>.observe(
    owner: LifecycleOwner,
    observer: DisplayableObserver<T>
) {
    data.observe(owner) {
        observer(it.first, it.second)
    }
}
