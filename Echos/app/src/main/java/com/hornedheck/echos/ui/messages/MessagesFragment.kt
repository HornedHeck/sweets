package com.hornedheck.echos.ui.messages

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.echos.R
import com.hornedheck.echos.appComponent
import com.hornedheck.echos.base.list.BaseAdapter
import com.hornedheck.echos.base.list.ListFragment
import com.hornedheck.echos.domain.models.Message
import kotlinx.android.synthetic.main.fragment_messages.*

class MessagesFragment :
    ListFragment<Message, MessageViewHolder>(R.layout.fragment_messages),
    MessagesView {

    override fun inject() {
        appComponent.inject(this)
    }

    override lateinit var adapter: BaseAdapter<Message, MessageViewHolder>
    override val recyclerView: RecyclerView
        get() = rvMessages

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MessageAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

}