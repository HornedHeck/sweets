package com.hornedheck.echos.data.repo

import com.hornedheck.echos.data.api.MessagesApi

internal class MessagesRepoImpl(
    private val api: MessagesApi
) : MessagesRepo {
}