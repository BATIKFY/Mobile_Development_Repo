package com.batikfy.batikfy.ui.game.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.ActivityQuizBinding
import com.batikfy.batikfy.model.quiz.QuestionModel
import java.util.*
import java.util.concurrent.TimeUnit

class QuizActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityQuizBinding
    lateinit var questionsList: List<QuestionModel>
    private var index: Int = 0
    private var maxQuiz: Int = 5
    lateinit var questionModel: QuestionModel

    private var backPressedTime: Long = 0
    private var backToast: Toast? = null
    private val delayDuration: Long = 1500

    private var correctAnswerCount: Int = 0
    private var wrongAnswerCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Init
        questionsList = getAllQuestion().shuffled().take(maxQuiz)
        questionModel = questionsList[index]

        // Back to home view
        onBack()

        // Game config
        setAllQuestions()
        countdown()

        // Click event
        binding.option1.setOnClickListener(this)
        binding.option2.setOnClickListener(this)
        binding.option3.setOnClickListener(this)
        binding.option4.setOnClickListener(this)
    }

    private fun getAllQuestion(): List<QuestionModel> {
        val question = resources.getStringArray(R.array.question)
        val opt1 = resources.getStringArray(R.array.opt_1)
        val opt2 = resources.getStringArray(R.array.opt_2)
        val opt3 = resources.getStringArray(R.array.opt_3)
        val opt4 = resources.getStringArray(R.array.opt_4)
        val optTrue = resources.getStringArray(R.array.opt_true)
        val listQuestion = ArrayList<QuestionModel>()
        for (i in question.indices) {
            val quiz = QuestionModel(question[i], opt1[i], opt2[i], opt3[i], opt4[i], optTrue[i])
            listQuestion.add(quiz)
        }
        return listQuestion
    }

    private fun setAllQuestions() {
        binding.questions.text = questionModel.question
        binding.option1.text = questionModel.option1
        binding.option2.text = questionModel.option2
        binding.option3.text = questionModel.option3
        binding.option4.text = questionModel.option4
    }

    fun countdown() {
        val duration: Long = TimeUnit.SECONDS.toMillis(15)

        object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val sDuration: String = String.format(
                    Locale.ENGLISH,
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                )
                binding.countDown.text = sDuration
            }

            override fun onFinish() {
                index++
                if (index < questionsList.size) {
                    Toast.makeText(this@QuizActivity, "next", Toast.LENGTH_SHORT).show()
                    questionModel = questionsList[index]
                    setAllQuestions()
                    resetBackground()
                    enableButton()
                    countdown()

                } else {
                    Toast.makeText(this@QuizActivity, "finish", Toast.LENGTH_SHORT).show()
                    gameResult()
                }
            }
        }.start()
    }

    private fun gameResult() {
        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("correct", correctAnswerCount.toString())
        intent.putExtra("total", questionsList.size.toString())
        startActivity(intent)
        finish()
    }

    private fun correctAns(option: Button) {
        option.background = ContextCompat.getDrawable(this, R.drawable.true_bg)
        correctAnswerCount++
    }

    private fun wrongAns(option: Button) {
        option.background = ContextCompat.getDrawable(this, R.drawable.false_bg)
        wrongAnswerCount++
    }

    private fun enableButton() {
        binding.option1.isClickable = true
        binding.option2.isClickable = true
        binding.option3.isClickable = true
        binding.option4.isClickable = true
    }

    private fun disableButton() {
        binding.option1.isClickable = false
        binding.option2.isClickable = false
        binding.option3.isClickable = false
        binding.option4.isClickable = false
    }

    private fun checkAnswer(option: Button) {
        disableButton()
        val selectedAnswer = option.text.toString()
        val correctAnswer = questionModel.answer
        if (selectedAnswer == correctAnswer) {
            correctAns(option)
        } else {
            wrongAns(option)
        }
    }

    private fun resetBackground() {
        binding.option1.background = ContextCompat.getDrawable(this, R.drawable.option_bg)
        binding.option2.background = ContextCompat.getDrawable(this, R.drawable.option_bg)
        binding.option3.background = ContextCompat.getDrawable(this, R.drawable.option_bg)
        binding.option4.background = ContextCompat.getDrawable(this, R.drawable.option_bg)
    }

    private fun onBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    backToast?.cancel()
                    finish()
                } else {
                    backToast = Toast.makeText(
                        baseContext,
                        getString(R.string.double_back_press),
                        Toast.LENGTH_SHORT
                    )
                    backToast?.show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.option1 -> {
                checkAnswer(binding.option1)
            }
            R.id.option2 -> {
                checkAnswer(binding.option2)
            }
            R.id.option3 -> {
                checkAnswer(binding.option3)
            }
            R.id.option4 -> {
                checkAnswer(binding.option4)
            }
        }
    }
}