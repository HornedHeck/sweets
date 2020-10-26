package com.hornedheck.echos.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hornedheck.echos.R
import com.hornedheck.echos.base.list.BaseAdapter
import com.hornedheck.echos.domain.models.ChannelInfo

class ContactsAdapter(itemCallback: ((ChannelInfo) -> Unit)) :
    BaseAdapter<ChannelInfo, ContactViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view, itemCallback)
    }

}