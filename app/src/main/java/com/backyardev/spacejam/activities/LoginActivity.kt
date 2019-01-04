package com.backyardev.spacejam.activities

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.backyardev.spacejam.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var forgotPass: TextView
    private lateinit var instead: TextView
    private lateinit var eMailText: TextInputEditText
    private lateinit var userMailText: TextInputEditText
    private lateinit var userPassText: TextInputEditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mail: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_Auth)
        setContentView(R.layout.activity_login)

        userPassText = findViewById(R.id.userPassText)
        userMailText = findViewById(R.id.userMailText)
        progressBar = findViewById(R.id.progressBar)

        btnLogin = findViewById(R.id.btnLogin)
        instead = findViewById(R.id.createAccText)
        forgotPass = findViewById(R.id.forgotText)

        mAuth = FirebaseAuth.getInstance()
        progressBar.indeterminateDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.MULTIPLY)

        btnLogin.setOnClickListener {
            var submit = true
            mail = userMailText.text!!.toString()
            pass = userPassText.text!!.toString()

            if (mail.isEmpty()) {
                userMailText.error = "Required Field!"
                submit = false
            }
            if (pass.isEmpty()) {
                userPassText.error = "Required Field!"
                submit = false
            }
            if (submit) {
                submitLogin(mail, pass)
            }
        }
    }

    private fun submitLogin(mail: String, pass: String) {
        progressBar.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE
        btnLogin.isEnabled = false
        mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                progressBar.visibility = View.INVISIBLE
                btnLogin.isEnabled = true
                btnLogin.visibility = View.VISIBLE
                try {
                    Log.d("loginTest", task.exception.toString())
                    throw task.exception!!

                } catch (e: FirebaseAuthInvalidUserException) {
                    Log.d("login test", "onComplete: invalid email")
                    Toast.makeText(this@LoginActivity, "Account not registered yet!", Toast.LENGTH_SHORT).show()
                    instead.visibility = View.VISIBLE
                } catch (wrongPassword: FirebaseAuthInvalidCredentialsException) {
                    Log.d("loginTest", "onComplete: wrong_password")
                    forgotPass.visibility = View.VISIBLE
                    userPassText.error = "Wrong password!"
                    userPassText.requestFocus()
                } catch (e: Exception) {
                    progressBar.visibility = View.INVISIBLE
                    btnLogin.isEnabled = true
                    btnLogin.visibility = View.VISIBLE
                    Log.d("loginTest extra", e.message)
                }

            } else {
                checkIfEmailVerified()
            }
        }
    }

    private fun checkIfEmailVerified() {
        val user = FirebaseAuth.getInstance().currentUser!!

        if (user.isEmailVerified) {
            finish()
            Log.d("loginTest", "signInWithEmail:success")
            val i = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(i)
        } else {
            progressBar.visibility = View.INVISIBLE
            btnLogin.isEnabled = true
            btnLogin.visibility = View.VISIBLE
            val builder = AlertDialog.Builder(this,R.style.AlertDialogTheme)
            builder.setTitle("Resend Verification mail?")
            builder.setMessage("Your email has not been verified, do you want to resend the verification mail?")
            builder.setPositiveButton("Yes"){ dialog,which ->
                Toast.makeText(this@LoginActivity, "Sending verification mail...", Toast.LENGTH_SHORT).show()
                sendVerificationMail(user)
            }
            builder.setNegativeButton("No"){dialog,which ->
                Toast.makeText(this@LoginActivity, "Verify email to login",Toast.LENGTH_SHORT).show()
            }

            builder.show()
            FirebaseAuth.getInstance().signOut()
        }
    }

    private fun sendVerificationMail(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@LoginActivity, "A verification mail has been sent!\nPlease Verify your email...", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this@LoginActivity, "Couldn't send verification email!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun forgotPassword(view: View) {
        var submit = true
        mail = userMailText.text!!.toString().trim { it <= ' ' }

        if (mail.isEmpty()) {
            userMailText.error = "Required Field!"
            submit = false
        }
        if (submit) {
            view.isEnabled = false
            progressBar.visibility = View.VISIBLE
            btnLogin.visibility = View.INVISIBLE
            btnLogin.isEnabled = false
            val auth = FirebaseAuth.getInstance()
            val emailAddress = userMailText.text!!.toString()
            Log.d("mail", emailAddress)
            auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener { task ->
                progressBar.visibility = View.INVISIBLE
                if (task.isSuccessful) {
                    view.isEnabled = true
                    progressBar.visibility = View.INVISIBLE
                    btnLogin.visibility = View.VISIBLE
                    btnLogin.isEnabled = true
                    Log.d("reset mail", "Email sent.")
                    Toast.makeText(this@LoginActivity, "A password reset email has been sent! Check your inbox", Toast.LENGTH_LONG).show()
                } else {
                    try
                    {
                        throw task.exception!!
                    } catch (e: Exception) {
                        view.isEnabled = true
                        progressBar.visibility = View.INVISIBLE
                        btnLogin.visibility = View.VISIBLE
                        btnLogin.isEnabled = true
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun insteadOnClick(view: View) {
        view.isEnabled = false
        val i = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(i)
        finish()
    }
}