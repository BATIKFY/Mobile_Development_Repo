package com.batikfy.batikfy.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.batikfy.batikfy.data.local.entity.BatikEntity
import com.batikfy.batikfy.data.local.room.BatikfyDao
import com.batikfy.batikfy.data.remote.response.*
import com.batikfy.batikfy.data.remote.retrofit.ApiService
import com.batikfy.batikfy.model.ArticleDataDummy
import com.batikfy.batikfy.model.BatikDataDummy
import com.batikfy.batikfy.utils.AppExecutors
import okhttp3.MultipartBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BatikfyRepository private constructor(
    private val apiService: ApiService,
    private val batikfyDao: BatikfyDao,
    private val appExecutors: AppExecutors
) {
    private val resultBatik = MediatorLiveData<Result<List<BatikEntity>>>()

    fun getAllBatikNoDB(): LiveData<Result<GetBatikResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllBatik()
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("HomeViewModel", "function get batik: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllArticleNoDB(): LiveData<Result<GetArticleResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllArticle()
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("HomeViewModel", "function get article: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
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