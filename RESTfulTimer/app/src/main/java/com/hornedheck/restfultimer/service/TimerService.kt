package com.hornedheck.restfultimer.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.data.Repository
import com.hornedheck.restfultimer.framework.models.StepType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class TimerService : Service(), KoinComponent {

    private val repo by inject<Repository>()
    private val builder: Notification.Builder by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createChannel()
            Notification.Builder(this, channel)
        } else {
            Notification.Builder(this)
        }
            .setSound(null)
            .setContentTitle("Title")
            .setSmallIcon(R.drawable.ic_run)
            .setStyle(Notification.BigTextStyle())
            .addAction(createAction(R.drawable.ic_pause, "Pause", PAUSE_COMMAND))
            .addAction(createAction(R.drawable.ic_skip, "Skip", NEXT_COMMAND))
    }

    private fun createAction(@DrawableRes icon: Int, title: CharSequence, action: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Notification.Action.Builder(
                Icon.createWithResource(this, icon),
                title,
                PendingIntent.getService(
                    this,
                    0,
                    Intent(this, TimerService::class.java).setAction(action),
                    0
                )
            ).build()
        } else {
            Notification.Action(
                icon,
                title,
                PendingIntent.getService(
                    this,
                    0,
                    Intent(this, TimerService::class.java).setAction(action),
                    0
                )
            )
        }

    private val notificationId = 1488
    private lateinit var executor: StepsExecutor

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(): String {
        val notificationManager =
            ContextCompat.getSystemService(this.applicationContext, NotificationManager::class.java)
        notificationManager?.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setSound(null, null)
            }
        )
        return CHANNEL_ID
    }

    private fun createNotification(content: String): Notification {
        return builder
            .setContentText(content)
            .build()
    }

    private fun updateNotification(
        content: String,
        modifyNotification: (Notification) -> Unit = {}
    ) {
        ContextCompat.getSystemService(this, NotificationManager::class.java)
            ?.notify(notificationId, createNotification(content).also(modifyNotification))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification("Initial text")
        when (intent?.action) {
            START_COMMAND -> {
                startForeground(notificationId, notification)
                StepsExecutor.notifyStep = { progress, step ->
                    updateNotification("${step.name} $progress/${step.duration}")
                }
                CoroutineScope(Dispatchers.Main).launch { action(intent.getLongExtra(ID_KEY, 0)) }
            }
            PAUSE_COMMAND -> {
                StepsExecutor.pause()
//                createNotification("Paused")
                updateNotification("Paused") {
                    it.actions = arrayOf(
                        createAction(R.drawable.ic_run, "Resume", RESUME_COMMAND),
                        createAction(R.drawable.ic_skip, "Skip", NEXT_COMMAND)
                    )
                }
            }
            RESUME_COMMAND -> {
                StepsExecutor.resume()
            }
            NEXT_COMMAND -> {
                StepsExecutor.skip()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private suspend fun action(id: Long) {

        val timer = withContext(Dispatchers.IO) {
            repo.getTimer(id)
        }.data!!
        val steps = timer.steps!!.map {
            Step(
                it.name,
                it.description,
                it.duration,
                StepType.values()[it.type],
                it.position
            )
        }.sortedBy { it.position }

//        executor = StepsExecutor(steps) { progress, step ->
//            updateNotification("${step.name} $progress/${step.duration}")}
        StepsExecutor.start(steps)
    }


    companion object {
        const val ID_KEY = "timer_id"
        private const val CHANNEL_ID = "timer_channel"
        private const val CHANNEL_NAME = "Tabata timer"
        private const val START_COMMAND = "Timer.Start"
        private const val PAUSE_COMMAND = "Timer.Pause"
        private const val RESUME_COMMAND = "Timer.Resume"
        private const val NEXT_COMMAND = "Timer.Next"

        fun getStartIntent(context: Context, id: Long): Intent {
            return Intent(context, TimerService::class.java)
                .putExtra(ID_KEY, id)
                .setAction(START_COMMAND)
        }

    }
}


