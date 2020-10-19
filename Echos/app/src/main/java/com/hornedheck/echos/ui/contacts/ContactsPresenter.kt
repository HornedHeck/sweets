package com.hornedheck.echos.ui.contacts

import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.data.repo.MessagesRepo
import io.reactivex.rxjava3.disposables.Disposable
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ContactsPresenter @Inject constructor(
    private val repo: MessagesRepo
) : BasePresenter<ContactsView>() {

    private val disposable: Disposable = repo.observeContracts().subscribe(viewState::addContact)


    fun addContact(link: String) {
        if (!repo.addContact(link)) {
            viewState.showError("", "Wrong link $link")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}