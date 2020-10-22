package com.hornedheck.echos.ui.contacts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.echos.domain.models.ChannelInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*

class ContactViewHolder(
    override val containerView: View,
    private val itemCallback: (com.hornedheck.echos.domain.models.ChannelInfo) -> Unit
) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {


    fun bind(item: com.hornedheck.echos.domain.models.ChannelInfo) {
        itemView.setOnClickListener { itemCallback(item) }
        tvName.text = item.user.name
        tvLink.text = item.user.link
    }

}