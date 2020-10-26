package com.hornedheck.echos.base.list

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder<T>>(
    protected val itemCallback: ((T) -> Unit)? = null,
) : RecyclerView.Adapter<VH>() {

    protected val items = mutableListOf<T>()

    fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}