package com.backyardev.spacejam.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.backyardev.spacejam.util.Photo
import com.backyardev.spacejam.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import androidx.fragment.app.Fragment

class DiscoverFragment : Fragment() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    private lateinit var cardView: MaterialCardView


    interface OnGridImageSelectedListener {
        fun onGridImageSelected(photo: Photo, activityNumber: Int)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    companion object {
        private val TAG = "DiscoverFragment"
    }
}
