package com.hornedheck.restfultimer.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.DrawableRes
import com.hornedheck.restfultimer.service.TimerService

fun playRingtone(context: Context) {
    try {
        val type = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(context, type)
        r.play()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun createAction(
    context: Context,
    @DrawableRes icon: Int,
    title: CharSequence,
    action: String
) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Notification.Action.Builder(
            Icon.createWithResource(context, icon),
            title,
            PendingIntent.getService(
                context,
                0,
                Intent(context, TimerService::class.java).setAction(action),
                0
            )
        ).build()
    } else {
        @Suppress("DEPRECATION")
        Notification.Action(
            icon,
            title,
            PendingIntent.getService(
                context,
                0,
                Intent(context, TimerService::class.java).setAction(action),
                0
            )
        )
    }
