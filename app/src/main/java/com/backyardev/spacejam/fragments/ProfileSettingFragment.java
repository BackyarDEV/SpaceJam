package com.backyardev.spacejam.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backyardev.spacejam.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ProfileSettingFragment extends Fragment implements View.OnClickListener {

    private MaterialButton editDp;
    private FloatingActionButton updFab,backFabProfile;
    private TextInputEditText username, website,description,email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_profile_setting,container,false );

        editDp = view.findViewById( R.id.changeProfilePhoto );
        updFab = view.findViewById( R.id.updFab );
        backFabProfile = view.findViewById( R.id.backFabProfile );
        username = view.findViewById( R.id.username );
        website = view.findViewById( R.id.website);
        description = view.findViewById( R.id.description );
        email = view.findViewById( R.id.email );
        
        editDp.setOnClickListener( this );
        updFab.setOnClickListener( this );
        backFabProfile.setOnClickListener( this );
        return view;
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.changeProfilePhoto:
                changeDP();
                break;
            case R.id.updFab:
                updFab();
                break;
            case R.id.backFabProfile:
                backFabProfile();
                break;
        }
    }

    private void backFabProfile() {

        MainSettingsFragment fragment = new MainSettingsFragment();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull( getActivity() ).
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
        fragmentTransaction.replace( R.id.settings_container, fragment, "MainSettings" );
        fragmentTransaction.commit();
    }

    private void changeDP() {

    }

    private void updFab() {
        email.setEnabled( true );
        username.setEnabled( true );
        description.setEnabled( true );
        website.setEnabled( true );

        updFab.setImageDrawable( getActivity().getDrawable( R.drawable.ic_file_upload_black ) );

    }
}