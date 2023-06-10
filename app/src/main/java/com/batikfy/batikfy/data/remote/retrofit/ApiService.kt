package com.batikfy.batikfy.data.remote.retrofit

import com.batikfy.batikfy.data.remote.response.BatikItem
import com.batikfy.batikfy.data.remote.response.BlogsItem
import com.batikfy.batikfy.data.remote.response.GetArticleResponse
import com.batikfy.batikfy.data.remote.response.GetBatikResponse
import retrofit2.http.*

interface ApiService {
 @GET("batik")
 fun getAllBatik(
 ): GetBatikResponse

 @GET("batik/{id}")
 fun getBatikDetail(
     @Path("id") id: String
 ): BatikItem

 @GET("blog")
 fun getAllBlog(
 ): GetArticleResponse

 @GET("blog/{id}")
 fun getBlogDetail(
     @Path("id") id: String
 ): BlogsItem
}