package com.backyardev.spacejam.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

import com.backyardev.spacejam.R
import com.backyardev.spacejam.activities.HomeActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

import java.util.Objects
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.fragment.app.FragmentTransaction


class MainSettingsFragment : Fragment(), View.OnClickListener {

    private lateinit var setting_array: Array<String>
    private lateinit var settingsListView: ListView
    private var backFab: FloatingActionButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main_settings, container, false)
        settingsListView = view.findViewById(R.id.settingsListView)
        backFab = view.findViewById(R.id.backFab)

        backFab!!.setOnClickListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting_array = Objects.requireNonNull<FragmentActivity>(activity).getResources().getStringArray(R.array.settings_list)
        settingsListView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view1, position, l -> getFragmentById(position) }
        val arrayAdapter = ArrayAdapter(activity!!, android.R.layout.simple_list_item_1, setting_array)
        settingsListView.adapter = arrayAdapter

    }

    private fun getFragmentById(id: Int) {

        val currentFragment = activity!!.supportFragmentManager.findFragmentById(R.id.settings_container)
        when (id) {
            0 -> {
                val profileSettingFragment = ProfileSettingFragment()
                if (currentFragment !is ProfileFragment) {
                    getFragment(profileSettingFragment, TAG_PROFILE_SETTING)
                } else
                    getFragment(null, TAG_PROFILE_SETTING)
            }
            1 -> {
                val accountSettingsFragment = AccountSettingsFragment()
                if (currentFragment !is ProfileFragment) {
                    getFragment(accountSettingsFragment, TAG_PROFILE_SETTING)
                } else
                    getFragment(null, TAG_PROFILE_SETTING)
            }
            2 -> {
                val notificationSettingsFragment = NotificationSettingsFragment()
                if (currentFragment !is ProfileFragment) {
                    getFragment(notificationSettingsFragment, TAG_PROFILE_SETTING)
                } else
                    getFragment(null, TAG_PROFILE_SETTING)
            }
            3 -> {
                val applicationSettingsFragment = ApplicationSettingsFragment()
                if (currentFragment !is ProfileFragment) {
                    getFragment(applicationSettingsFragment, TAG_PROFILE_SETTING)
                } else
                    getFragment(null, TAG_PROFILE_SETTING)
            }
            4 -> {
                val privacySettingsFragment = PrivacySettingsFragment()
                if (currentFragment !is ProfileFragment) {
                    getFragment(privacySettingsFragment, TAG_PROFILE_SETTING)
                } else
                    getFragment(null, TAG_PROFILE_SETTING)
            }
        }
    }

    private fun getFragment(fragment: Fragment?, TAG: String) {
        if (fragment != null) {
            val fragmentTransaction = Objects.requireNonNull<FragmentActivity>(activity).getSupportFragmentManager().beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.settings_container, fragment, TAG)
            fragmentTransaction.addToBackStack(TAG_PROFILE_SETTING)
            fragmentTransaction.commit()
        }
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.backFab -> backFabAction()
            else -> return
        }
    }


    private fun backFabAction() {
        val i = Intent(activity, HomeActivity::class.java)
        startActivity(i)
    }

    companion object {
        private val TAG_PROFILE_SETTING = "profile_setting"
    }
}