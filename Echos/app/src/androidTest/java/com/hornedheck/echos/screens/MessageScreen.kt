package com.hornedheck.echos.screens

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.hornedheck.echos.R


class MessageOutScreen : Screen<MessageOutScreen>() {

    val content = KTextView { withId(R.id.tvContent) }
    val date = KTextView { withId(R.id.tvTime) }


}