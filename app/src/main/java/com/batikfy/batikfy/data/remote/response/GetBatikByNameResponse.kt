package com.batikfy.batikfy.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetBatikByNameResponse(

	@field:SerializedName("data")
	val data: DataBatikByName,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataBatikByName(

	@field:SerializedName("batik")
	val batik: List<BatikItem>?
)
