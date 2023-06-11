package com.batikfy.batikfy.ui.explore

import androidx.lifecycle.ViewModel
import com.batikfy.batikfy.data.BatikfyRepository

class ExploreArticleViewModel (private val repository: BatikfyRepository) : ViewModel() {
    fun getAllArticleData() = repository.getAllArticleNoDB()
}