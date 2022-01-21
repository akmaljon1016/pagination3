package com.example.paginationpractice.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "newsr")
data class New(
    @SerializedName("favorite")
    var favorite: Int,
    @SerializedName("image_source_link")
    var imageSourceLink: String,
    @SerializedName("image_source_title")
    var imageSourceTitle: String,
    @SerializedName("member_first_name")
    var memberFirstName: String,
    @SerializedName("member_id")
    var memberId: String,
    @SerializedName("member_last_name")
    var memberLastName: String,
    @SerializedName("member_like")
    var memberLike: Int,
    @SerializedName("member_profile_pic")
    var memberProfilePic: String,
    @SerializedName("member_username")
    var memberUsername: String,
    @SerializedName("news_content")
    var newsContent: String,
    @SerializedName("news_id")
    @PrimaryKey
    var newsId: String,
    @SerializedName("news_image")
    var newsImage: String,
    @SerializedName("news_publishdate")
    var newsPublishdate: String,
    @SerializedName("news_title")
    var newsTitle: String,
    @SerializedName("news_url")
    var newsUrl: String,
    @SerializedName("slug")
    var slug: String,
    @SerializedName("tags")
    var tags: String,
    @SerializedName("thumb_image")
    var thumbImage: String,
    @SerializedName("total_comment")
    var totalComment: Int,
    @SerializedName("total_like")
    var totalLike: Int,
    @SerializedName("total_views")
    var totalViews: Int
)