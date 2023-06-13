package com.batikfy.batikfy.ui.auth.register

import androidx.lifecycle.ViewModel
import com.batikfy.batikfy.data.BatikfyRepository

class RegisterViewModel(private val repository: BatikfyRepository) : ViewModel() {
    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}