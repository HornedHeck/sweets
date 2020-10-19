package com.hornedheck.echos.ui.contacts

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.hornedheck.echos.R
import com.hornedheck.echos.appComponent
import com.hornedheck.echos.base.BaseFragment
import com.hornedheck.echos.data.models.User
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
        super.onViewCreated(view, savedInstanceState)
        adapter = ContactsAdapter()
        rvContacts.adapter = adapter
        rvContacts.layoutManager = LinearLayoutManager(requireContext())

        fabAddContact.setOnClickListener {

            val view = EditText(requireContext())
            AlertDialog.Builder(requireContext())
                .setView(view)
                .setPositiveButton("Ok") { _, _ -> presenter.addContact(view.text.toString()) }
                .setNegativeButton("Cancel") { _, _ -> }
                .create()
                .show()
        }
    }

    override fun inject() = appComponent.inject(this)

    override fun addContact(user: User) = adapter.addContact(user)
}