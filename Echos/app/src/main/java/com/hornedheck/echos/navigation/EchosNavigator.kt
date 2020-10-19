package com.hornedheck.echos.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import timber.log.Timber

class EchosNavigator(
    activity: FragmentActivity,
    @IdRes container: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager
) : SupportAppNavigator(activity, fragmentManager, container) {

    override fun applyCommands(commands: Array<out Command>) {
        try {
            super.applyCommands(commands)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}