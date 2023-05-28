package com.batikfy.batikfy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.batikfy.batikfy.data.local.entity.BatikEntity
import com.batikfy.batikfy.data.local.room.BatikfyDao
import com.batikfy.batikfy.data.remote.response.BatikItem
import com.batikfy.batikfy.data.remote.response.BlogsItem
import com.batikfy.batikfy.data.remote.response.GetBatikResponse
import com.batikfy.batikfy.data.remote.retrofit.ApiService
import com.batikfy.batikfy.model.ArticleDataDummy
import com.batikfy.batikfy.model.BatikDataDummy
import com.batikfy.batikfy.utils.AppExecutors
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
    /**
     * Ubah 2 fun ini supaya bisa pakai database
     */
    fun getBatik(): List<BatikItem> {
        return BatikDataDummy.getData()
    }

    fun getArticle(): List<BlogsItem> {
        return ArticleDataDummy.getData()
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