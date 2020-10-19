package com.hornedheck.echos.ui.contacts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.echos.data.models.ChannelInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*

class ContactViewHolder(
    override val containerView: View,
    private val itemCallback: (ChannelInfo) -> Unit
) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {


    fun bind(item: ChannelInfo) {
        itemView.setOnClickListener { itemCallback(item) }
        tvName.text = item.name
        tvLink.text = item.link
    }

}