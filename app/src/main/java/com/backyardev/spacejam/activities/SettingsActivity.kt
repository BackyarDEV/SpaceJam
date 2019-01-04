package com.backyardev.spacejam.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import android.os.Bundle

import com.backyardev.spacejam.R
import com.backyardev.spacejam.fragments.MainSettingsFragment
import com.backyardev.spacejam.fragments.ProfileSettingFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setMainFragment()
    }

    private fun setMainFragment() {
        run {
            val fragment = MainSettingsFragment()
            supportFragmentManager.beginTransaction().add(R.id.settings_container, fragment).commit()
        }
        getIncomingIntent()
    }

    private fun getIncomingIntent() {
        val intent = intent
        if (intent.hasExtra(getString(R.string.calling_activity))) {
            getFragment(intent.getStringExtra(getString(R.string.calling_activity)))
        }
    }

    private fun getFragment(activity: String) {
        if (activity == "ProfileSettingsFragment") {
            val profileSettingFragment = ProfileSettingFragment()
            CURR_TAG = TAG_PROFILE_SETTING
            setFragment(profileSettingFragment, CURR_TAG)
        }
    }

    private fun setFragment(fragment: Fragment, TAG: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        fragmentTransaction.replace(R.id.settings_container, fragment, TAG)
        if (supportFragmentManager.findFragmentByTag(TAG) == null) {
            fragmentTransaction.addToBackStack(TAG)
        }
        fragmentTransaction.commit()
    }

    companion object {
        private val TAG_PROFILE_SETTING = "profileSettings"
        private val TAG_MAIN_SETTING = "mainSettings"
        private var CURR_TAG = TAG_MAIN_SETTING
    }
}