package com.hornedheck.echos.ui.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hornedheck.echos.R
import com.hornedheck.echos.base.list.BaseAdapter
import com.hornedheck.echos.domain.models.Message

class MessageAdapter : BaseAdapter<Message, MessageViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isIncoming) {
            MESSAGE_IN
        } else {
            MESSAGE_OUT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            when (viewType) {
                MESSAGE_IN -> R.layout.item_message_in
                else -> R.layout.item_message_out
            },
            parent, false)

        return MessageViewHolder(view)
    }

    companion object {
        private const val MESSAGE_IN = 1
        private const val MESSAGE_OUT = 2
    }
}