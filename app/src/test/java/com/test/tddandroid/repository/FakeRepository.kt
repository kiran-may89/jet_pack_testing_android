package com.test.tddandroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.tddandroid.database.ShoppingItem
import com.test.tddandroid.net.responses.ImageResponse
import com.test.tddandroid.net.responses.Resource
import org.junit.Assert.*

class FakeRepository : ShoppingRepository {
    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()
    private var shouldReturnError = false
    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice
            .postValue(getTotalPrice())
    }

    fun getTotalPrice() = shoppingItems.sumBy { it.price }.toFloat()

    override suspend fun insertShoppingItem(item: ShoppingItem) {
        shoppingItems.add(item)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(item: ShoppingItem) {
        shoppingItems.remove(item)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchImage(searchQuery: String): Resource<ImageResponse> {
        return if (shouldReturnError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }
}

