package com.test.tddandroid.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.test.tddandroid.database.ShoppingItem
import com.test.tddandroid.net.responses.Resource
import com.test.tddandroid.net.responses.Status
import com.test.tddandroid.repository.FakeRepository
import com.test.tddandroid.utils.Constants
import com.test.tddandroid.utils.Event

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk=[Build.VERSION_CODES.O_MR1])
class MainViewModelTest{
    @get:Rule
    val instantExecutRule = InstantTaskExecutorRule()
    lateinit var viewmodel:MainViewModel


    @Before
    fun setup(){
        viewmodel  = MainViewModel(FakeRepository())
    }

    @Test
    fun `insert  shopping item with empty field returns error`(){

        val itemObserver = Observer<Event<Resource<ShoppingItem>>>{}

        try{
            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
            viewmodel.insertShoppingItem("Apple","","")
            val value =  viewmodel.insertShoppingItemStatus.value
            assertThat(value?.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
        }finally {

            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
        }


    }
    @Test
    fun `insert  shopping item with too high amount  returns error`(){

        val itemObserver = Observer<Event<Resource<ShoppingItem>>>{}

        try{
            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
            viewmodel.insertShoppingItem("Apple","99999999999999999","")
            val value =  viewmodel.insertShoppingItemStatus.value
            assertThat(value?.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
        }finally {

            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
        }


    }
    @Test
    fun `insert shopping item with too long name, returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        val itemObserver = Observer<Event<Resource<ShoppingItem>>>{}
        try{
            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
            viewmodel.insertShoppingItem(string,"22","3")
            val value =  viewmodel.insertShoppingItemStatus.value
            assertThat(value?.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
        }finally {

            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
        }
    }
    @Test
    fun `insert shopping item with too long price, returns error`() {
        val string = buildString {
            for(i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        val itemObserver = Observer<Event<Resource<ShoppingItem>>>{}

        try{
            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
            viewmodel.insertShoppingItem("Apple","22",string)
            val value =  viewmodel.insertShoppingItemStatus.value
            assertThat(value?.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
        }finally {

            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
        }
    }
    @Test
    fun `insert  shopping item with correct input  returns success`(){

        val itemObserver = Observer<Event<Resource<ShoppingItem>>>{}

        try{
            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
            viewmodel.insertShoppingItem("Apple","22","3")
            val value =  viewmodel.insertShoppingItemStatus.value
            assertThat(value?.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
        }finally {

            viewmodel.insertShoppingItemStatus.observeForever { itemObserver }
        }


    }
}