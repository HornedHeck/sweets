package com.hornedheck.restfultimer.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.utils.observe
import com.hornedheck.restfultimer.utils.visible
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.recyclerView as rv
import kotlinx.android.synthetic.main.fragment_list.swipeRefreshLayout as srl

abstract class ListFragment<T> : Fragment() {


    protected abstract val viewModel: ListViewModel<T>

    @get: LayoutRes
    protected open val layoutRes: Int = R.layout.fragment_list

    protected open val recyclerView: RecyclerView get() = rv

    protected open val errorText: TextView get() = tvNoItems

    protected open val swipeRefreshLayout: SwipeRefreshLayout get() = srl

    protected abstract fun submitData(data: List<T>)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initUI()

        viewModel.updateData()
    }

    open fun initObservers() {
        viewModel.items.observe(this) { isVisible, data ->
            recyclerView.visible = isVisible
            errorText.visible = !isVisible
            submitData(data!!)
            swipeRefreshLayout.isRefreshing = false
        }
        viewModel.isError.observe(this) {
            recyclerView.visible = !it
            errorText.visible = it
            swipeRefreshLayout.isRefreshing = false
        }
        viewModel.isLoading.observe(this) {
            swipeRefreshLayout.isRefreshing = it
            if (it) {
                recyclerView.visible = false
                errorText.visible = false
            }
        }
    }

    open fun initUI() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateData()
        }
        recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
    }

}