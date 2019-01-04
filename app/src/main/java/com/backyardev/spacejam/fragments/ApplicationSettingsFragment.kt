package com.backyardev.spacejam.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.backyardev.spacejam.R
import androidx.fragment.app.Fragment

class ApplicationSettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_application_setting, container, false)
    }
}
