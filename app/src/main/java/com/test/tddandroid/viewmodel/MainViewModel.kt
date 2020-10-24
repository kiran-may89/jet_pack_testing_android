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
import com.test.tddandroid.utils.Constants
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
    val insertShoppingItemStatus:LiveData<Event<Resource<ShoppingItem>>> get() = _insertShoppingItemStatus

    fun setImageUrl( url:String){
        _curlImageUrl.postValue(url)
    }
    fun deleteShoppingItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteShoppingItem(item)
    }
    fun insertShoppingItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO){
        repo.insertShoppingItem(item)
    }
    fun insertShoppingItem(name: String, amountString: String, priceString: String) {
        if(name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The fields must not be empty", null)))
            return
        }
        if(name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The name of the item" +
                    "must not exceed ${Constants.MAX_NAME_LENGTH} characters", null)))
            return
        }
        if(priceString.length > Constants.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The price of the item" +
                    "must not exceed ${Constants.MAX_PRICE_LENGTH} characters", null)))
            return
        }
        val amount = try {
            amountString.toInt()
        } catch(e: Exception) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter a valid amount", null)))
            return
        }
        val shoppingItem = ShoppingItem(name, amount, priceString.toInt(), _curlImageUrl.value ?: "")
        insertShoppingItem(shoppingItem)
        setImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String) {
        if(imageQuery.isEmpty()) {
            return
        }
        _imagesMutableLiveData.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repo.searchImage(imageQuery)
            _imagesMutableLiveData.value = Event(response)
        }
    }
}