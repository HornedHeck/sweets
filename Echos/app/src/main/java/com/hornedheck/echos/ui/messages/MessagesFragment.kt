package com.hornedheck.echos.ui.messages

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.echos.R
import com.hornedheck.echos.base.list.BaseAdapter
import com.hornedheck.echos.base.list.ListFragment
import com.hornedheck.echos.domain.models.Message
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.layout_message_actions.view.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MessagesFragment :
    ListFragment<Message, MessageViewHolder>(R.layout.fragment_messages),
    MessagesView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MessagesPresenter

    override val layoutManager by lazy {
        LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            presenter.back()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        appComponent.inject(this)
        presenter.init(requireArguments().getString(CHANNEL_ID_KEY)!!)
    }

    override fun addItem(item: Message) {
        super.addItem(item)
        rvMessages.scrollToPosition(adapter.itemCount - 1)
    }

    override lateinit var adapter: BaseAdapter<Message, MessageViewHolder>
    override val recyclerView: RecyclerView
        get() = rvMessages

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MessageAdapter(this::askForAction)
        super.onViewCreated(view, savedInstanceState)

        with((requireActivity() as AppCompatActivity)) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener { presenter.back() }


        btnSend.setOnClickListener {
            presenter.sendMessage(etContent.text.toString())
            etContent.setText("")
        }
    }

    override fun updateItem(message: Message) {
        (adapter as MessageAdapter).updateItem(message)
    }

    override fun deleteItem(message: Message) {
        (adapter as MessageAdapter).deleteItem(message)
    }

    @SuppressLint("InflateParams")
    private fun askForAction(item: Message) {

        val view = LayoutInflater.from(requireContext()).inflate(
            R.layout.layout_message_actions,
            null,
            false
        )


        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .create()

        view.btnDelete.setOnClickListener {
            dialog.dismiss()
            presenter.deleteMessage(item)
        }
        view.btnEdit.setOnClickListener {
            dialog.dismiss()
            presenter.updateMessage(item)
        }

        dialog.show()
    }

    companion object {

        private const val CHANNEL_ID_KEY = "channel_id"

        fun getInstance(channelId: String) = MessagesFragment()
            .apply {
                arguments = bundleOf(CHANNEL_ID_KEY to channelId)
            }
    }

}