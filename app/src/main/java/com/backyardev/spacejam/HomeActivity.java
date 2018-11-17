package com.backyardev.spacejam;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.google.android.material.bottomappbar.BottomAppBar.FAB_ALIGNMENT_MODE_END;

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
    private int menuItemIndex = 0;
    int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setTheme( R.style.AppTheme );
        setContentView( R.layout.activity_home );

        fab = findViewById( R.id.fab );
        bottomAppBar = findViewById( R.id.bottom_app_bar );
        setSupportActionBar( bottomAppBar );

        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CURRENT_TAG.equals( "home" )) {
                    newPostFabClick();
                } else {
                    mode=0;
                    fabModeChanger();
                    CURRENT_TAG = TAG_HOME;
                    menuItemIndex = 0;
                    loadHomeFragment();
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

                loadHomeFragment();

                return true;
            }
        } );
    }

    private void newPostFabClick() {
        Toast.makeText( this,"New Post under construction",Toast.LENGTH_SHORT).show();
    }


    private Fragment getHomeFragment() {
        switch (menuItemIndex) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                ProfileFragment photosFragment = new ProfileFragment();
                return photosFragment;
            case 2:
                DiscoverFragment discoverFragment = new DiscoverFragment();
                return discoverFragment;
            case 3:
                NotificationFragment notificationFragment = new NotificationFragment();
                return notificationFragment;

            default:
                return new HomeFragment();
        }
    }
    private void profileBtnAction() {
        mode=1;
        fabModeChanger();
        CURRENT_TAG = TAG_PROFILE;
        menuItemIndex = 1;
    }
    private void notificationBtnAction() {
        mode=1;
        fabModeChanger();
        CURRENT_TAG=TAG_NOTIFICATIONS;
        menuItemIndex=2;
    }

    private void discoverBtnAction() {
        mode=1;
        fabModeChanger();
        CURRENT_TAG=TAG_DISCOVER;
        menuItemIndex=3;
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
//
//        bottomNavBarFragment.setArguments(args);
//
//        FragmentTransaction transaction  = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, bottomNavBarFragment);
//        transaction.addToBackStack(getString(R.string.view_post_fragment));
//        transaction.commit();
    }

    protected void loadHomeFragment() {
        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
        fragmentTransaction.replace( R.id.home_container, fragment, CURRENT_TAG );
        fragmentTransaction.commitAllowingStateLoss();
    }
    private void fabModeChanger(){

            bottomAppBar.setFabAlignmentMode( mode );
            if(mode==1){
            fab.setImageDrawable( getDrawable( R.drawable.ic_arrow_back_black_24dp ) );
        } else if(mode ==0){
            fab.setImageDrawable( getDrawable( R.drawable.ic_add_black_24dp ) );
        }
    }
}