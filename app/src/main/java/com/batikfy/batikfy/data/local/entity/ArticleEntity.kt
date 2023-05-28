package com.batikfy.batikfy.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
class ArticleEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "title")
    val title: String,

    @field:ColumnInfo(name = "textBlog")
    val textBlog: String,

    @field:ColumnInfo(name = "source")
    val source: String,

    @field:ColumnInfo(name = "image")
    val image: String,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
)