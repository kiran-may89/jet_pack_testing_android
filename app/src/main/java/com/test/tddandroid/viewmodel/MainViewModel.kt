package com.test.tddandroid.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.tddandroid.database.ShoppingItem
import com.test.tddandroid.net.responses.ImageResponse
import com.test.tddandroid.net.responses.Resource
import com.test.tddandroid.repository.ShoppingRepository
import com.test.tddandroid.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(val repo:ShoppingRepository):ViewModel() {

    val shoppingItems = repo.observeAllShoppingItems()
    val totalPrice = repo.observeTotalPrice()
    private val  _imagesMutableLiveData = MutableLiveData<Event<Resource<ImageResponse>>>()
     val imagesLiveData:LiveData<Event<Resource<ImageResponse>>> = _imagesMutableLiveData

    private val _curlImageUrl = MutableLiveData<String>()
    val curlImageUrl = _curlImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItem:LiveData<Event<Resource<ShoppingItem>>> get() = _insertShoppingItemStatus

    fun setImageUrl( url:String){
        _curlImageUrl.postValue(url)
    }
    fun deleteShoppingItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteShoppingItem(item)
    }
    fun insertShoppingItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO){
        repo.insertShoppingItem(item)
    }
}