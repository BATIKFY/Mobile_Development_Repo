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

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): PostLoginResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): PostRegisterResponse

    // Still struggling with local storage
    @GET("batik")
    fun getAllBatikWithDB(): Call<GetBatikResponse>

}