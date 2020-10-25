package com.test.tddandroid.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.test.tddandroid.adapters.ImageAdapter
import com.test.tddandroid.views.fragments.ImagePickFragment
import javax.inject.Inject

class ShoppingFactory @Inject constructor(  private val imageAdapter: ImageAdapter):FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name->
                ImagePickFragment(imageAdapter)
                else ->super.instantiate(classLoader, className)
        }
    }
}