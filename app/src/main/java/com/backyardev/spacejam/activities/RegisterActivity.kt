package com.backyardev.spacejam.activities

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.backyardev.spacejam.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

import java.util.HashMap
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailInput: String
    private lateinit var passwordInput: String
    private lateinit var nameInput: String
    private lateinit var nameText: TextInputEditText
    private lateinit var passText: TextInputEditText
    private lateinit var eMailText: TextInputEditText
    private lateinit var regProgress: ProgressBar
    private lateinit var btnRegister: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_Auth)
        setContentView(R.layout.activity_register)

        nameText = findViewById(R.id.fullNameText)
        passText = findViewById(R.id.PassText)
        eMailText = findViewById(R.id.emailText)
        regProgress = findViewById(R.id.regProgress)
        btnRegister = findViewById(R.id.btnRegister)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        regProgress.indeterminateDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY)

        btnRegister.setOnClickListener(View.OnClickListener {
            emailInput = eMailText.text.toString()
            passwordInput = passText.text.toString()
            nameInput = nameText.text.toString()
            if (!validateEmail(emailInput) or !validateUsername(nameInput) or !validatePassword(passwordInput)) {
                return@OnClickListener
            }
            registerData(emailInput, passwordInput, nameInput)
        })

        FirebaseAuth.AuthStateListener { authStateListener ->
            val user = authStateListener.currentUser
            if (user != null) {
                Log.d("emailTag", "Signed in")
                sendVerificationEmail()
            } else {
                Log.d("emailTag", "Signed out")
                Toast.makeText(this, "Null", Toast.LENGTH_LONG).show();
            }
        }
    }

    private fun sendVerificationEmail() {
        val user = mAuth.currentUser
        user!!.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@RegisterActivity, "A verification mail has been sent!\nPlease Verify your email...", Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()

            } else {
                Toast.makeText(this@RegisterActivity, "Couldn't send verification email!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateEmail(email: String): Boolean {

        return if (email.isEmpty()) {
            eMailText.error = "Field can't be empty"
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eMailText.error = "Please enter a valid email address"
            false
        } else {
            eMailText.error = null
            true
        }
    }

    private fun validateUsername(name: String): Boolean {


        return if (name.isEmpty()) {
            nameText.error = "Field can't be empty"
            false
        } else {
            nameText.error = null
            true
        }
    }

    private fun validatePassword(pass: String): Boolean {


        if (pass.isEmpty()) {
            passText.error = "Field can't be empty"
            return false
        } else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
            passText.error = "Password too weak"
            return false
        } else {
            passText.error = null
            return true
        }
    }

    private fun registerData(mail: String, pass: String, name: String) {

        regProgress.visibility = View.VISIBLE
        btnRegister.visibility = View.INVISIBLE
        btnRegister.isEnabled = false

        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                uploadData(mail, name)
            } else {
                regProgress.visibility = View.INVISIBLE
                btnRegister.isEnabled = true
                btnRegister.visibility = View.VISIBLE
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthUserCollisionException) {
                    Toast.makeText(this@RegisterActivity, "Account already exists!\nLogin to continue...", Toast.LENGTH_SHORT).show()
                    val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(i)
                } catch (e: Exception) {
                    Log.d("registration exceptions", e.toString())
                }

            }
        }
    }

    private fun uploadData(mail: String, name: String) {

        val user = HashMap<String, Any>()
        user["name"] = name
        user["email"] = mail
        val dbCollection = db.collection("users")
        dbCollection.add(user).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                regProgress.visibility = View.INVISIBLE
                btnRegister.isEnabled = true
                btnRegister.visibility = View.VISIBLE
                Toast.makeText(this@RegisterActivity, "Database Write Failed!", Toast.LENGTH_SHORT).show()
            } else {
                regProgress.visibility = View.INVISIBLE
                btnRegister.isEnabled = true
                btnRegister.visibility = View.VISIBLE
                Toast.makeText(this, "Sending verification mail...", Toast.LENGTH_LONG).show();
                sendVerificationEmail()
            }
        }
    }

    fun alreadyAction(view: View) {
        view.isEnabled = false
        val i = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(i)
        finish()
    }

    companion object {

        private val PASSWORD_PATTERN = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                ".{8,}" +               //at least 8 characters
                "$")
    }
}