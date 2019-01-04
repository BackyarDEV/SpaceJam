package com.backyardev.spacejam.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast

import com.backyardev.spacejam.util.Photo
import com.backyardev.spacejam.R
import com.backyardev.spacejam.fragments.BottomNavigationDrawerFragment
import com.backyardev.spacejam.fragments.DiscoverFragment
import com.backyardev.spacejam.fragments.HomeFragment
import com.backyardev.spacejam.fragments.NotificationFragment
import com.backyardev.spacejam.fragments.ProfileFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class HomeActivity : AppCompatActivity(), ProfileFragment.OnGridImageSelectedListener {

    private lateinit var fab: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar
    private val bottomNavBarFragment = BottomNavigationDrawerFragment()
    private var fragment: Fragment? = null
    private var homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_home)

        fab = findViewById(R.id.fab)
        bottomAppBar = findViewById(R.id.bottom_app_bar)
        setSupportActionBar(bottomAppBar)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.home_container, homeFragment, TAG_HOME).commit()

        fab.setOnClickListener { newPostFabClick() }
        bottomAppBar.setNavigationOnClickListener { bottomNavBarFragment.show(supportFragmentManager, bottomNavBarFragment.tag) }

        bottomAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.bottom_app_home -> homeButtonAction()
                R.id.bottom_app_profile -> profileBtnAction()
                R.id.bottom_app_discover -> discoverBtnAction()
                R.id.bottom_app_notifs -> notificationBtnAction()
            }

            item.isChecked = !item.isChecked
            item.isChecked = true

            loadHomeFragment(fragment, CURRENT_TAG)

            true
        }
    }

    private fun newPostFabClick() {
        Toast.makeText(this, "New Post under construction", Toast.LENGTH_SHORT).show()
    }

    private fun homeButtonAction() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        CURRENT_TAG = TAG_HOME
        if (currentFragment !is HomeFragment) {
            fragment = homeFragment
        } else
            fragment = null
    }

    private fun profileBtnAction() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        CURRENT_TAG = TAG_PROFILE
        if (currentFragment !is ProfileFragment) {
            val profileFragment = ProfileFragment()
            fragment = profileFragment
        } else
            fragment = null
    }


    private fun notificationBtnAction() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        CURRENT_TAG = TAG_NOTIFICATIONS
        if (currentFragment !is NotificationFragment) {
            val notificationFragment = NotificationFragment()
            fragment = notificationFragment
        } else
            fragment = null
    }

    private fun discoverBtnAction() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.home_container)
        CURRENT_TAG = TAG_DISCOVER
        if (currentFragment !is DiscoverFragment) {
            val discoverFragment = DiscoverFragment()
            fragment = discoverFragment
        } else
            fragment = null
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_app_bar, menu)
        return true
    }

    override fun onGridImageSelected(photo: Photo, activityNumber: Int) {
        Log.d(TAG_LOG, "onGridImageSelected: selected an image gridview: " + photo.toString())
        //        ViewPostFragment bottomNavBarFragment = new ViewPostFragment();
        //        Bundle args = new Bundle();
        //        args.putParcelable(getString(R.string.photo), photo);
        //        args.putInt(getString(R.string.activity_number), activityNumber);
    }

    protected fun loadHomeFragment(newFragment: Fragment?, TAG: String) {
        if (newFragment != null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.home_container, newFragment, CURRENT_TAG)
            if (supportFragmentManager.findFragmentByTag(TAG) == null) {
                fragmentTransaction.addToBackStack(TAG)
            }
            fragmentTransaction.commit()
        }
    }

    companion object {
        private val TAG_LOG = "ProfileActivity"
        private val TAG_HOME = "home"
        private val TAG_FAB = "fab"
        private val TAG_PROFILE = "profile"
        private val TAG_DISCOVER = "discover"
        private val TAG_NOTIFICATIONS = "notifications"
        private var CURRENT_TAG = TAG_HOME
    }
}