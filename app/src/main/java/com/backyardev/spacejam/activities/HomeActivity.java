package com.backyardev.spacejam.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.backyardev.spacejam.util.Photo;
import com.backyardev.spacejam.R;
import com.backyardev.spacejam.fragments.BottomNavigationDrawerFragment;
import com.backyardev.spacejam.fragments.DiscoverFragment;
import com.backyardev.spacejam.fragments.HomeFragment;
import com.backyardev.spacejam.fragments.NotificationFragment;
import com.backyardev.spacejam.fragments.ProfileFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity implements ProfileFragment.OnGridImageSelectedListener {

    protected FloatingActionButton fab;
    protected BottomAppBar bottomAppBar;
    private BottomNavigationDrawerFragment bottomNavBarFragment = new BottomNavigationDrawerFragment();
    private static final String TAG_LOG = "ProfileActivity";
    private static final String TAG_HOME = "home";
    private static final String TAG_FAB = "fab";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_DISCOVER = "discover";
    private static final String TAG_NOTIFICATIONS = "notifications";
    public static String CURRENT_TAG = TAG_HOME;
    private Fragment fragment;
    HomeFragment homeFragment = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setTheme( R.style.AppTheme );
        setContentView( R.layout.activity_home );

        fab = findViewById( R.id.fab );
        bottomAppBar = findViewById( R.id.bottom_app_bar );
        setSupportActionBar( bottomAppBar );

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.home_container,homeFragment,TAG_HOME ).commit();

        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CURRENT_TAG.equals( TAG_HOME )) {
                    newPostFabClick();
                } else {
                    CURRENT_TAG = TAG_HOME;
                    loadHomeFragment( fragment, CURRENT_TAG );
                }
            }
        } );
        bottomAppBar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomNavBarFragment.show( getSupportFragmentManager(), bottomNavBarFragment.getTag() );
            }
        } );

        bottomAppBar.setOnMenuItemClickListener( new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_app_home:
                        homeButtonAction();
                        break;
                    case R.id.bottom_app_profile:
                        profileBtnAction();
                        break;
                    case R.id.bottom_app_discover:
                        discoverBtnAction();
                        break;
                    case R.id.bottom_app_notifs:
                        notificationBtnAction();
                        break;
                }

                if (item.isChecked()) {
                    item.setChecked( false );
                } else {
                    item.setChecked( true );
                }
                item.setChecked( true );

                loadHomeFragment( fragment, CURRENT_TAG );

                return true;
            }
        } );
    }

    private void newPostFabClick() {
        Toast.makeText( this, "New Post under construction", Toast.LENGTH_SHORT ).show();
    }

    private void homeButtonAction() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById( R.id.home_container );
        CURRENT_TAG = TAG_HOME;
        if (!(currentFragment instanceof HomeFragment)) {
            fragment = homeFragment;
        } else fragment = null;
    }

    private void profileBtnAction() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById( R.id.home_container );
        CURRENT_TAG = TAG_PROFILE;
        if (!(currentFragment instanceof ProfileFragment)) {
            ProfileFragment profileFragment = new ProfileFragment();
            fragment = profileFragment;
        } else fragment = null;
    }


    private void notificationBtnAction() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById( R.id.home_container );
        CURRENT_TAG = TAG_NOTIFICATIONS;
        if (!(currentFragment instanceof NotificationFragment)) {
            NotificationFragment notificationFragment = new NotificationFragment();
            fragment = notificationFragment;
        } else fragment = null;
    }

    private void discoverBtnAction() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById( R.id.home_container );
        CURRENT_TAG = TAG_DISCOVER;
        if (!(currentFragment instanceof DiscoverFragment)) {
            DiscoverFragment discoverFragment = new DiscoverFragment();
            fragment = discoverFragment;
        } else fragment = null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.bottom_app_bar, menu );
        return true;
    }

    @Override
    public void onGridImageSelected(Photo photo, int activityNumber) {
        Log.d( TAG_LOG, "onGridImageSelected: selected an image gridview: " + photo.toString() );
//        ViewPostFragment bottomNavBarFragment = new ViewPostFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(getString(R.string.photo), photo);
//        args.putInt(getString(R.string.activity_number), activityNumber);
    }

    protected void loadHomeFragment(Fragment newFragment, String TAG) {
        if (newFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
            fragmentTransaction.replace( R.id.home_container, newFragment, CURRENT_TAG );
            if (getSupportFragmentManager().findFragmentByTag( TAG ) == null) {
                fragmentTransaction.addToBackStack( TAG );
            }
            fragmentTransaction.commit();
        }
    }
}