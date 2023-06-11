package com.batikfy.batikfy.data.remote.retrofit

import com.batikfy.batikfy.data.remote.response.GetBatikResponse
import com.batikfy.batikfy.data.remote.response.PostScanResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("process-image")
    suspend fun scanImage(
        @Part image: MultipartBody.Part
    ): PostScanResponse

    // Get All Batik List
    @GET("batik")
    suspend fun getAllBatik(): GetBatikResponse
}