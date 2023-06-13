package com.batikfy.batikfy.ui.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.Result
import com.batikfy.batikfy.data.remote.response.PostRegisterResponse
import com.batikfy.batikfy.databinding.ActivityRegisterBinding
import com.batikfy.batikfy.ui.auth.login.LoginActivity
import com.batikfy.batikfy.utils.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Remove Action Bar
        supportActionBar?.hide()
        onBack()

        binding.btnRegister.isEnabled = false
        binding.edPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val passwordLength = s?.length ?: 0
                binding.btnRegister.isEnabled = (passwordLength >= 8)
                if(passwordLength < 8) binding.edPassword.error = getString(R.string.error_password)
            }
        })
        binding.login.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btn_register -> {
                var isValid = true
                val name = binding.edName.text.toString()
                val email = binding.edEmail.text.toString()
                val password = binding.edPassword.text.toString()
                if (name.isEmpty()) {
                    binding.edName.error = getString(R.string.error_name)
                    isValid = false
                }
                if (email.isEmpty()) {
                    binding.edEmail.error = getString(R.string.error_email)
                    isValid = false
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.edEmail.error = getString(R.string.error_email_format)
                    isValid = false
                }
                if (password.isEmpty()) {
                    binding.edPassword.error = getString(R.string.error_password)
                    isValid = false
                }
                if (isValid) {
                    val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
                    val registerViewModel: RegisterViewModel by viewModels {
                        factory
                    }
                    registerViewModel.register(name, email, password).observe(this) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    showLoading(true)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    checkRegistration(result.data)
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        this,
                                        getString(R.string.error_register),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_register), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun checkRegistration(data: PostRegisterResponse) {
        if (data.succses == "false") {
            Toast.makeText(
                this,
                getString(R.string.error_register) + ": " + data.message,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this, getString(R.string.success_register), Toast.LENGTH_SHORT).show()
            showLoading(true)
            lifecycleScope.launch {
                delay(1000)
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    private fun onBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}