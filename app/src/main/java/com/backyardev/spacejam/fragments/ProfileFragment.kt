package com.backyardev.spacejam.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.backyardev.spacejam.activities.SettingsActivity
import com.backyardev.spacejam.util.Photo
import com.backyardev.spacejam.R
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var mOnGridImageSelectedListener: OnGridImageSelectedListener

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener


    private lateinit var mPosts: TextView
    private lateinit var mFollowers: TextView
    private lateinit var mFollowing: TextView
    private lateinit var mDisplayName: TextView
    private lateinit var mUsername: TextView
    private lateinit var mWebsite: TextView
    private lateinit var mDescription: TextView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mProfilePhoto: CircleImageView
    private lateinit var gridView: GridView
    private lateinit var toolbar: Toolbar
    private lateinit var profileMenu: ImageView
    private lateinit var editProfileButton: Button


    private val mConnectionCount = 0
    private val mRoomsCount = 0
    private val mPostsCount = 0


    interface OnGridImageSelectedListener {
        fun onGridImageSelected(photo: Photo, activityNumber: Int)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        editProfileButton = view.findViewById(R.id.btnEditProfile)
        editProfileButton!!.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.btnEditProfile -> btnEditProfileAction()
        }
    }

    private fun btnEditProfileAction() {
        val intent = Intent(activity, SettingsActivity::class.java)
        intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_settings_fragment))
        startActivity(intent)
    }

    companion object {

        private val TAG = "ProfileFragment"
        private val ACTIVITY_NUM = 4
        private val NUM_GRID_COLUMNS = 3
    }
}
