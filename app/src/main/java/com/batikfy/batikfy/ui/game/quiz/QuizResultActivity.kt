package com.batikfy.batikfy.ui.game.quiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.ActivityQuizResultBinding

class QuizResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val percentage = 50
        binding.progressResult.progress = percentage.toFloat()
    }
}