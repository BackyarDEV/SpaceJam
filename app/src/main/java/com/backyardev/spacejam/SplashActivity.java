package com.backyardev.spacejam;

import android.content.Intent;
import android.os.Bundle;

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
            Intent i = new Intent( SplashActivity.this, RegisterActivity.class );
            startActivity( i );
            finish();
        }
    }
}