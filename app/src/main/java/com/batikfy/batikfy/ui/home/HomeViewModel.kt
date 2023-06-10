package com.batikfy.batikfy.ui.home

import androidx.lifecycle.ViewModel
import com.batikfy.batikfy.data.BatikfyRepository

class HomeViewModel(private val repository: BatikfyRepository) : ViewModel() {
        fun getAllBatik() = repository.getAllBatik()
}