package com.hornedheck.echos.uitests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.hornedheck.echos.screens.MessagesScreen
import com.hornedheck.echos.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test

class MessagesUiTests {


    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun buttonsVisibilityTest(){

        onScreen<MessagesScreen>{
            send{
                isVisible()
            }
//            edit{
//                isNotDisplayed()
//            }
//            cancel{
//                isNotDisplayed()
//            }
        }

    }

}