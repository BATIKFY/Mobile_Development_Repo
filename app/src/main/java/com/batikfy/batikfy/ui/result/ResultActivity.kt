package com.batikfy.batikfy.ui.result

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.batikfy.batikfy.BuildConfig
import com.batikfy.batikfy.data.Result
import com.batikfy.batikfy.MainActivity
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.remote.response.PostScanResponse
import com.batikfy.batikfy.databinding.ActivityResultBinding
import com.batikfy.batikfy.utils.ViewModelFactory
import com.batikfy.batikfy.utils.reduceFileImage
import com.batikfy.batikfy.utils.rotateFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.DecimalFormat

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

        val file = reduceFileImage(pictureFile as File)
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "image",
            file.name,
            requestImageFile
        )

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this, BuildConfig.SCAN_URL)
        val resultViewModel: ResultViewModel by viewModels {
            factory
        }
        resultViewModel.scanImage(imageMultipart)
            .observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            setResult(result.data)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this, result.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
    }

    private fun setResult(data: PostScanResponse) {
        // Temporary
        binding.tvBatikName.text = "Batik" + " " + data.predictedClass
        binding.tvBatikDesc.text =
            "This is batik description that have some sentences. But I am to lazy add lorem ipsum, so yeah i wrote this instead."

        // set accuracy in donut chart
        val percentage = data.confidence.toFloat()
        binding.progressResult.progress = percentage
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