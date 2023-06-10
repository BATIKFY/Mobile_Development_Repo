package com.batikfy.batikfy.ui.game.quiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.ActivityQuizResultBinding
import java.text.DecimalFormat

class QuizResultActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set score
        val score = intent.getFloatExtra(EXTRA_SCORE, 0f)
        val percentage = DecimalFormat("#.##").format((score * 100)).toFloat()
        binding.progressResult.progress = percentage

        val greeting = when {
            percentage > 70 -> getString(R.string.greeting3)
            percentage in 40.0..70.0 -> getString(R.string.greeting2)
            else -> getString(R.string.greeting1)
        }
        binding.tvGreeting.text = greeting

        // click event
        binding.menuRetry.setOnClickListener(this)
        binding.menuMain.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.menu_retry -> {
                val intent = Intent(this@QuizResultActivity, QuizActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.menu_main -> {
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_SCORE = "extra_score"
    }
}