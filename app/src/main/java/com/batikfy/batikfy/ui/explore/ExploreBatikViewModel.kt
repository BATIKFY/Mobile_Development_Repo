package com.batikfy.batikfy.ui.explore

import androidx.lifecycle.ViewModel
import com.batikfy.batikfy.data.BatikfyRepository

class ExploreBatikViewModel (private val repository: BatikfyRepository) : ViewModel() {
    fun getAllBatikData() = repository.getAllBatikNoDB()
}