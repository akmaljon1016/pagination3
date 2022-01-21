package com.example.paginationpractice.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paginationpractice.di.NetworkApi
import com.example.paginationpractice.model.New
import com.example.paginationpractice.model.RemoteKey
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
@ActivityRetainedScoped
class NewsRemoteMediator @Inject constructor(
    private val database: NewsDatabase,
    private val api: NetworkApi
) : RemoteMediator<Int, New>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, New>): MediatorResult {
        val pageKey = getPageKeyData(state, loadType)
        val page = when (pageKey) {
            is MediatorResult.Success -> {
                return pageKey
            }
            else -> {
                pageKey as Int
            }
        }
        return try {
            val response = api.getResult(page)
            val isListEmpty = response.body()?.news?.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteDao().deleteByQuery()
                    database.newsDao().clearAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isListEmpty == true) null else page + 1

                val keys = response.body()?.news?.map {
                    RemoteKey(it.newsId, prevKey, nextKey)
                }
                response.body()?.let { database.newsDao().insertAll(it.news) }
                keys?.let { database.remoteDao().insertOrReplace(it) }
            }
            return MediatorResult.Success(endOfPaginationReached = isListEmpty == true)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    suspend fun getPageKeyData(state: PagingState<Int, New>, loadType: LoadType): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getCurrentPositon(state)
                val current = remoteKeys?.nextKey?.minus(1)
                if (current != null) {
                    return current
                } else {
                    4
                }
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstPosition(state)
                val prevKey = remoteKeys?.prevKey
                return prevKey
                    ?: MediatorResult.Success(
                        endOfPaginationReached = false
                    )
            }
            LoadType.APPEND -> {
                val remoteKey = getLastRemoteKey(state)
                val nextKey = remoteKey?.nextKey
                return if (nextKey != null) nextKey else MediatorResult.Success(
                    endOfPaginationReached = false
                )
            }
            else -> {

            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, New>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { news ->
                database.remoteDao().remoteKeyByQuery(news.newsId)
            }
    }

    private suspend fun getFirstPosition(state: PagingState<Int, New>): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { news ->
                database.remoteDao().remoteKeyByQuery(news.newsId)
            }
    }

    private suspend fun getCurrentPositon(state: PagingState<Int, New>): RemoteKey? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.newsId?.let { id ->
                database.remoteDao().remoteKeyByQuery(id)
            }
        }
    }
}