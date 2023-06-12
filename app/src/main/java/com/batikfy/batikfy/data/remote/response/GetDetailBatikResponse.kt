package com.batikfy.batikfy.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetDetailBatikResponse(

	@field:SerializedName("data")
	val data: DataDetailBatik,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataDetailBatik(

	@field:SerializedName("batik")
	val batik: BatikItem
)
