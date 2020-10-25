package com.test.tddandroid.views.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.test.tddandroid.R
import com.test.tddandroid.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.item_image.*

class AddShoppingItemFragment:Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel: MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        ivShoppingImage.setOnClickListener{
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )

        }
        val backPressCallBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setImageUrl("")
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressCallBack)

    }
}