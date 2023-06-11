package com.batikfy.batikfy.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetArticleResponse(

	@field:SerializedName("data")
	val data: DataArticle,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataArticle(

	@field:SerializedName("blog")
	val blogs: List<BlogsItem>
)

data class BlogsItem(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("textBlog")
	val textBlog: String,

	@field:SerializedName("source")
	val source: String,

	@field:SerializedName("image")
	val image: String,
)
