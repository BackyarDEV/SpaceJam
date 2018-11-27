package com.backyardev.spacejam.fragments;

import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backyardev.spacejam.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HomeFragment";


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private MaterialCardView cardView;
    private SwipeRefreshLayout homeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_home, container, false );
        homeRefreshLayout = view.findViewById( R.id.homeSwipeRefresh );
        homeRefreshLayout.setOnRefreshListener( this );
        return view;
    }

    @Override
    public void onRefresh() {
        new MyTask().execute();
    }

    private class MyTask {

        void execute() {
            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    if (homeRefreshLayout.isRefreshing()) {
                        homeRefreshLayout.setRefreshing( false );
                    }
                }
            }, 2000 );
        }
    }
}