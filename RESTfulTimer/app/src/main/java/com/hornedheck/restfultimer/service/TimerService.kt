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
import com.hornedheck.restfultimer.entities.Timer
import com.hornedheck.restfultimer.framework.models.StepType
import kotlinx.coroutines.*
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
    }
    private val notificationId = 1488
    private lateinit var timer: Timer

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

    private fun updateNotification(content: String) {
        ContextCompat.getSystemService(this, NotificationManager::class.java)
            ?.notify(notificationId, createNotification(content))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotification("Initial text")
        startForeground(notificationId, notification)
        CoroutineScope(Dispatchers.Main).launch { action(intent!!.getLongExtra(ID_KEY, 0)) }
        return super.onStartCommand(intent, flags, startId)
    }

    private suspend fun action(id: Long) {
        timer = withContext(Dispatchers.IO) {
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

        val setRest = steps.firstOrNull { it.type == StepType.SETS_REST }
        val sets = steps.firstOrNull { it.type == StepType.SETS }?.duration ?: 1

        val filteredSteps = steps.filter {
            it.type == StepType.WORK
                    || it.type == StepType.REST
                    || it.type == StepType.REPEAT
        }

        steps.firstOrNull { it.type == StepType.PREPARE }?.let { proceedStep(it) }
        var allRepeats = 0
        while (allRepeats < sets) {
            var repeats = 0
            var pivot = 0
            var i = 0
            while (i < filteredSteps.size) {
                val step = filteredSteps[i]
                when (step.type) {
                    StepType.WORK, StepType.REST -> {
                        proceedStep(step)
                    }
                    StepType.REPEAT -> {
                        if (repeats < step.duration) {
                            i = pivot
                            repeats += 1
                        } else {
                            pivot = i + 1
                            repeats = 0
                        }
                    }
                }
                i += 1
            }
            if (allRepeats < sets - 1) {
                setRest?.let { proceedStep(it) }
            }

            allRepeats += 1
        }
        steps.firstOrNull { it.type == StepType.CALM_DOWN }?.let { proceedStep(it) }
    }

    private suspend fun proceedStep(step: Step) {
        repeat(step.duration) {
            updateNotification("${step.name} $it/${step.duration}")
            delay(1000L)
        }
    }

    companion object {
        const val ID_KEY = "timer_id"
        private const val CHANNEL_ID = "timer_channel"
        private const val CHANNEL_NAME = "Tabata timer"

        fun getStartIntent(context: Context, id: Long): Intent {
            return Intent(context, TimerService::class.java)
                .putExtra(ID_KEY, id)
        }

    }

}


