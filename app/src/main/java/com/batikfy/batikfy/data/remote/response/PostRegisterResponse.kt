package com.batikfy.batikfy.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostRegisterResponse(

	@field:SerializedName("data")
	val data: DataRegister,

	@field:SerializedName("succses")
	val succses: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataRegister(

	@field:SerializedName("user")
	val user: User
)

data class User(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String
)
