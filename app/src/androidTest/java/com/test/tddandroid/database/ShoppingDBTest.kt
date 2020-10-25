package com.test.tddandroid.database


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.android.example.github.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.test.tddandroid.launchFragmentInHiltContainer
import com.test.tddandroid.views.fragments.ShoppingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.everyItem
import org.hamcrest.Matchers.greaterThan
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class ShoppingDBTest {
    @get:Rule
    val instantRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val hiltRule:HiltAndroidRule = HiltAndroidRule(this)
    @Inject
    @Named("test_db")
     lateinit var db: ShoppingDB
     lateinit var shoppingDao: ShoppingDao

    @Before
    fun setUp() {
        hiltRule.inject()

        shoppingDao = db.shopppingDao()
    }

    @Test
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<ShoppingFragment> {

        }
    }
    @Test
    fun testNonNullDB() {
        assertThat(db).isNotNull()


    }

    @Test
    fun testNonNullDao() {
      //  assertNotNull(shoppingDao)
        assertThat(shoppingDao).isNotNull()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsertItem() = runBlocking {
        val shoppingItem = ShoppingItem("Apple", 12, 5, "test")
        val transaction = shoppingDao.insert(shoppingItem)

       // assertNotEquals(0, transaction)
        assertThat(transaction).isNotEqualTo(0)
    }

    @Test
    fun testInsertItems() = runBlocking {

        val list = listOf(
            ShoppingItem("Apple", 12, 5, "test"),
            ShoppingItem("Apple", 12, 5, "test"),
            ShoppingItem("PineApple", 15, 3, "test"),
            ShoppingItem("Orange", 11, 2, "test")
        )
        val transaction = shoppingDao.insertAll(list)
        print(transaction)
        //assertThat(transaction, everyItem(greaterThan(0L)))
        assertThat(transaction).doesNotContain(0)
    }

    @Test
    fun testgetTotalPrice() = runBlocking {
        val list = listOf(
            ShoppingItem("Apple", 4, 5, "test"),
            ShoppingItem("Apple", 2, 5, "test"),
            ShoppingItem("PineApple", 3, 3, "test"),
            ShoppingItem("Orange", 2, 2, "test")
        )
        val transaction = shoppingDao.insertAll(list)
        val cartValue = shoppingDao.getTotalPrice().getOrAwaitValue()
        print(cartValue)
     //   assertTrue(cartValue > 0)
        assertThat(cartValue).isGreaterThan(0)

    }

    @Test
    fun testdeleteItem() = runBlocking {

        val list = listOf(
            ShoppingItem("Apple", 4, 5, "test"),

            ShoppingItem("PineApple", 3, 3, "test"),
            ShoppingItem("Orange", 2, 2, "test")
        )
        val transaction = shoppingDao.insertAll(list)
        val items = shoppingDao.getShoppingItems().getOrAwaitValue()
        shoppingDao.deleteShoppingItem(items[0])
        val afterDelete = shoppingDao.getShoppingItems().getOrAwaitValue()

        assertThat(items.size).isGreaterThan(afterDelete.size)

    }

}