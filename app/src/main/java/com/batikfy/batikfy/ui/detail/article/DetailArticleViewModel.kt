package com.batikfy.batikfy.ui.detail.article

import androidx.lifecycle.ViewModel
import com.batikfy.batikfy.data.BatikfyRepository

class DetailArticleViewModel (private val repository: BatikfyRepository) : ViewModel() {
    fun getDetailArticle(id: String) = repository.getDetailArticle(id)
    fun getAllArticleData() = repository.getAllArticleNoDB()
}