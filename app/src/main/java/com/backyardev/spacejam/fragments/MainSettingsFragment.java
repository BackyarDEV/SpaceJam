package com.backyardev.spacejam.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backyardev.spacejam.R;
import com.backyardev.spacejam.activities.HomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;


public class MainSettingsFragment extends Fragment implements View.OnClickListener {

    String[] setting_array;
    ListView settingsListView;
    private static String TAG_PROFILE_SETTING = "profile_setting";
    private FloatingActionButton backFab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_main_settings, container, false );
        settingsListView = view.findViewById( R.id.settingsListView );
        backFab = view.findViewById( R.id.backFab );

        backFab.setOnClickListener( this );

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        setting_array = Objects.requireNonNull( getActivity() ).getResources().getStringArray( R.array.settings_list );
        settingsListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> adapterView, View view1, int position, long l) {
                getFragmentById( position );
            }
        } );
        ArrayAdapter <String> arrayAdapter = new ArrayAdapter <String>( getActivity(), android.R.layout.simple_list_item_1, setting_array );
        settingsListView.setAdapter( arrayAdapter );

    }

    private void getFragmentById(int id) {

        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById( R.id.settings_container );
        switch (id) {
            case 0:
                ProfileSettingFragment profileSettingFragment = new ProfileSettingFragment();
                if (!(currentFragment instanceof ProfileFragment)) {
                    getFragment( profileSettingFragment, TAG_PROFILE_SETTING );
                } else getFragment( null, TAG_PROFILE_SETTING );
                break;
            case 1:
                AccountSettingsFragment accountSettingsFragment = new AccountSettingsFragment();
                if (!(currentFragment instanceof ProfileFragment)) {
                    getFragment( accountSettingsFragment, TAG_PROFILE_SETTING );
                } else getFragment( null, TAG_PROFILE_SETTING );
                break;
            case 2:
                NotificationSettingsFragment notificationSettingsFragment = new NotificationSettingsFragment();
                if (!(currentFragment instanceof ProfileFragment)) {
                    getFragment( notificationSettingsFragment, TAG_PROFILE_SETTING );
                } else getFragment( null, TAG_PROFILE_SETTING );
                break;
            case 3:
                ApplicationSettingsFragment applicationSettingsFragment = new ApplicationSettingsFragment();
                if (!(currentFragment instanceof ProfileFragment)) {
                    getFragment( applicationSettingsFragment, TAG_PROFILE_SETTING );
                } else getFragment( null, TAG_PROFILE_SETTING );
                break;
            case 4:
                PrivacySettingsFragment privacySettingsFragment = new PrivacySettingsFragment();
                if (!(currentFragment instanceof ProfileFragment)) {
                    getFragment( privacySettingsFragment, TAG_PROFILE_SETTING );
                } else getFragment( null, TAG_PROFILE_SETTING );
                break;
        }
    }

    private void getFragment(Fragment fragment, String TAG) {
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = Objects.requireNonNull( getActivity() ).
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
            fragmentTransaction.replace( R.id.settings_container, fragment, TAG );
            fragmentTransaction.addToBackStack( TAG_PROFILE_SETTING );
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.backFab:
                backFabAction();
                break;
            default:
                return;
        }
    }



    private void backFabAction() {
        Intent i = new Intent( getActivity(), HomeActivity.class );
        startActivity( i );
    }
}