package com.softex.task.di.module

import androidx.room.Room
import android.content.Context
import com.softex.task.BuildConfig
import com.softex.task.db.ItemDao
import com.softex.task.db.SoftexTestDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {


    @Provides
    @Singleton
    fun provideItemDao(db: SoftexTestDatabase): ItemDao {
        return db.itemDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        SoftexTestDatabase::class.java,
        BuildConfig.DATABASE_NAME
    ).build()

}