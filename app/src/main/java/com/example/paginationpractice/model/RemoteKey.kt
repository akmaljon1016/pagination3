package com.example.paginationpractice.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
class RemoteKey(@PrimaryKey var id :String, var prevKey:Int?, var nextKey:Int? )