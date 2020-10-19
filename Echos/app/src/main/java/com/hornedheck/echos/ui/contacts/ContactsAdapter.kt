package com.hornedheck.echos.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.echos.R
import com.hornedheck.echos.data.models.ChannelInfo

class ContactsAdapter(
    private val itemCallback: (ChannelInfo) -> Unit
) : RecyclerView.Adapter<ContactViewHolder>() {

    private val items = mutableListOf<ChannelInfo>()

    fun addContact(item: ChannelInfo) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view , itemCallback)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}