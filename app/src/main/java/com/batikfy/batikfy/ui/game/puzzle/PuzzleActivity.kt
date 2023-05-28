package com.batikfy.batikfy.ui.game.puzzle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.ActivityPuzzleBinding

class PuzzleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPuzzleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPuzzleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}