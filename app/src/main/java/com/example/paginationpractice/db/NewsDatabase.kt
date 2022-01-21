package com.example.paginationpractice.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paginationpractice.model.New
import com.example.paginationpractice.model.News
import com.example.paginationpractice.model.RemoteKey

@Database(entities = [New::class, RemoteKey::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewDao
    abstract fun remoteDao(): RemoteDao
}