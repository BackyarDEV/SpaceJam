package com.backyardev.spacejam;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;


public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    NavigationView navigation_view;
    ImageView close_imageView;
    CircleImageView imageView;
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_DISCOVER = "discover";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTING = "settings";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_bottom_sheet, container, false );
        navigation_view = view.findViewById( R.id.navigation_view );
        close_imageView = view.findViewById( R.id.close_imageView );
        imageView = view.findViewById( R.id.imageView );
        close_imageView.setOnClickListener( this );
        imageView.setOnClickListener( this );
        disableNavigationViewScrollbars( navigation_view );

        return view;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new BottomSheetDialog( requireContext(), getTheme() );

        return dialog;
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt( 0 );
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled( false );
            }
        }
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        navigation_view.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_logout:
                        logoutAction();
                        break;
                    case R.id.nav_discover:
                        discoverClick();
                        break;
                    case R.id.nav_prefs:
                        preferenceClick();
                        break;
                    case R.id.nav_notifs:
                        notificationClick();
                        break;
                    case R.id.nav_profile:
                        profileClick();
                        break;
                    case R.id.nav_home:
                        homeClick();
                        break;
                }
                return true;
            }
        } );

    }

    private void logoutAction() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent( getActivity(), LoginActivity.class );
        startActivity( i );
        Objects.requireNonNull( getActivity() ).finish();
    }

    private void homeClick() {
       HomeFragment fragment = new HomeFragment();
       getFragment( fragment,TAG_HOME );
    }

    private void profileClick() {
        ProfileFragment fragment = new ProfileFragment();
        getFragment( fragment, TAG_PROFILE );
    }

    private void notificationClick() {
        NotificationFragment fragment = new NotificationFragment();
        getFragment( fragment, TAG_NOTIFICATIONS );
    }

    private void preferenceClick() {
        SettingsFragment fragment = new SettingsFragment();
        getFragment( fragment, TAG_SETTING );

    }

    private void discoverClick() {
        DiscoverFragment fragment = new DiscoverFragment();
        getFragment( fragment, TAG_DISCOVER );
    }


    public void getFragment(Fragment fragment, String TAG) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull( getActivity() ).
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
        fragmentTransaction.replace( R.id.home_container, fragment, TAG );
        fragmentTransaction.commitAllowingStateLoss();
        this.dismiss();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_imageView:
                this.dismiss();
            case R.id.imageView:
                profileClick();
        }
    }
}