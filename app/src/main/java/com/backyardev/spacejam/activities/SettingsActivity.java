package com.backyardev.spacejam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.backyardev.spacejam.R;
import com.backyardev.spacejam.fragments.MainSettingsFragment;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        MainSettingsFragment fragment = new MainSettingsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.settings_container,fragment)
                .commit();
    }
}