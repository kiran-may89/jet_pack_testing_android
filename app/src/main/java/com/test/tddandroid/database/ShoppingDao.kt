package com.test.tddandroid.database

import androidx.lifecycle.LiveData
import androidx.room.*
import retrofit2.http.DELETE

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShoppingItem):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<ShoppingItem>):List<Long>

    @Query("SELECT * FROM shopping_item")
    fun getShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price*amount) FROM shopping_item")
    fun getTotalPrice(): LiveData<Float>

    @Delete
    suspend fun deleteShoppingItem(item: ShoppingItem)
}