package com.hornedheck.restfultimer.presentation.settings

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import com.hornedheck.restfultimer.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val fontPrefs = findPreference<SeekBarPreference>("font")
        fontPrefs?.max = 20
        fontPrefs?.min = 7
        fontPrefs?.setOnPreferenceChangeListener { pref, value ->
            value as Int
            pref?.summary = (value * 0.1).format(1)
            scaleFont(value * 0.1f)
            true
        }
        fontPrefs?.summary = fontPrefs?.value?.times(0.1)?.format(1)

        val langPrefs = findPreference<ListPreference>("lang")

        val clearPref = findPreference<Preference>("clear")
        clearPref?.setOnPreferenceClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.settings_clear_warning)
                .setPositiveButton(R.string.yes) { _, _ -> }
                .setNegativeButton(R.string.no) { _, _ -> }
                .create()
                .show()
            true
        }
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun scaleFont(scale: Float) {
        resources.configuration.fontScale = scale
        val metrics = resources.displayMetrics
        val wm = getSystemService(requireContext(), WindowManager::class.java)
        wm!!.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = resources.configuration.fontScale * metrics.density
        resources.updateConfiguration(resources.configuration, metrics)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .detach(this)
            .attach(this)
            .commit()

    }

}