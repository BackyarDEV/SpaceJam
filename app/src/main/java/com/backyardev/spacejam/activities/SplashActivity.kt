package com.backyardev.spacejam.activities

import android.content.Intent
import android.os.Bundle

import com.backyardev.spacejam.R
import com.google.firebase.auth.FirebaseAuth

import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_Splash)
        fAuth = FirebaseAuth.getInstance()
        if (fAuth.currentUser != null) {
            val i = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(i)
            finish()
        } else {
            val i = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}