package com.hornedheck.echos.ui.contacts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hornedheck.echos.R
import com.hornedheck.echos.appComponent
import com.hornedheck.echos.base.BaseFragment
import com.hornedheck.echos.domain.models.ChannelInfo
import com.hornedheck.echos.utils.textInputDialog
import kotlinx.android.synthetic.main.fragment_contacts.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class ContactsFragment : BaseFragment(R.layout.fragment_contacts), ContactsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: ContactsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private lateinit var adapter: ContactsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().setTheme(R.style.Theme_Echos)

        super.onViewCreated(view, savedInstanceState)

        adapter = ContactsAdapter(presenter::selectContact)
        rvContacts.adapter = adapter
        rvContacts.layoutManager = LinearLayoutManager(requireContext())

        fabAddContact.setOnClickListener {
            requireContext().textInputDialog(
                presenter::addContact,
                hint = R.string.hint_link
            ).show()
        }
    }

    override fun inject() = appComponent.inject(this)

    override fun addContact(info: com.hornedheck.echos.domain.models.ChannelInfo) = adapter.addContact(info)
}