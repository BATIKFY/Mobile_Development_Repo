package com.batikfy.batikfy.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "batiks")
class BatikEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "origin")
    val origin: String,

    @field:ColumnInfo(name = "meaning")
    val meaning: String,

    @field:ColumnInfo(name = "image")
    val image: String,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
)