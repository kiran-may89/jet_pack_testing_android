package com.test.tddandroid.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.test.tddandroid.adapters.ImageAdapter
import com.test.tddandroid.adapters.ShoppingItemAdapter
import com.test.tddandroid.repository.FakeRepository
import com.test.tddandroid.viewmodel.MainViewModel
import com.test.tddandroid.views.fragments.AddShoppingItemFragment
import com.test.tddandroid.views.fragments.ImagePickFragment
import com.test.tddandroid.views.fragments.ShoppingFragment
import org.junit.Assert.*
import javax.inject.Inject

class ShoppingFactoryTest @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
):FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(
                shoppingItemAdapter,
                MainViewModel(FakeRepository())
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}