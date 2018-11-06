package com.backyardev.spacejam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile( "^" + "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            ".{8,}" +               //at least 8 characters
            "$" );

    Button btnRegister;
    TextInputEditText nameText, passText, eMailText, usernameText;
    ProgressBar regProgress;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String emailInput, passwordInput, nameInput;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setTheme( R.style.AppTheme_Auth );
        setContentView( R.layout.activity_register );

        btnRegister = findViewById( R.id.btnRegister );

        nameText = findViewById( R.id.fullNameText );
        passText = findViewById( R.id.PassText );
        eMailText = findViewById( R.id.eMailText );
        regProgress = findViewById( R.id.regProgress );

        regProgress.getIndeterminateDrawable().setColorFilter( getResources().getColor( R.color.colorPrimary ), PorterDuff.Mode.MULTIPLY );

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailInput = eMailText.getText().toString().trim();
                passwordInput = passText.getText().toString().trim();
                nameInput = nameText.getText().toString().trim();
                if (!validateEmail( emailInput ) | !validateUsername( nameInput ) | !validatePassword( passwordInput )) {
                    return;
                }
                registerData(emailInput, passwordInput, nameInput );
            }
        } );
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d( "emailTag", "user is signed in" );
                    sendVerificationEmail();
                } else {
                    Log.d( "emailTag", "user not signed in" );
                }
            }
        };
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener( new OnCompleteListener <Void>() {
            @Override
            public void onComplete(@NonNull Task <Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText( RegisterActivity.this, "A verification mail has been sent!\nPlease Verify your email...", Toast.LENGTH_LONG ).show();
                    FirebaseAuth.getInstance().signOut();
                    startActivity( new Intent( RegisterActivity.this, LoginActivity.class ) );
                    finish();

                } else {
                    Toast.makeText( RegisterActivity.this, "Couldn't send verification email!", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    private boolean validateEmail(String email) {

        if (email.isEmpty()) {
            eMailText.setError( "Field can't be empty" );
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            eMailText.setError( "Please enter a valid email address" );
            return false;
        } else {
            eMailText.setError( null );
            return true;
        }
    }

    private boolean validateUsername(String name) {


        if (name.isEmpty()) {
            nameText.setError( "Field can't be empty" );
            return false;
        } else {
            nameText.setError( null );
            return true;
        }
    }

    private boolean validatePassword(String pass) {


        if (pass.isEmpty()) {
            passText.setError( "Field can't be empty" );
            return false;
        } else if (!PASSWORD_PATTERN.matcher( pass ).matches()) {
            passText.setError( "Password too weak" );
            return false;
        } else {
            passText.setError( null );
            return true;
        }
    }

    public void registerData( final String mail, final String pass, final String name) {

        regProgress.setVisibility( View.VISIBLE );
        btnRegister.setVisibility( View.INVISIBLE );
        btnRegister.setEnabled( false );

                    mAuth.createUserWithEmailAndPassword( mail, pass ).addOnCompleteListener( new OnCompleteListener <AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task <AuthResult> task) {
                            if (task.isSuccessful()) {
                                uploadData( mail, name );
                            } else {
                                regProgress.setVisibility( View.INVISIBLE );
                                btnRegister.setEnabled( true );
                                btnRegister.setVisibility( View.VISIBLE );
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    Toast.makeText( RegisterActivity.this, "Account already exists!\nLogin to continue...", Toast.LENGTH_SHORT ).show();
                                    Intent i = new Intent( RegisterActivity.this, LoginActivity.class );
                                    startActivity( i );
                                } catch (Exception e) {
                                    Log.d( "registration exceptions", String.valueOf( e ) );
                                }
                            }
                        }
        } );
    }

    public void uploadData(final String mail, final String name) {

        Map <String, Object> user = new HashMap <>();
        user.put( "name", name );
        user.put( "email", mail );
        CollectionReference dbCollection = db.collection( "users" );
        dbCollection.add( user ).addOnCompleteListener( new OnCompleteListener <DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task <DocumentReference> task) {
                if (!task.isSuccessful()) {
                    regProgress.setVisibility( View.INVISIBLE );
                    btnRegister.setEnabled( true );
                    btnRegister.setVisibility( View.VISIBLE );
                    Toast.makeText( RegisterActivity.this, "Database Write Failed!", Toast.LENGTH_SHORT ).show();
                } else {
                    regProgress.setVisibility( View.INVISIBLE );
                    btnRegister.setEnabled( true );
                    btnRegister.setVisibility( View.VISIBLE );
                    sendVerificationEmail();
                }
            }
        } );
    }

    public void alreadyAction(View view) {
        view.setEnabled( false );
        Intent i = new Intent( RegisterActivity.this, LoginActivity.class );
        startActivity( i );
        finish();
    }
}