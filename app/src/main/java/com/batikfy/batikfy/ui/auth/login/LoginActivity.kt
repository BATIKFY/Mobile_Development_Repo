package com.batikfy.batikfy.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.batikfy.batikfy.MainActivity
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.ActivityLoginBinding
import com.batikfy.batikfy.ui.auth.register.RegisterActivity
import com.batikfy.batikfy.utils.AuthPreference
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
//        private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
//        private val loginViewModel: LoginViewModel by viewModels {
//            factory
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Remove Action Bar
        supportActionBar?.hide()

//        binding.btnLogin.isEnabled = false
//        binding.edPassword.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable?) {
//                val passwordLength = s?.length ?: 0
//                binding.btnLogin.isEnabled = (passwordLength >= 8)
//                if(passwordLength < 8) binding.edPassword.error = getString(R.string.error_password)
//            }
//        })
        binding.signup.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.signup -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btn_login -> {
                var isValid = true
//                val email = binding.edEmail.text.toString()
//                val password = binding.edPassword.text.toString()
//                if (email.isEmpty()) {
//                    binding.edEmail.error = getString(R.string.error_email)
//                    isValid = false
//                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    binding.edEmail.error = getString(R.string.error_email_format)
//                    isValid = false
//                }
//                if (password.isEmpty()) {
//                    binding.edPassword.error = getString(R.string.error_password)
//                    isValid = false
//                }
                if (isValid) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
//                    loginViewModel.login(email, password).observe(this) { result ->
//                        if (result != null) {
//                            when (result) {
//                                is Result.Loading -> {
//                                    showLoading(true)
//                                }
//                                is Result.Success -> {
//                                    showLoading(false)
//                                    checkLogin(result.data)
//                                }
//                                is Result.Error -> {
//                                    showLoading(false)
//                                    Toast.makeText(
//                                        this,
//                                        result.error,
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//                        }
//                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

//    private fun checkLogin(data: LoginResponse) {
//        if (data.error) {
//            Toast.makeText(
//                this,
//                getString(R.string.error_login) + ": " + data.message,
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
//            AuthPreference.initialize(this)
//            AuthPreference.setToken(data.loginResult.token)
//            showLoading(true)
//            lifecycleScope.launch {
//                delay(1000)
//                val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }
}