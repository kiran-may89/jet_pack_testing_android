package com.test.tddandroid.repository

import androidx.lifecycle.LiveData
import com.test.tddandroid.database.ShoppingDao
import com.test.tddandroid.database.ShoppingItem
import com.test.tddandroid.net.configs.PixelBayApi
import com.test.tddandroid.net.responses.ImageResponse
import com.test.tddandroid.net.responses.Resource

class ShoppingRepositoryImpl(val dao: ShoppingDao, val api: PixelBayApi) : ShoppingRepository {
    override suspend fun insertShoppingItem(item: ShoppingItem) {
        dao.insert(item)
    }

    override suspend fun deleteShoppingItem(item: ShoppingItem) {
        dao.deleteShoppingItem(item)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return dao.getShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return dao.getTotalPrice()
    }

    override suspend fun searchImage(searchQuery: String): Resource<ImageResponse> {
        val response = api.searchForImage(searchQuery)
        return if (response.isSuccessful) {
            response.body()?.let {
                Resource.success(it)
            } ?: Resource.error("Unknown Error Occured", null)

        } else {
            return Resource.error("Unkown error Occured.", null)
        }
    }
}