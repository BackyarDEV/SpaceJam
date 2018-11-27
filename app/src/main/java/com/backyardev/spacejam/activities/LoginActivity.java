package com.backyardev.spacejam.activities;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backyardev.spacejam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    ProgressBar progressBar, regProgress;
    public TextView forgotPass, instead;
    TextInputEditText eMailText, userMailText, userPassText;
    public FirebaseAuth mAuth;
    String mail, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setTheme( R.style.AppTheme_Auth );
        setContentView( R.layout.activity_login );

        userPassText = findViewById( R.id.userPassText );
        userMailText = findViewById( R.id.userMailText );
        progressBar = findViewById( R.id.progressBar );

        btnLogin = findViewById( R.id.btnLogin );
        instead = findViewById( R.id.createAccText );
        forgotPass = findViewById( R.id.forgotText );

        eMailText = findViewById( R.id.eMailText );
        mAuth = FirebaseAuth.getInstance();
        progressBar.getIndeterminateDrawable().setColorFilter( getResources().getColor( R.color.colorPrimary ), PorterDuff.Mode.MULTIPLY );

    }

    public void login(View v) {
        boolean submit = true;
        mail = userMailText.getText().toString().trim();
        pass = userPassText.getText().toString().trim();

        if (mail.isEmpty()) {
            userMailText.setError( "Required Field!" );
            submit = false;
        }
        if (pass.isEmpty()) {
            userPassText.setError( "Required Field!" );
            submit = false;
        }
        if (submit) {
            submitLogin( mail, pass );
        }
    }

    public void submitLogin(final String mail, final String pass) {
        progressBar.setVisibility( View.VISIBLE );
        btnLogin.setVisibility( View.INVISIBLE );
        btnLogin.setEnabled( false );
        mAuth.signInWithEmailAndPassword( mail, pass ).addOnCompleteListener( new OnCompleteListener <AuthResult>() {
            @Override
            public void onComplete(@NonNull Task <AuthResult> task) {
                if (!task.isSuccessful()) {
                    progressBar.setVisibility( View.INVISIBLE );
                    btnLogin.setEnabled( true );
                    btnLogin.setVisibility( View.VISIBLE );
                    try {
                        Log.d( "loginTest", String.valueOf( task.getException() ) );
                        throw Objects.requireNonNull( task.getException() );

                    } catch (FirebaseAuthInvalidUserException e) {
                        Log.d( "login test", "onComplete: invalid email" );
                        Toast.makeText( LoginActivity.this, "Account not registered yet!", Toast.LENGTH_SHORT ).show();
                        instead.setVisibility( View.VISIBLE );
                    } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                        Log.d( "loginTest", "onComplete: wrong_password" );
                        forgotPass.setVisibility( View.VISIBLE );
                        userPassText.setError( "Wrong password!" );
                        userPassText.requestFocus();
                    } catch (Exception e) {
                        Log.d( "loginTest extra", e.getMessage() );
                    }
                } else {
                    checkIfEmailVerified();
                }
            }
        } );
    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (user.isEmailVerified()) {
            finish();
            Log.d( "loginTest", "signInWithEmail:success" );
            Intent i = new Intent( LoginActivity.this, HomeActivity.class );
            startActivity( i );
        } else {
            Toast.makeText( LoginActivity.this, "Email not verified yet!\nCheck your emails...", Toast.LENGTH_SHORT ).show();
            FirebaseAuth.getInstance().signOut();
        }
    }

    public void forgotPassword(final View view) {
        boolean submit = true;
        mail = userMailText.getText().toString().trim();

        if (mail.isEmpty()) {
            userMailText.setError( "Required Field!" );
            submit = false;
        }
        if (submit) {
            view.setEnabled( false );
            progressBar.setVisibility( View.VISIBLE );
            btnLogin.setVisibility( View.INVISIBLE );
            btnLogin.setEnabled( false );
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = userMailText.getText().toString();
            Log.d( "mail", emailAddress );
            auth.sendPasswordResetEmail( emailAddress ).addOnCompleteListener( new OnCompleteListener <Void>() {
                @Override
                public void onComplete(@NonNull Task <Void> task) {
                    progressBar.setVisibility( View.INVISIBLE );
                    if (task.isSuccessful()) {
                        view.setEnabled( true );
                        progressBar.setVisibility( View.INVISIBLE );
                        btnLogin.setVisibility( View.VISIBLE );
                        btnLogin.setEnabled( true );
                        Log.d( "reset mail", "Email sent." );
                        Toast.makeText( LoginActivity.this, "A password reset email has been sent! Check your inbox", Toast.LENGTH_LONG ).show();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e) {
                            view.setEnabled( true );
                            progressBar.setVisibility( View.INVISIBLE );
                            btnLogin.setVisibility( View.VISIBLE );
                            btnLogin.setEnabled( true );
                            Toast.makeText( LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                }
            } );

        }
    }

    public void insteadOnClick(View view) {
        view.setEnabled( false );
        Intent i = new Intent( LoginActivity.this, RegisterActivity.class );
        startActivity( i );
        finish();
    }
}