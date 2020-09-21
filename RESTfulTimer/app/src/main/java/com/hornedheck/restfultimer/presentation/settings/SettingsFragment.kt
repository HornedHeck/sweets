package com.hornedheck.restfultimer.presentation.settings

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.preference.*
import com.hornedheck.restfultimer.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel by viewModel<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val isNight = prefs.getBoolean("theme", false)
        val themedContext = ContextThemeWrapper(
            requireActivity(), if (isNight) {
                R.style.AppTheme_Dark
            } else {
                R.style.AppTheme
            }
        )
        val themeInflater = inflater.cloneInContext(themedContext)
        val view = super.onCreateView(themeInflater, container, savedInstanceState)
        val colorValue = TypedValue()
        themedContext.theme.resolveAttribute(R.attr.listBackgroundColor, colorValue, true)
        view?.setBackgroundColor(colorValue.data)
        return view
    }

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
        langPrefs?.setOnPreferenceChangeListener { _, value ->
            setLocale(value.toString())
            true
        }

        val clearPref = findPreference<Preference>("clear")
        clearPref?.setOnPreferenceClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.settings_clear_warning)
                .setPositiveButton(R.string.yes) { _, _ -> viewModel.clearData() }
                .setNegativeButton(R.string.no) { _, _ -> }
                .create()
                .show()
            true
        }

        val themePref = findPreference<SwitchPreferenceCompat>("theme")
        themePref?.setOnPreferenceChangeListener { _, value ->
            value as Boolean
            if (value) {
                setTheme(R.style.AppTheme_Dark)
            } else {
                setTheme(R.style.AppTheme)
            }
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

    private fun setLocale(code: String) {
        val locale = Locale(code)
        val config = requireActivity().baseContext.resources.configuration
        Locale.setDefault(locale)
        config.setLocale(locale)
        requireActivity().baseContext.resources.updateConfiguration(
            config,
            requireActivity().baseContext.resources.displayMetrics
        )
        requireActivity().supportFragmentManager
            .beginTransaction()
            .remove(this)
            .add(R.id.fragment, SettingsFragment())
            .commitAllowingStateLoss()
    }

    private fun setTheme(@StyleRes theme: Int) {
        requireActivity().setTheme(theme)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .detach(this)
            .attach(this)
            .commit()
    }
}