package com.batikfy.batikfy.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.FragmentProfileBinding
import com.batikfy.batikfy.ui.auth.login.LoginActivity
import com.batikfy.batikfy.ui.game.puzzle.PuzzleActivity
import com.batikfy.batikfy.ui.game.quiz.QuizActivity
import com.batikfy.batikfy.utils.AuthPreference

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.menuAccountInfo.setOnClickListener(this)
        binding.menuChangePass.setOnClickListener(this)
        binding.menuChangeLang.setOnClickListener(this)
        binding.menuLogout.setOnClickListener(this)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.menu_account_info  -> {
                Toast.makeText(activity, "Account Info", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_change_pass  -> {
                Toast.makeText(activity, "Change Password", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_change_lang  -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.menu_logout -> {
                AuthPreference.initialize(requireActivity())
                AuthPreference.clearToken()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }
}