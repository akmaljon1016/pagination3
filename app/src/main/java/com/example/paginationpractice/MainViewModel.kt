package com.example.paginationpractice

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.paginationpractice.db.NewsDatabase
import com.example.paginationpractice.db.NewsRemoteMediator
import com.example.paginationpractice.di.NetworkApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class MainViewModel @Inject constructor(networkApi: NetworkApi, database: NewsDatabase) :
    ViewModel() {
    //    val listData = Pager(PagingConfig(pageSize = 10)) {
//        PagingDataSource(networkApi)
//    }.flow.cachedIn(viewModelScope)
//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            database.newsDao().pagingSource()
//        }
//    }

    init {
        Log.d("DATABASE",database.newsDao().pagingSource().toString())
    }

    val listData = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        remoteMediator = NewsRemoteMediator(database, networkApi),
        pagingSourceFactory = { database.newsDao().pagingSource() }
    ).flow
}