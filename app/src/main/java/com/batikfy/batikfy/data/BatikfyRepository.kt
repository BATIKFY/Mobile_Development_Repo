package com.batikfy.batikfy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.batikfy.batikfy.data.remote.response.BatikItem
import com.batikfy.batikfy.data.remote.response.DataBatik
import com.batikfy.batikfy.data.remote.retrofit.ApiService
import com.bumptech.glide.load.engine.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BatikfyRepository private constructor(
    val apiService: ApiService,
) {
    fun getAllBatik(): LiveData<com.batikfy.batikfy.data.Result<List<BatikItem>>> = liveData {
        emit(com.batikfy.batikfy.data.Result.Loading)
        try {
            val response = apiService.getAllBatik()
            emit(com.batikfy.batikfy.data.Result.Success(response.data.batik))
        } catch (e: Exception) {
            emit(com.batikfy.batikfy.data.Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: BatikfyRepository? = null

        fun getInstance(
            apiService: ApiService
        ): BatikfyRepository =
            instance ?: synchronized(this) {
                instance ?: BatikfyRepository(apiService)
            }.also { instance = it }
    }
}