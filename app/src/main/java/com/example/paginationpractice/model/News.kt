package com.example.paginationpractice.model



import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("news")
    var news: List<New>,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("total_pages")
    var totalPages: Int
)