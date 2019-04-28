package com.softex.task.db

import androidx.paging.DataSource
import androidx.room.*
import com.softex.task.model.Item
import io.reactivex.Completable

/**
 * Data access table for the Items table.
 *
 * The Items table contains all Items which are got from the api
 */
@Dao
interface ItemDao {

    /**
     * @return all Items from the db that are not deleted
     */
    @Query("SELECT * FROM Items WHERE deleted = 0 ORDER BY time")
    fun getItems(): DataSource.Factory<Int, Item>

    /**
     * Insert items to the database.

     * @param items the items to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItems(items: List<Item>): Completable

    /**
     * update the item deleted property in the database.
     * @param item to update
     */
    @Update
    fun updateItem(item: Item): Completable
}