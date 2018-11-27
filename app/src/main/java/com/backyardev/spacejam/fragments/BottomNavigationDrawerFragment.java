package com.backyardev.spacejam.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.backyardev.spacejam.activities.LoginActivity;
import com.backyardev.spacejam.R;
import com.backyardev.spacejam.activities.SettingsActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;


public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private NavigationView navigation_view;
    private ImageView close_imageView;
    private CircleImageView circleImageView;
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_DISCOVER = "discover";
    private static final String TAG_NOTIFICATIONS = "notifications";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_bottom_sheet, container, false );
        navigation_view = view.findViewById( R.id.navigation_view );
        close_imageView = view.findViewById( R.id.close_imageView );
        circleImageView = view.findViewById( R.id.imageView );
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        close_imageView.setOnClickListener( this );
        circleImageView.setOnClickListener( this );
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
        getFragment( fragment, TAG_HOME );
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
        Intent i = new Intent( getActivity(), SettingsActivity.class );
        startActivity( i );
        this.dismiss();
    }

    private void discoverClick() {
        DiscoverFragment fragment = new DiscoverFragment();
        getFragment( fragment, TAG_DISCOVER );
    }

    private void getFragment(Fragment fragment, String TAG) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull( getActivity() ).
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
        fragmentTransaction.replace( R.id.home_container, fragment, TAG );
        if (getActivity().getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            fragmentTransaction.addToBackStack(TAG);
        }
        fragmentTransaction.commit();
        this.dismiss();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_imageView:
                this.dismiss();
                break;
            case R.id.imageView:
                profileClick();
                break;
        }
    }
}