package com.hornedheck.echos.ui.contacts

import com.hornedheck.echos.base.BasePresenter
import com.hornedheck.echos.domain.interactors.ChannelsInteractor
import com.hornedheck.echos.navigation.MessagesScreen
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ContactsPresenter @Inject constructor(
    private val interactor: ChannelsInteractor,
    private val router: Router
) : BasePresenter<ContactsView>() {

    private val disposable = CompositeDisposable()

    init {
        disposable.addAll(
            interactor.observeChannels().subscribe(viewState::addContact, viewState::showError),
            interactor.getChannels().subscribe(viewState::addContact, viewState::showError)
        )
    }


    fun selectContact(info: com.hornedheck.echos.domain.models.ChannelInfo) {
        router.navigateTo(MessagesScreen(info.id))
    }

    fun addContact(link: String) {
        interactor.addContact(link)
            .subscribe({
                Timber.d("Successful for $link")
            }, {
                Timber.d("Fail for $link")
                viewState.showError("", "Wrong link $link")
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}