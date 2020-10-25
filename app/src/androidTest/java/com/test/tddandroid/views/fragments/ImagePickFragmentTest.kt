package com.test.tddandroid.views.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import com.android.example.github.util.getOrAwaitValue
import com.google.common.truth.ExpectFailure
import com.google.common.truth.Truth.assertThat

import com.test.tddandroid.R
import com.test.tddandroid.adapters.ImageAdapter
import com.test.tddandroid.factory.ShoppingFactory
import com.test.tddandroid.launchFragmentInHiltContainer
import com.test.tddandroid.repository.FakeRepository
import com.test.tddandroid.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_image_pick.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest{
    @get:Rule
    var instantExcution:InstantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var hiltRuler:HiltAndroidRule = HiltAndroidRule(this)
    @Inject
    lateinit var fragmentFactory:ShoppingFactory

    @Before
    fun setup(){
        hiltRuler.inject()
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl(){
        val navController = mock(NavController::class.java)
        val imageUrl = "TEST"
        var testViewModel = MainViewModel(FakeRepository())
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(),navController)
            imageAdapter.images = listOf(imageUrl)
            viewModel = testViewModel
        }
        onView(withId(R.id.rvImages)).perform(RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(0,click()))

        verify(navController).popBackStack()
        assertThat(testViewModel.curlImageUrl.getOrAwaitValue()).isEqualTo(imageUrl)
    }



}