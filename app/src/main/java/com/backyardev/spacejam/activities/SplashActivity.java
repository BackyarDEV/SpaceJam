package com.backyardev.spacejam.activities;

import android.content.Intent;
import android.os.Bundle;

import com.backyardev.spacejam.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setTheme( R.style.AppTheme_Splash );
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {

            Intent i = new Intent( SplashActivity.this, HomeActivity.class );
            startActivity( i );
            finish();
        } else {
            Intent i = new Intent( SplashActivity.this, LoginActivity.class );
            startActivity( i );
            finish();
        }
    }
}