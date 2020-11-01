package com.hornedheck.echos.ui.main

import android.os.Bundle
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hornedheck.echos.BuildConfig
import com.hornedheck.echos.R
import com.hornedheck.echos.base.BaseActivity
import com.hornedheck.echos.messages.NewMessagesWorker
import com.hornedheck.echos.navigation.EchosNavigator
import com.hornedheck.echos.navigation.GlobalNavigation
import com.hornedheck.echos.navigation.NavigationHostScreen
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main) {

    @Inject
    lateinit var navigation: GlobalNavigation

    private val navigator = EchosNavigator(this, R.id.flRootContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigation.router.newRootScreen(NavigationHostScreen())
    }

    override fun inject() {
        appComponent.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigation.navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigation.navHolder.removeNavigator()
    }

    override fun onBackPressed() {
        navigation.router.exit()
    }

    override fun onStart() {
        WorkManager.getInstance(this)
            .cancelAllWorkByTag(BuildConfig.WORK_TAG)
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        val request = PeriodicWorkRequestBuilder<NewMessagesWorker>(15, TimeUnit.MINUTES)
            .addTag(BuildConfig.WORK_TAG)
            .setInitialDelay(BuildConfig.WORK_DELAY.toLong(), TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueue(request)
    }

}