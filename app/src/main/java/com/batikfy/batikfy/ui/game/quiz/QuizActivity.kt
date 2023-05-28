package com.batikfy.batikfy.ui.game.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}