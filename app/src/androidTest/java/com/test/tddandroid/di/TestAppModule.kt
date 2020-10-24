package com.test.tddandroid.di

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.test.tddandroid.database.ShoppingDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named

@InstallIn(ApplicationComponent::class)
@Module
object TestAppModule {
    @Named("test_db")
    @Provides
    fun providesMemoryDb(): ShoppingDB {
        return Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext,
            ShoppingDB::class.java
        ).allowMainThreadQueries().build()
    }
}