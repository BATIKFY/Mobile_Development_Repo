package com.batikfy.batikfy.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetDetailArticleResponse(

	@field:SerializedName("data")
	val data: DataDetailArticle,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataDetailArticle(

	@field:SerializedName("blog")
	val blog: BlogsItem
)
