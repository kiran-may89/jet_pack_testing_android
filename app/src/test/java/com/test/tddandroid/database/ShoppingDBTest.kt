package com.test.tddandroid.database

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.android.example.github.util.getOrAwaitValue
import com.test.tddandroid.BuildConfig
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.everyItem
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.*


import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.internal.matchers.GreaterThan
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@SmallTest

@Config(sdk = [Build.VERSION_CODES.O_MR1])
class ShoppingDBTest {
    @get:Rule
    val instantRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var db: ShoppingDB
    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext,
            ShoppingDB::class.java
        ).allowMainThreadQueries().build()

        shoppingDao = db.shopppingDao()
    }

    @Test
    fun `test non Null DB`() {
        assertNotNull(db)


    }

    @Test
    fun `test nonNull Dao`() {
        assertNotNull(shoppingDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `test InsertItem`() = runBlocking {
        val shoppingItem = ShoppingItem("Apple", 12, 5, "test")
        val transaction = shoppingDao.insert(shoppingItem)

        assertNotEquals(0, transaction)
    }

    @Test
    fun `test InsertItems`() = runBlocking {

        val list = listOf(
            ShoppingItem("Apple", 12, 5, "test"),
            ShoppingItem("Apple", 12, 5, "test"),
            ShoppingItem("PineApple", 15, 3, "test"),
            ShoppingItem("Orange", 11, 2, "test")
        )
        val transaction = shoppingDao.insertAll(list)
        print(transaction)
        assertThat(transaction, everyItem(greaterThan(0L)))
    }

    @Test
    fun `test getTotalPrice`() = runBlocking {
        val list = listOf(
            ShoppingItem("Apple", 4, 5, "test"),
            ShoppingItem("Apple", 2, 5, "test"),
            ShoppingItem("PineApple", 3, 3, "test"),
            ShoppingItem("Orange", 2, 2, "test")
        )
        val transaction = shoppingDao.insertAll(list)
        val cartValue = shoppingDao.getTotalPrice().getOrAwaitValue()
        print(cartValue)
        assertTrue(cartValue > 0)

    }

    @Test
    fun `test deleteItem`() = runBlocking {

        val list = listOf(
            ShoppingItem("Apple", 4, 5, "test"),

            ShoppingItem("PineApple", 3, 3, "test"),
            ShoppingItem("Orange", 2, 2, "test")
        )
        val transaction = shoppingDao.insertAll(list)
        val items = shoppingDao.getShoppingItems().getOrAwaitValue()
        shoppingDao.deleteShoppingItem(items[0])
        val afterDelete = shoppingDao.getShoppingItems().getOrAwaitValue()
        assertThat(items.size, greaterThan(afterDelete.size))

    }

}