package com.example.paginationpractice.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paginationpractice.model.New
import com.example.paginationpractice.model.RemoteKey

@Dao
interface NewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(user: List<New>)

    @Query("SELECT * FROM newsr")
    fun pagingSource():PagingSource<Int,New>

    @Query("DELETE FROM newsr")
    suspend fun clearAll()

}