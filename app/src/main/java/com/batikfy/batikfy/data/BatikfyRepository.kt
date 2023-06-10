package com.batikfy.batikfy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.batikfy.batikfy.data.local.room.BatikfyDao
import com.batikfy.batikfy.data.remote.response.BatikItem
import com.batikfy.batikfy.data.remote.response.DataBatik
import com.batikfy.batikfy.data.remote.retrofit.ApiService
import com.batikfy.batikfy.utils.AppExecutors
import com.batikfy.batikfy.data.Result
import com.bumptech.glide.load.engine.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BatikfyRepository private constructor(
    private val apiService: ApiService,
    private val batikfyDao: BatikfyDao,
    private val appExecutors: AppExecutors
) {
    fun getAllBatik(): LiveData<Result<List<BatikItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllBatik()
            emit(Result.Success(response.data.batik))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: BatikfyRepository? = null

        fun getInstance(
            apiService: ApiService,
            batikfyDao: BatikfyDao,
            appExecutors: AppExecutors
        ): BatikfyRepository =
            instance ?: synchronized(this) {
                instance ?: BatikfyRepository(apiService, batikfyDao, appExecutors)
            }.also { instance = it }
    }
}