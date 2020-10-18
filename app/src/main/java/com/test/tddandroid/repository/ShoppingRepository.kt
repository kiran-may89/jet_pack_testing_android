package com.test.tddandroid.repository

import androidx.lifecycle.LiveData
import com.test.tddandroid.database.ShoppingItem
import com.test.tddandroid.net.responses.ImageResponse
import com.test.tddandroid.net.responses.Resource

interface ShoppingRepository {
    suspend fun  insertShoppingItem(item:ShoppingItem)
    suspend fun deleteShoppingItem(item:ShoppingItem)
    fun observeAllShoppingItems():LiveData<List<ShoppingItem>>
    fun observeTotalPrice():LiveData<Float>
    suspend fun searchImage(searchQuery:String):Resource<ImageResponse>
}