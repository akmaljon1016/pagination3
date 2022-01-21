package com.example.paginationpractice.di

import com.example.paginationpractice.model.New
import com.example.paginationpractice.model.News
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NetworkApi {
    @FormUrlEncoded
    @POST("LGLNews")
    suspend fun getResult(
        @Field("page") page: Int
    ): Response<News>
}