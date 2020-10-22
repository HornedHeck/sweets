package com.hornedheck.echos.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.echos.R
import com.hornedheck.echos.domain.models.ChannelInfo

class ContactsAdapter(
    private val itemCallback: (com.hornedheck.echos.domain.models.ChannelInfo) -> Unit
) : RecyclerView.Adapter<ContactViewHolder>() {

    private val items = mutableListOf<com.hornedheck.echos.domain.models.ChannelInfo>()

    fun addContact(item: com.hornedheck.echos.domain.models.ChannelInfo) {
        items.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view , itemCallback)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}