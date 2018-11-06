package com.backyardev.spacejam;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    NavigationView navigation_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_bottom_sheet, container, false );
        navigation_view = view.findViewById( R.id.navigation_view );
        ImageButton close_btn = view.findViewById( R.id.close_sheet_btn );
        close_btn.setOnClickListener( this );
        return view;
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

    private void closeClick() {
        this.dismiss();
    }

    private void logoutAction() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent( getActivity(), LoginActivity.class );
        startActivity( i );
        Objects.requireNonNull( getActivity() ).finish();
    }

    private void profileClick() {
        Toast.makeText( getContext(), "profile unavailable!", Toast.LENGTH_SHORT ).show();
    }

    private void notificationClick() {
        Toast.makeText( getContext(), "notifications unavailable!", Toast.LENGTH_SHORT ).show();
    }

    private void homeClick() {
        Toast.makeText( getContext(), "home unavailable!", Toast.LENGTH_SHORT ).show();
    }

    private void preferenceClick() {
        Toast.makeText( getContext(), "no prefs unavailable!", Toast.LENGTH_SHORT ).show();
    }

    private void discoverClick() {
        Toast.makeText( getContext(), "disc unavailable!", Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_sheet_btn:
                closeClick();
                break;
        }
    }
}