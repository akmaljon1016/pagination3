package com.example.paginationpractice

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paginationpractice.di.NetworkApi
import com.example.paginationpractice.model.New
import com.example.paginationpractice.model.News

class PagingDataSource(val networkApi: NetworkApi) : PagingSource<Int, New>() {

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, New>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, New> {
        val currentPage = params.key ?: 4
        return try {
            val response = networkApi.getResult(currentPage)
            val data = response.body()?.news
            val responseData = mutableListOf<New>()
            data?.let { responseData.addAll(it) }
            Log.d("DATAaaaaa",responseData.toString())

            LoadResult.Page(
                data=responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = if (data?.isEmpty() == true) null else currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}