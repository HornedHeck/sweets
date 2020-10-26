package com.hornedheck.echos.ui.contacts

import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hornedheck.echos.R
import com.hornedheck.echos.appComponent
import com.hornedheck.echos.base.BaseFragment
import com.hornedheck.echos.base.list.BaseAdapter
import com.hornedheck.echos.base.list.BaseViewHolder
import com.hornedheck.echos.base.list.ListFragment
import com.hornedheck.echos.domain.models.ChannelInfo
import com.hornedheck.echos.utils.textInputDialog
import kotlinx.android.synthetic.main.fragment_contacts.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class ContactsFragment : ListFragment<ChannelInfo , ContactViewHolder>(R.layout.fragment_contacts), ContactsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ContactsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override lateinit var adapter: BaseAdapter<ChannelInfo, ContactViewHolder>

    override val recyclerView: RecyclerView
        get() = rvContacts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = ContactsAdapter(presenter::selectContact)

        super.onViewCreated(view, savedInstanceState)

        fabAddContact.setOnClickListener {
            requireContext().textInputDialog(
                presenter::addContact,
                hint = R.string.hint_link
            ).show()
        }
    }

    override fun inject() = appComponent.inject(this)

}