package com.backyardev.spacejam.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.backyardev.spacejam.activities.SettingsActivity;
import com.backyardev.spacejam.util.Photo;
import com.backyardev.spacejam.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileFragment";


    public interface OnGridImageSelectedListener {
        void onGridImageSelected(Photo photo, int activityNumber);
    }

    OnGridImageSelectedListener mOnGridImageSelectedListener;
    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private TextView mPosts, mFollowers, mFollowing, mDisplayName, mUsername, mWebsite, mDescription;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;
    private GridView gridView;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private Button editProfileButton;


    private int mConnectionCount = 0;
    private int mRoomsCount = 0;
    private int mPostsCount = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_profile, container, false );

        editProfileButton = view.findViewById( R.id.btnEditProfile );
        editProfileButton.setOnClickListener( this );
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnEditProfile:
                btnEditProfileAction();
                break;
        }
    }

    private void btnEditProfileAction() {
        Intent intent = new Intent( getActivity(), SettingsActivity.class );
        intent.putExtra( getString( R.string.calling_activity ), getString( R.string.profile_settings_fragment ) );
        startActivity( intent );
    }
}
