package com.batikfy.batikfy.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.batikfy.batikfy.BuildConfig
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

    fun login(email: String, password: String): LiveData<Result<PostLoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("LoginViewModel", "function login: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<PostRegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("LoginViewModel", "function register: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

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

    fun getDetailBatik(id: String): LiveData<Result<GetDetailBatikResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailBatik(id)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("DetailBatikViewModel", "function get detail batik: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailArticle(id: String): LiveData<Result<GetDetailArticleResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailArticle(id)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("DetailArticleViewModel", "function get detail article: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getSearchBatikByName(keyword: String): LiveData<Result<GetBatikByNameResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSearchBatikByName(keyword)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("Search batik by name", "function search batik: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllBatikWithDB(): LiveData<Result<List<BatikEntity>>> {
        resultBatik.value = Result.Loading
        appExecutors.diskIO.execute {
            try {
                val response = apiService.getAllBatikWithDB().execute()
                if (response.isSuccessful) {
                    val batiks = response.body()?.data?.batiks
                    val batikList = ArrayList<BatikEntity>()
                    batiks?.forEach { batik ->
                        val isBookmarked = batikfyDao.isBatikBookmarkedById(batik.id)
                        val userAccount = BatikEntity(
                            id = batik.id,
                            name = batik.name,
                            origin = batik.origin,
                            meaning = batik.meaning,
                            image = batik.image,
                            isBookmarked = isBookmarked
                        )
                        batikList.add(userAccount)
                    }
                    batikfyDao.deleteAllBatik()
                    batikfyDao.insertBatik(batikList)
                    resultBatik.postValue(Result.Success(batikList))
                } else {
                    resultBatik.postValue(Result.Error("Failed to fetch data"))
                    Log.e("Call GetBatikResponse", "Failed to fetch data")
                }
            } catch (e: Exception) {
                resultBatik.postValue(Result.Error(e.message.toString()))
                Log.e("Call GetBatikResponse", e.message.toString())
            }
        }
        return resultBatik
    }

    fun getArticle(): List<BlogsItem> {
        return ArticleDataDummy.getData()
    }

    fun scanImage(
        file: MultipartBody.Part
    ): LiveData<Result<PostScanResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.scanImage(file, BuildConfig.SCAN_URL+"process-image")
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