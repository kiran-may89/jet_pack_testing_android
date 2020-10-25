package com.test.tddandroid.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.tddandroid.R
import com.test.tddandroid.viewmodel.MainViewModel

class ImagePickFragment:Fragment(R.layout.fragment_image_pick) {
    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
}