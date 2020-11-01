package com.hornedheck.echos.ui.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hornedheck.echos.R
import com.hornedheck.echos.base.list.BaseAdapter
import com.hornedheck.echos.domain.models.Message

class MessageAdapter(private val longClickCallback: (Message) -> Unit) :
    BaseAdapter<Message, MessageViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isIncoming) {
            MESSAGE_IN
        } else {
            MESSAGE_OUT
        }
    }

    fun deleteItem(item: Message) {
        val pos = items.indexOf(item)
        if (pos !in items.indices) return
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun updateItem(item: Message) {
        val pos = items.indexOf(item)
        if (pos !in items.indices) return
        items[pos] = item
        notifyItemChanged(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            if (viewType == MESSAGE_IN) R.layout.item_message_in
            else R.layout.item_message_out,
            parent,
            false
        )
        val callback = if (viewType == MESSAGE_IN) null else longClickCallback

        return MessageViewHolder(view, callback)
    }

    companion object {
        private const val MESSAGE_IN = 1
        private const val MESSAGE_OUT = 2
    }
}