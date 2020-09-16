package com.hornedheck.restfultimer.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseAdapter<T, VH : BaseViewHolder<T>> : Adapter<VH>() {

    protected val _items = mutableListOf<T>()
    var items: List<T> = _items
        set(value) {
            _items.clear()
            _items.addAll(value)
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

}

abstract class BaseViewHolder<T>(itemView: View) : ViewHolder(itemView) {

    abstract fun bind(item: T)

}