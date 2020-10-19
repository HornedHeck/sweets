package com.hornedheck.echos.ui.contacts

import com.hornedheck.echos.data.repo.MessagesRepo
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ContactsPresenter @Inject constructor(
    private val repo: MessagesRepo
) : MvpPresenter<ContactsView>() {


}