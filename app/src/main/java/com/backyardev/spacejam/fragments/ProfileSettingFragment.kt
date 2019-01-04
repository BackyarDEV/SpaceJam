package com.backyardev.spacejam.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast

import com.backyardev.spacejam.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.HashMap
import java.util.Objects
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import de.hdodenhof.circleimageview.CircleImageView

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference


class ProfileSettingFragment : Fragment(), View.OnClickListener {

    private lateinit var editDp: MaterialButton
    private lateinit var updFab: FloatingActionButton
    private lateinit var backFabProfile: FloatingActionButton
    private lateinit var cameraFab: FloatingActionButton
    private lateinit var galleryFab: FloatingActionButton
    private lateinit var username: TextInputEditText
    private lateinit var website: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var changePicRel: RelativeLayout
    private lateinit var innerLayout: RelativeLayout
    private lateinit var profile_photo: CircleImageView
    private lateinit var photo: Bitmap
    private lateinit var timestamp: String
    private lateinit var user: String
    private lateinit var uid: String
    private lateinit var storage: StorageReference
    private lateinit var storRef: StorageReference
    private lateinit var db: FirebaseFirestore

    private val imageFile: File?
        get() {
            val f = Objects.requireNonNull<FragmentActivity>(activity).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFiles = f!!.listFiles()

            if (imageFiles == null || imageFiles.size == 0) {
                return null
            }

            var lastModifiedFile = imageFiles[0]
            for (i in 1 until imageFiles.size) {
                if (lastModifiedFile.lastModified() < imageFiles[i].lastModified()) {
                    lastModifiedFile = imageFiles[i]
                }
            }
            return lastModifiedFile
        }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_setting, container, false)

        editDp = view.findViewById(R.id.changeProfilePhoto)
        updFab = view.findViewById(R.id.updFab)
        backFabProfile = view.findViewById(R.id.backFabProfile)
        cameraFab = view.findViewById(R.id.cameraFab)
        galleryFab = view.findViewById(R.id.galleryFab)
        username = view.findViewById(R.id.username)
        website = view.findViewById(R.id.website)
        description = view.findViewById(R.id.description)
        email = view.findViewById(R.id.email)
        changePicRel = view.findViewById(R.id.changePicRel)
        innerLayout = view.findViewById(R.id.innerLayout)
        profile_photo = view.findViewById(R.id.profile_photo)
        user = FirebaseAuth.getInstance().currentUser!!.displayName!!
        uid = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()

        changePicRel.setOnClickListener(this)
        editDp.setOnClickListener(this)
        updFab.setOnClickListener(this)
        backFabProfile.setOnClickListener(this)
        galleryFab.setOnClickListener(this)
        cameraFab.setOnClickListener(this)
        innerLayout.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.changeProfilePhoto -> changeDP()
            R.id.updFab -> updFab()
            R.id.backFabProfile -> backFabProfile()
            R.id.cameraFab -> cameraFab()
            R.id.galleryFab -> galleryFab()
            R.id.changePicRel -> changePicRel()
            R.id.innerLayout -> changeDP()
        }
    }

    private fun changePicRel() {
        changePicRel.isEnabled = false
        changePicRel.visibility = View.INVISIBLE
    }

    private fun cameraFab() {
        dispatchTakePictureIntent()
    }

    private fun galleryFab() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull<FragmentActivity>(activity), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_STORAGE)

        }
        val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(i, GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageFile = imageFile

            photo = BitmapFactory.decodeFile(imageFile!!.absolutePath)
            profile_photo.setImageBitmap(photo)


        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImage = data!!.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = activity!!.contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
            cursor!!.moveToFirst()

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            profile_photo.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }
        changePicRel()
    }

    private fun uploadData() {
        val picName = uid + "_" + timestamp + ".jpg"
        val profilePicture = HashMap<String, String>()
        profilePicture["profile_picture"] = picName
        val dbCollection = db.collection("users")
        dbCollection.add(profilePicture as Map<String, Any>).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(activity, "Database Right Failed!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Profile Photo updated successfully!", Toast.LENGTH_SHORT).show()
                changePicRel()
            }
        }

    }


    private fun backFabProfile() {

        val fragment = MainSettingsFragment()
        val fragmentTransaction = Objects.requireNonNull<FragmentActivity>(activity).getSupportFragmentManager().beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        fragmentTransaction.replace(R.id.settings_container, fragment, "MainSettings")
        fragmentTransaction.commit()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(Objects.requireNonNull<FragmentActivity>(activity).getPackageManager()) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                Log.d("IOlog", ex.message)
            }

            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(activity!!.applicationContext, "com.backyardev.spacejam.fileprovider", photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Objects.requireNonNull<FragmentActivity>(activity).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */)

        val mCurrentPhotoPath = image.absolutePath
        return image
    }

    private fun changeDP() {
        changePicRel.visibility = View.VISIBLE
        changePicRel.isEnabled = true
    }

    private fun updFab() {
        email.isEnabled = true
        username.isEnabled = true
        description.isEnabled = true
        website.isEnabled = true
        updFab.setImageDrawable(activity!!.getDrawable(R.drawable.ic_file_upload_black))
    }

    companion object {
        private val CAMERA_REQUEST_CODE = 1
        private val READ_EXTERNAL_STORAGE = 3
        private val GALLERY_REQUEST_CODE = 2
        private val REQUEST_TAKE_PHOTO = 1
    }
}