package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.MessagesApi

internal class MessagesRepoImpl(
    private val api: MessagesApi
) : MessagesRepo {

    override fun observeContracts() = api.observeContracts()

    override fun addContact(link: String) = api.addContact(link)
}