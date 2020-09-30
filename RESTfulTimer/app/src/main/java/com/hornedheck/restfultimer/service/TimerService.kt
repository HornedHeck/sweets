package com.hornedheck.restfultimer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.data.Repository
import com.hornedheck.restfultimer.framework.models.StepType
import com.hornedheck.restfultimer.utils.createAction
import com.hornedheck.restfultimer.utils.playRingtone
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject


class TimerService : Service(), KoinComponent {

    private val repo by inject<Repository>()
    private val builder: Notification.Builder by lazy {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = createChannel()
            Notification.Builder(this, channel)
        } else {
            Notification.Builder(this)
        }
            .setSound(null)
            .setSmallIcon(R.drawable.ic_all_repeats)
            .setStyle(Notification.BigTextStyle())
            .addAction(
                createAction(
                    this,
                    R.drawable.ic_stop,
                    getString(R.string.command_stop),
                    STOP_COMMAND
                )
            )
            .addAction(
                createAction(
                    this,
                    R.drawable.ic_pause,
                    getString(R.string.command_pause),
                    PAUSE_COMMAND
                )
            )
            .addAction(
                createAction(
                    this,
                    R.drawable.ic_skip,
                    getString(R.string.command_skip),
                    NEXT_COMMAND
                )
            )

    }
    private val pauseActions by lazy {
        arrayOf(
            createAction(
                this,
                R.drawable.ic_stop,
                getString(R.string.command_stop),
                STOP_COMMAND
            ),
            createAction(
                this,
                R.drawable.ic_run,
                getString(R.string.command_resume),
                RESUME_COMMAND
            ),
            createAction(
                this,
                R.drawable.ic_skip,
                getString(R.string.command_skip),
                NEXT_COMMAND
            )
        )
    }
    private var contentString = ""

    private val notificationId = 1488

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
        when (intent?.action) {
            START_COMMAND -> {
                startForeground(notificationId, createNotification(""))
                configureStepsExecutor()
                start(intent.getLongExtra(ID_KEY, 0))
            }
            PAUSE_COMMAND -> {
                StepsExecutor.pause()
                updateNotification(getString(R.string.status_paused) + contentString) {
                    it.actions = pauseActions
                }
            }
            RESUME_COMMAND -> {
                StepsExecutor.resume()
            }
            NEXT_COMMAND -> {
                StepsExecutor.skip()
            }
            STOP_COMMAND -> {
                StepsExecutor.stop()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun configureStepsExecutor() {
        StepsExecutor.notifyStep = { progress, step ->
            contentString = "${step.name} $progress/${step.duration}"
            updateNotification(contentString)
        }
        StepsExecutor.notifyFinished = {
            updateNotification(getString(R.string.status_finished)) {
                it.actions = emptyArray()
            }
            playRingtone(this)
            stopForeground(false)
        }
        StepsExecutor.notifyStepFinished = { playRingtone(this) }
    }

    private fun start(id: Long) {
        CoroutineScope(Dispatchers.Default).launch {
            val timer = withContext(Dispatchers.IO) {
                repo.getTimer(id)
            }.data!!
            builder.setContentTitle(timer.name)
            val steps = timer.steps!!.map {
                Step(
                    it.name,
                    it.description,
                    it.duration,
                    StepType.values()[it.type],
                    it.position
                )
            }.sortedBy { it.position }
            StepsExecutor.stop(false)
            StepsExecutor.start(steps)
        }
    }

    companion object {
        const val ID_KEY = "timer_id"
        private const val CHANNEL_ID = "timer_channel"
        private const val CHANNEL_NAME = "Tabata timer"
        private const val START_COMMAND = "Timer.Start"
        private const val PAUSE_COMMAND = "Timer.Pause"
        private const val RESUME_COMMAND = "Timer.Resume"
        private const val NEXT_COMMAND = "Timer.Next"
        private const val STOP_COMMAND = "Timer.Stop"

        fun getStartIntent(context: Context, id: Long): Intent {
            return Intent(context, TimerService::class.java)
                .putExtra(ID_KEY, id)
                .setAction(START_COMMAND)
        }
    }
}


