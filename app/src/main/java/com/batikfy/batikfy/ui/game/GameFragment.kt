package com.batikfy.batikfy.ui.game

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.FragmentGameBinding
import com.batikfy.batikfy.ui.game.puzzle.PuzzleActivity
import com.batikfy.batikfy.ui.game.quiz.QuizActivity

class GameFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentGameBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cvGame1.setOnClickListener(this)
        binding.cvGame2.setOnClickListener(this)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.cv_game_1  -> {
                val intent = Intent(activity, QuizActivity::class.java)
                startActivity(intent)
            }
            R.id.cv_game_2 -> {
                val intent = Intent(activity, PuzzleActivity::class.java)
                startActivity(intent)
            }
        }
    }
}