package com.batikfy.batikfy.ui.auth.login

import androidx.lifecycle.ViewModel
import com.batikfy.batikfy.data.BatikfyRepository

class LoginViewModel(private val repository: BatikfyRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}