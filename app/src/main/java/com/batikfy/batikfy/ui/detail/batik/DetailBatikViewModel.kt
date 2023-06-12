package com.batikfy.batikfy.ui.detail.batik

import androidx.lifecycle.ViewModel
import com.batikfy.batikfy.data.BatikfyRepository

class DetailBatikViewModel (private val repository: BatikfyRepository) : ViewModel() {
    fun getDetailBatik(id: String) = repository.getDetailBatik(id)
    fun getAllBatikData() = repository.getAllBatikNoDB()
}