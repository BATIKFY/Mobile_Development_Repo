package com.batikfy.batikfy.ui.result

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.batikfy.batikfy.MainActivity
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.Result
import com.batikfy.batikfy.databinding.ActivityResultBinding
import com.batikfy.batikfy.ui.home.GridBatikAdapter
import com.batikfy.batikfy.utils.ViewModelFactory
import com.batikfy.batikfy.utils.reduceFileImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ResultActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.result)
        onBack()

        binding.readMoreBtnBatik.setOnClickListener(this)

        val pictureFile = intent.getSerializableExtra("picture") as? File

        pictureFile?.let { file ->
            binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
        }

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val resultViewModel: ResultViewModel by viewModels {
            factory
        }

        lifecycleScope.launch {
            val file = reduceFileImage(pictureFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )
            if (imageMultipart != null) {
                resultViewModel.scanImage(imageMultipart)
                    .observe(this@ResultActivity) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    showLoading(true)
                                    binding.tvBatikName.text =
                                        resources.getString(R.string.loading_data)
                                    binding.tvBatikDesc.text =
                                        resources.getString(R.string.loading_data)
                                }
                                is Result.Success -> {
                                    showLoading(false)
                                    resultViewModel.getBatikData(result.data.predictedClass)
                                        .observe(this@ResultActivity) { result2 ->
                                            if (result2 != null) {
                                                when (result2) {
                                                    is Result.Loading -> {
                                                        showLoading(true)
                                                        binding.tvBatikName.text =
                                                            resources.getString(R.string.loading_data)
                                                        binding.tvBatikDesc.text =
                                                            resources.getString(R.string.loading_data)
                                                    }
                                                    is Result.Success -> {
                                                        showLoading(false)
                                                        binding.tvBatikName.text =
                                                            result2.data.data.batik?.get(0)?.name
                                                        binding.tvBatikDesc.text =
                                                            result2.data.data.batik?.get(0)?.meaning
                                                    }
                                                    is Result.Error -> {
                                                        showLoading(false)
                                                        Toast.makeText(
                                                            this@ResultActivity,
                                                            result2.error,
                                                            Toast.LENGTH_LONG
                                                        )
                                                            .show()
                                                        binding.tvBatikName.text =
                                                            resources.getString(R.string.batik_name_not_found)
                                                        binding.tvBatikDesc.text =
                                                            resources.getString(R.string.batik_desc_not_found)
                                                    }
                                                }
                                            }
                                        }

                                    // set accuracy in donut chart
                                    val percentage = result.data.confidence.toFloat()
                                    binding.progressResult.progress = percentage
                                }
                                is Result.Error -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        this@ResultActivity,
                                        result.error,
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    binding.tvBatikName.text =
                                        resources.getString(R.string.batik_name_not_found)
                                    binding.tvBatikDesc.text =
                                        resources.getString(R.string.batik_desc_not_found)
                                }
                            }
                        }
                    }
            }
        }

        resultViewModel.getAllBatikData().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Success -> {
                        val selected4Data = result.data.data.batiks.shuffled().take(4)

                        // Init RecyclerView and Adapter
                        val gridBatikAdapter = GridBatikAdapter(selected4Data)

                        binding.rvReadMoreBatik.apply {
                            layoutManager = GridLayoutManager(this@ResultActivity, 2)
                            adapter = gridBatikAdapter
                        }
                        binding.loading.visibility = View.GONE
                    }
                    is Result.Error -> {
                        binding.loading.visibility = View.GONE
                        Toast.makeText(
                            this@ResultActivity,
                            resources.getString(R.string.error_occurred) + ": " + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Result.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                }
            }
        }

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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.read_more_btn_batik -> {
                Toast.makeText(
                    this@ResultActivity,
                    resources.getString(R.string.feature_not_ready),
                    Toast.LENGTH_SHORT
                ).show()
//                val intent = Intent(this@ResultActivity, MainActivity::class.java)
//                intent.putExtra("activeTab", R.id.navigation_explore)
//                intent.putExtra("activeFragment", "batik")
//                startActivity(intent)
            }
        }
    }
}