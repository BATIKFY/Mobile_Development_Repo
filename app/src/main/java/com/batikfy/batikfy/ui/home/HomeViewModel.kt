package com.batikfy.batikfy.ui.home

import androidx.lifecycle.ViewModel
import com.batikfy.batikfy.data.BatikfyRepository

class HomeViewModel(private val repository: BatikfyRepository) : ViewModel() {
    fun getAllBatikData() = repository.getAllBatikNoDB()
    fun getAllArticleData() = repository.getAllArticleNoDB()

    fun getAllBatikDataWithDB() = repository.getAllBatikWithDB()
}