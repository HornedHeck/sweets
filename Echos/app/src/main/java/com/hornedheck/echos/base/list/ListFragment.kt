package com.hornedheck.echos.base.list

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.echos.base.BaseFragment

abstract class ListFragment<T, VH : BaseViewHolder<T>>(@LayoutRes layoutRes: Int = 0) :
    BaseFragment(layoutRes),
    ListView<T> {

    protected abstract var adapter: BaseAdapter<T, VH>

    protected abstract val recyclerView: RecyclerView

    protected open val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(requireContext())

    override fun addItem(item: T) {
        adapter.add(item)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
    }
}