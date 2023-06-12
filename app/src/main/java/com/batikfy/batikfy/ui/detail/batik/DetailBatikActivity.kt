package com.batikfy.batikfy.ui.detail.batik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.Result
import com.batikfy.batikfy.databinding.ActivityDetailBatikBinding
import com.batikfy.batikfy.ui.home.GridBatikAdapter
import com.batikfy.batikfy.utils.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailBatikActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBatikBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBatikBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val detailBatikViewModel: DetailBatikViewModel by viewModels {
            factory
        }

        val id = intent.getStringExtra(DETAIL_BATIK) ?: ""
        if (id != ""){
            detailBatikViewModel.getDetailBatik(id).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Success -> {
                            binding.tvBatikName.text = result.data.data.batik.name
                            binding.tvBatikOrigin.text = result.data.data.batik.origin
                            binding.tvBatikMeaning.text = result.data.data.batik.meaning
                            Glide.with(this@DetailBatikActivity)
                                .load(result.data.data.batik.image)
                                .apply(
                                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                                        .error(R.drawable.ic_error)
                                )
                                .into(binding.ivBatik)
                            binding.loading.visibility = View.GONE
                        }
                        is Result.Error -> {
                            binding.loading.visibility = View.GONE
                            binding.tvBatikName.text = resources.getString(R.string.error_occurred)
                            binding.tvBatikOrigin.text = resources.getString(R.string.error_occurred)
                            binding.tvBatikMeaning.text = resources.getString(R.string.error_occurred)
                            Glide.with(this@DetailBatikActivity)
                                .load(R.drawable.ic_error)
                                .into(binding.ivBatik)
                            Toast.makeText(
                                this@DetailBatikActivity,
                                resources.getString(R.string.error_occurred) + ": " + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Result.Loading -> {
                            binding.tvBatikName.text = resources.getString(R.string.loading_data)
                            binding.tvBatikOrigin.text = resources.getString(R.string.loading_data)
                            binding.tvBatikMeaning.text = resources.getString(R.string.loading_data)
                            Glide.with(this@DetailBatikActivity)
                                .load(R.drawable.ic_loading)
                                .into(binding.ivBatik)
                            binding.loading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        detailBatikViewModel.getAllBatikData().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Success -> {
                        val selected4Data = result.data.data.batiks.shuffled().take(4)

                        // Init RecyclerView and Adapter
                        val gridBatikAdapter = GridBatikAdapter(selected4Data)

                        binding.rvReadMoreBatik.apply {
                            layoutManager = GridLayoutManager(this@DetailBatikActivity, 2)
                            adapter = gridBatikAdapter
                        }
                        binding.loading.visibility = View.GONE
                    }
                    is Result.Error -> {
                        binding.loading.visibility = View.GONE
                        Toast.makeText(
                            this@DetailBatikActivity,
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

    companion object {
        const val DETAIL_BATIK = "detail batik"
    }
}