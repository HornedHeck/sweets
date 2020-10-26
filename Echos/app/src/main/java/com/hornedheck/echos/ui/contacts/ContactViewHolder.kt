package com.hornedheck.echos.ui.contacts

import android.view.View
import com.hornedheck.echos.base.list.BaseViewHolder
import com.hornedheck.echos.domain.models.ChannelInfo
import kotlinx.android.synthetic.main.item_contact.*

class ContactViewHolder(
    containerView: View,
    private val itemCallback: ((ChannelInfo) -> Unit)?,
) : BaseViewHolder<ChannelInfo>(containerView) {

    override fun bind(item: ChannelInfo) {
        itemView.setOnClickListener { itemCallback?.invoke(item) }
        tvName.text = item.user.name
        tvLink.text = item.user.link
    }

}