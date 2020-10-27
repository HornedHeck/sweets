package com.hornedheck.echos.ui.messages

import android.view.View
import com.hornedheck.echos.base.list.BaseViewHolder
import com.hornedheck.echos.domain.models.Message
import kotlinx.android.synthetic.main.item_message_in.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MessageViewHolder(containerView: View) : BaseViewHolder<Message>(containerView) {

    override fun bind(item: Message) {
        tvTime.text =
            LocalDateTime.ofInstant(item.time, ZoneId.systemDefault()).toLocalTime().format(
                DateTimeFormatter.ofPattern("hh:mm"))
        tvContent.text = item.content
    }
}