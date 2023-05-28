package com.batikfy.batikfy.data.remote.retrofit

import android.content.Context
import com.batikfy.batikfy.BuildConfig
import com.batikfy.batikfy.utils.AuthPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(context: Context): ApiService {

        // Set Token Header
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            AuthPreference.initialize(context)
            val token = AuthPreference.getToken() ?: ""

            val requestHeaders =
                if (req.header("No-Authentication") == null && token.isNotEmpty()) {
                    req.newBuilder()
                        .addHeader("Authorization", token)
                        .build()
                } else {
                    req.newBuilder()
                        .build()
                }
            chain.proceed(requestHeaders)
        }

        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}