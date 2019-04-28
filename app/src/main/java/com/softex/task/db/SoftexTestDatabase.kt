package com.softex.task.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.softex.task.model.Item

/**
 * The Room database that contains the Items table
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class SoftexTestDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao
}