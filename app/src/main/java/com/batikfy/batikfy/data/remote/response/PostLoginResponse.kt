package com.batikfy.batikfy.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostLoginResponse(

	@field:SerializedName("data")
	val data: DataLogin?,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataLogin(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("accessToken")
	val accessToken: String
)
