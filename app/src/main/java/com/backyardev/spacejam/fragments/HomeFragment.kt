package com.backyardev.spacejam.fragments

import android.os.Bundle

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.backyardev.spacejam.R
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    private lateinit var cardView: MaterialCardView
    private lateinit var homeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeRefreshLayout = view.findViewById(R.id.homeSwipeRefresh)
        homeRefreshLayout!!.setOnRefreshListener(this)
        return view
    }

    override fun onRefresh() {
        MyTask().execute()
    }

    private inner class MyTask {

        internal fun execute() {
            Handler().postDelayed({
                if (homeRefreshLayout!!.isRefreshing) {
                    homeRefreshLayout!!.isRefreshing = false
                }
            }, 2000)
        }
    }

    companion object {
        private val TAG = "HomeFragment"
    }
}