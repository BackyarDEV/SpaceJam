package com.backyardev.spacejam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.backyardev.spacejam.R;
import com.backyardev.spacejam.fragments.MainSettingsFragment;
import com.backyardev.spacejam.fragments.ProfileSettingFragment;

public class SettingsActivity extends AppCompatActivity {

    private static String TAG_PROFILE_SETTING="profileSettings";
    private static String TAG_MAIN_SETTING="mainSettings";
    private static String CURR_TAG=TAG_MAIN_SETTING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );
        setMainFragment();
    }

    private void setMainFragment() {
        {
            MainSettingsFragment fragment = new MainSettingsFragment();
            getSupportFragmentManager().beginTransaction().add( R.id.settings_container, fragment ).commit();
        }
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra( getString( R.string.calling_activity ) )) {
            getFragment(intent.getStringExtra( getString( R.string.calling_activity ) ));
        }
    }

    private void getFragment(String activity){
        if(activity.equals( "ProfileSettingsFragment" )){
            Toast.makeText( this,activity,Toast.LENGTH_SHORT ).show();
            ProfileSettingFragment profileSettingFragment = new ProfileSettingFragment();
            CURR_TAG=TAG_PROFILE_SETTING;
            setFragment( profileSettingFragment,CURR_TAG );
        }

    }
    

    private void setFragment(Fragment fragment, String TAG){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
        fragmentTransaction.replace( R.id.settings_container, fragment, TAG );
        if (getSupportFragmentManager().findFragmentByTag( TAG ) == null) {
            fragmentTransaction.addToBackStack( TAG );
        }
        fragmentTransaction.commit();
    }
}