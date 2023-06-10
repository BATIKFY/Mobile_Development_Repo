package com.batikfy.batikfy.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostScanResponse(

	@field:SerializedName("confidence")
	val confidence: Int,

	@field:SerializedName("predicted_class")
	val predictedClass: String
)
