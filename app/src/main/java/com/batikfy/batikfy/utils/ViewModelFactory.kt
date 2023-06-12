package com.batikfy.batikfy.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.batikfy.batikfy.data.BatikfyRepository
import com.batikfy.batikfy.di.Injection
import com.batikfy.batikfy.ui.auth.login.LoginViewModel
import com.batikfy.batikfy.ui.auth.register.RegisterViewModel
import com.batikfy.batikfy.ui.detail.article.DetailArticleViewModel
import com.batikfy.batikfy.ui.detail.batik.DetailBatikViewModel
import com.batikfy.batikfy.ui.explore.ExploreArticleViewModel
import com.batikfy.batikfy.ui.explore.ExploreBatikViewModel
import com.batikfy.batikfy.ui.home.HomeViewModel
import com.batikfy.batikfy.ui.result.ResultViewModel

class ViewModelFactory private constructor(private val repository: BatikfyRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ExploreBatikViewModel::class.java)) {
            return ExploreBatikViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ExploreArticleViewModel::class.java)) {
            return ExploreArticleViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailBatikViewModel::class.java)) {
            return DetailBatikViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailArticleViewModel::class.java)) {
            return DetailArticleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}