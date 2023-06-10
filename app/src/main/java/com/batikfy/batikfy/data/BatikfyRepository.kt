package com.batikfy.batikfy.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.batikfy.batikfy.data.local.room.BatikfyDao
import com.batikfy.batikfy.data.remote.response.BatikItem
import com.batikfy.batikfy.data.remote.response.BlogsItem
import com.batikfy.batikfy.data.remote.response.PostScanResponse
import com.batikfy.batikfy.data.remote.retrofit.ApiService
import com.batikfy.batikfy.model.ArticleDataDummy
import com.batikfy.batikfy.model.BatikDataDummy
import com.batikfy.batikfy.utils.AppExecutors
import okhttp3.MultipartBody

class BatikfyRepository private constructor(
    private val apiService: ApiService,
    private val batikfyDao: BatikfyDao,
    private val appExecutors: AppExecutors
) {
    /**
     * Ubah 2 fun ini supaya bisa pakai database
     */
    fun getBatik(): List<BatikItem> {
        return BatikDataDummy.getData()
    }

    fun getArticle(): List<BlogsItem> {
        return ArticleDataDummy.getData()
    }

    fun scanImage(
        file: MultipartBody.Part
    ): LiveData<Result<PostScanResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.scanImage(file)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("ResultViewModel", "function scan: ${e.message.toString()}")
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