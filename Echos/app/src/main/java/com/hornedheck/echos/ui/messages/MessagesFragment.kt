package com.hornedheck.echos.ui.messages

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.echos.R
import com.hornedheck.echos.appComponent
import com.hornedheck.echos.base.list.BaseAdapter
import com.hornedheck.echos.base.list.ListFragment
import com.hornedheck.echos.domain.models.Message
import kotlinx.android.synthetic.main.fragment_messages.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MessagesFragment :
    ListFragment<Message, MessageViewHolder>(R.layout.fragment_messages),
    MessagesView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MessagesPresenter

    @ProvidePresenter
    fun providePresenter() = presenter


    override fun inject() {
        appComponent.inject(this)
        presenter.init(requireArguments().getString(CHANNEL_ID_KEY)!!)
    }

    override lateinit var adapter: BaseAdapter<Message, MessageViewHolder>
    override val recyclerView: RecyclerView
        get() = rvMessages

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MessageAdapter()
        super.onViewCreated(view, savedInstanceState)

        btnSend.setOnClickListener {
            presenter.sendMessage(etContent.text.toString())
            etContent.setText("")
        }
    }

    companion object {

        private const val CHANNEL_ID_KEY = "channel_id"

        fun getInstance(channelId: String) = MessagesFragment()
            .apply {
                arguments = bundleOf(CHANNEL_ID_KEY to channelId)
            }
    }

}