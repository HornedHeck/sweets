package com.hornedheck.echos.ui.contacts

import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.data.models.ChannelInfo
import com.hornedheck.echos.data.repo.MessagesRepo
import com.hornedheck.echos.navigation.MessagesScreen
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ContactsPresenter @Inject constructor(
    private val repo: MessagesRepo,
    private val router: Router
) : BasePresenter<ContactsView>() {

    private val disposable = CompositeDisposable()

    init {
        disposable.add(repo.observeContracts().subscribe(viewState::addContact))
    }

    fun selectContact(info: ChannelInfo) {
        router.navigateTo(MessagesScreen(info.id))
    }

    fun addContact(link: String) {
        repo.addContact(link, 5)
            .subscribe({}, { viewState.showError("", "Wrong link $link") })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}