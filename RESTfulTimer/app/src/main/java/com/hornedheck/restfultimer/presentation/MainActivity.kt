package com.hornedheck.restfultimer.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.presentation.timers.TimersFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment, TimersFragment())
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}