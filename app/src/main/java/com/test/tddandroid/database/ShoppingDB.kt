package com.test.tddandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingDB : RoomDatabase() {
    abstract fun shopppingDao(): ShoppingDao

    companion object {
        @Volatile
        private var db: ShoppingDB? = null
        fun getDataBase(context: Context): ShoppingDB =
            db ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDB::class.java,
                    "shopping_db"
                ).build().also {
                    db = it
                }
            }


    }
}