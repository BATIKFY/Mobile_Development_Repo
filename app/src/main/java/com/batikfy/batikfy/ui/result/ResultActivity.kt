package com.batikfy.batikfy.ui.result

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.batikfy.batikfy.MainActivity
import com.batikfy.batikfy.R
import com.batikfy.batikfy.databinding.ActivityResultBinding
import com.batikfy.batikfy.utils.rotateFile
import java.io.File

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title = getString(R.string.result)
        onBack()

        val pictureFile = intent.getSerializableExtra("picture") as? File
        val isBackCamera = intent.getBooleanExtra("isBackCamera", true)

        pictureFile?.let { file ->
            binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
        }
    }

    private fun onBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@ResultActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    companion object {
        const val CAMERA_X_RESULT = 200
    }
}