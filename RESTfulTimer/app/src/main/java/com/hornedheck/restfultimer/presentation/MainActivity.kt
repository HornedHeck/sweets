package com.hornedheck.restfultimer.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.hornedheck.restfultimer.R
import com.hornedheck.restfultimer.presentation.timers.TimersFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadPreferences()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
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

    private fun loadPreferences() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        setLocale(prefs.getString("lang", "en")!!)
        scaleFont(prefs.getInt("font", 10) * 0.1f)
        setTheme(prefs.getBoolean("theme", false))
    }

    private fun setLocale(code: String) {
        val locale = Locale(code)
        val config = baseContext.resources.configuration
        Locale.setDefault(locale)
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }

    private fun scaleFont(scale: Float) {
        resources.configuration.fontScale = scale
        val metrics = resources.displayMetrics
        val wm = ContextCompat.getSystemService(this, WindowManager::class.java)
        wm!!.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = resources.configuration.fontScale * metrics.density
        resources.updateConfiguration(resources.configuration, metrics)
    }

    private fun setTheme(isNight: Boolean) {
        if (isNight) {
            setTheme(R.style.AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }
}