package com.batikfy.batikfy.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.batikfy.batikfy.MainActivity
import com.batikfy.batikfy.databinding.ActivitySplashBinding
import com.batikfy.batikfy.ui.auth.login.LoginActivity
import com.batikfy.batikfy.utils.AuthPreference

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Remove Action Bar
        supportActionBar?.hide()

        // Check login yet
        AuthPreference.initialize(this)
        val token = AuthPreference.getToken() ?: ""

        // Set timer to show and logic to move to the other activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (token.isEmpty()) {
                Intent(this@SplashActivity, LoginActivity::class.java)
            } else {
                Intent(this@SplashActivity, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 1750)
    }
}