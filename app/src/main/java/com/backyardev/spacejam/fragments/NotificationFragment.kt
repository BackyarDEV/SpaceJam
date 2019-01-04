package com.backyardev.spacejam.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.backyardev.spacejam.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import androidx.fragment.app.Fragment

class NotificationFragment : Fragment() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    private val cardView: MaterialCardView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    companion object {
        private val TAG = "NotificationFragment"
    }
}
