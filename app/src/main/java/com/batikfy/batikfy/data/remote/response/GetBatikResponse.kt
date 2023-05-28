package com.batikfy.batikfy.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetBatikResponse(

	@field:SerializedName("data")
	val data: DataBatik,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)


data class DataBatik(

	@field:SerializedName("batik")
	val batik: List<BatikItem>
)

data class BatikItem(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("origin")
	val origin: String,

	@field:SerializedName("meaning")
	val meaning: String,

	@field:SerializedName("image")
	val image: String
)