package com.hornedheck.echos.screens

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.hornedheck.echos.R

class MessagesScreen : Screen<MessagesScreen>() {

//    val messages = KRecyclerView { withId(R.id.rvMessages) }

    val send = KImageView { withId(R.id.btnSend) }

    val editButtons = KView { withId(R.id.llEditButtons) }

}