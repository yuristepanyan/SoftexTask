package com.softex.task.db

import androidx.room.Room
import androidx.room.paging.LimitOffsetDataSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.softex.task.model.Item
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var database: SoftexTestDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            SoftexTestDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        ITEM_ONE.apply { deleted = false }
        database.close()
    }

    @Test
    fun dbMustBeEmpty() {
        Assert.assertTrue((database.itemDao().getItems().create() as LimitOffsetDataSource).countItems() == 0)
    }

    @Test
    fun insertAndCheck() {
        database.itemDao().insertItems(listOf(ITEM_ONE)).blockingAwait()
        Assert.assertTrue((database.itemDao().getItems().create() as LimitOffsetDataSource).countItems() == 1)
    }

    @Test
    fun setDeletedAndTest() {
        database.itemDao().insertItems(listOf(ITEM_ONE)).blockingAwait()
        Assert.assertTrue((database.itemDao().getItems().create() as LimitOffsetDataSource).countItems() == 1)
        database.itemDao().updateItem(ITEM_ONE.apply { deleted = true }).blockingAwait()
        Assert.assertTrue((database.itemDao().getItems().create() as LimitOffsetDataSource).countItems() == 0)
    }

    @Test
    fun testOrdering() {
        database.itemDao().insertItems(listOf(ITEM_ONE, ITEM_TWO)).blockingAwait()
        Assert.assertEquals(
            (database.itemDao().getItems().create() as LimitOffsetDataSource).loadRange(
                0,
                2
            )[0].name, "first"
        )
    }

    companion object {
        val ITEM_ONE = Item("1", "first", null, "2016-08-08 18:20:20.9566253")
        val ITEM_TWO = Item("2", "second", null, "2016-08-08 18:21:20.9566253")
    }
}