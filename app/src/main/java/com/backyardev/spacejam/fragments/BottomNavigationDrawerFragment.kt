package com.backyardev.spacejam.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.backyardev.spacejam.activities.LoginActivity
import com.backyardev.spacejam.R
import com.backyardev.spacejam.activities.SettingsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

import java.util.Objects
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import de.hdodenhof.circleimageview.CircleImageView


class BottomNavigationDrawerFragment : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var navigation_view: NavigationView
    private lateinit var close_imageView: ImageView
    private lateinit var circleImageView: CircleImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        navigation_view = view.findViewById(R.id.navigation_view)
        close_imageView = view.findViewById(R.id.close_imageView)
        circleImageView = view.findViewById(R.id.imageView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        close_imageView.setOnClickListener(this)
        circleImageView.setOnClickListener(this)
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> logoutAction()
                R.id.nav_discover -> discoverClick()
                R.id.nav_prefs -> preferenceClick()
                R.id.nav_notifs -> notificationClick()
                R.id.nav_profile -> profileClick()
                R.id.nav_home -> homeClick()
            }
            true
        }
    }

    private fun logoutAction() {
        FirebaseAuth.getInstance().signOut()
        val i = Intent(activity, LoginActivity::class.java)
        startActivity(i)
        Objects.requireNonNull<FragmentActivity>(activity).finish()
    }

    private fun homeClick() {
        val fragment = HomeFragment()
        getFragment(fragment, TAG_HOME)
    }

    private fun profileClick() {
        val fragment = ProfileFragment()
        getFragment(fragment, TAG_PROFILE)
    }

    private fun notificationClick() {
        val fragment = NotificationFragment()
        getFragment(fragment, TAG_NOTIFICATIONS)
    }

    private fun preferenceClick() {
        val i = Intent(activity, SettingsActivity::class.java)
        startActivity(i)
        this.dismiss()
    }

    private fun discoverClick() {
        val fragment = DiscoverFragment()
        getFragment(fragment, TAG_DISCOVER)
    }

    private fun getFragment(fragment: Fragment, TAG: String) {
        val fragmentTransaction = Objects.requireNonNull<FragmentActivity>(activity).getSupportFragmentManager().beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        fragmentTransaction.replace(R.id.home_container, fragment, TAG)
        if (activity!!.supportFragmentManager.findFragmentByTag(TAG) == null) {
            fragmentTransaction.addToBackStack(TAG)
        }
        fragmentTransaction.commit()
        this.dismiss()

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.close_imageView -> this.dismiss()
            R.id.imageView -> profileClick()
        }
    }

    companion object {
        private val TAG_HOME = "home"
        private val TAG_PROFILE = "profile"
        private val TAG_DISCOVER = "discover"
        private val TAG_NOTIFICATIONS = "notifications"
    }
}