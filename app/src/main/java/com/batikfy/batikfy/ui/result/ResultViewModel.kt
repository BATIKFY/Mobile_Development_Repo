package com.batikfy.batikfy.ui.result

import androidx.lifecycle.ViewModel
import com.batikfy.batikfy.data.BatikfyRepository
import okhttp3.MultipartBody

class ResultViewModel(private val repository: BatikfyRepository) : ViewModel() {
    fun scanImage(file: MultipartBody.Part) = repository.scanImage(file)
}