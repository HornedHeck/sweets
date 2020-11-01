package com.hornedheck.echos.messages

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hornedheck.echos.BuildConfig
import com.hornedheck.echos.R
import com.hornedheck.echos.domain.interactors.ChannelsInteractor
import com.hornedheck.echos.ui.login.LoginActivity
import timber.log.Timber

class NewMessagesWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val interactor: ChannelsInteractor
) :
    Worker(
        context,
        workerParams
    ) {

    override fun doWork(): Result {
        interactor.getUnreadCount().subscribe(
            { (messages, channels) ->
                prepareNotification(channels, messages)
            },
            Timber::e
        )

        return Result.success()
    }

    private fun prepareNotification(channels: Int, messages: Int) {
        if (messages == 0) return
        createNotificationChannel()
        val text = if (channels == 1) {
            applicationContext.getString(R.string.new_messages_single, messages)
        } else {
            applicationContext.getString(R.string.new_messages_multi, messages, channels)
        }
        showNotification(text)
    }

    private fun showNotification(text: String) {

        val intent = PendingIntent.getActivity(
            applicationContext,
            1,
            Intent(applicationContext, LoginActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationCompat.Builder(applicationContext, BuildConfig.NOTIFICATION_CHANNEL)
            } else {
                NotificationCompat.Builder(applicationContext)
            }
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText(text)
                .setContentTitle(applicationContext.getString(R.string.new_messages_title))
                .setContentIntent(intent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        NotificationManagerCompat.from(applicationContext).notify(
            BuildConfig.NOTIFICATION_ID, notification
        )
    }


    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            BuildConfig.NOTIFICATION_CHANNEL,
            applicationContext.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        NotificationManagerCompat.from(applicationContext).createNotificationChannel(channel)
    }

    companion object

}