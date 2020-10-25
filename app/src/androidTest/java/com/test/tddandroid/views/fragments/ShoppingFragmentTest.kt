package com.test.tddandroid.views.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import com.android.example.github.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.test.tddandroid.R
import com.test.tddandroid.adapters.ShoppingItemAdapter
import com.test.tddandroid.database.ShoppingItem
import com.test.tddandroid.factory.ShoppingFactoryTest
import com.test.tddandroid.launchFragmentInHiltContainer
import com.test.tddandroid.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_shopping.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@LargeTest
class ShoppingFragmentTest {
    @get:Rule
    var executionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var factory: ShoppingFactoryTest

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun swipeShoppingItem_deleteItemInDb() {
        val shoppingItem = ShoppingItem("TEST", 1, 1f, "TEST", 1)
        var testViewModel: MainViewModel? = null
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = factory) {
            testViewModel = viewModel
            viewModel?.insertShoppingItem(shoppingItem)

        }
        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0, swipeLeft()
            )
        )
        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment() {
        val controller = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = factory) {
            Navigation.setViewNavController(requireView(), controller)

        }
        onView(withId(R.id.fabAddShoppingItem)).perform(click())
        verify(controller).navigate(ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment())
    }
}