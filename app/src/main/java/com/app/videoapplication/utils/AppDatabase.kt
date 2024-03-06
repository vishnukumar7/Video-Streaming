package com.app.videoapplication.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.videoapplication.model.GenresItem
import com.app.videoapplication.model.dao.DatabaseDao

@Database(entities = [GenresItem::class], exportSchema = false, version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun databaseDao() : DatabaseDao
}