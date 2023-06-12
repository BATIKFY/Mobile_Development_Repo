package com.batikfy.batikfy.data.remote.retrofit

import com.batikfy.batikfy.data.remote.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST()
    suspend fun scanImage(
        @Part image: MultipartBody.Part,
        @Url url: String
    ): PostScanResponse

    @GET("batik")
    suspend fun getAllBatik(): GetBatikResponse

    @GET("blog")
    suspend fun getAllArticle(): GetArticleResponse

    @GET("batik/{id}")
    suspend fun getDetailBatik(
        @Path("id") id: String
    ): GetDetailBatikResponse

    @GET("blog/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: String
    ): GetDetailArticleResponse

    @GET("batik/name/{keyword}")
    suspend fun getSearchBatikByName(
        @Path("keyword") keyword: String
    ): GetBatikByNameResponse

    // Still struggling with local storage
    @GET("batik")
    fun getAllBatikWithDB(): Call<GetBatikResponse>

}