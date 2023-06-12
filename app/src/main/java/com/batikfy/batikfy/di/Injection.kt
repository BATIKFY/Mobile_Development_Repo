package com.batikfy.batikfy.di

import android.content.Context
import com.batikfy.batikfy.data.BatikfyRepository
import com.batikfy.batikfy.data.local.room.BatikfyDatabase
import com.batikfy.batikfy.data.remote.retrofit.ApiConfig
import com.batikfy.batikfy.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): BatikfyRepository {
        val apiService = ApiConfig.getApiService(context)
        val database = BatikfyDatabase.getInstance(context)
        val dao = database.batikfyDao()
        val appExecutors = AppExecutors()
        return BatikfyRepository.getInstance(apiService, dao, appExecutors)
    }
}