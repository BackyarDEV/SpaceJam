package com.backyardev.spacejam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private static final String TAG = "HomeFragment";


    public interface OnGridImageSelectedListener {
        void onGridImageSelected(Photo photo, int activityNumber);
    }


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private MaterialCardView cardView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_settings, container, false );

        return view;
    }
}
