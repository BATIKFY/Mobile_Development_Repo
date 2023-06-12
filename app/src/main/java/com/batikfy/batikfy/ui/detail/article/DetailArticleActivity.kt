package com.batikfy.batikfy.ui.detail.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.batikfy.batikfy.MainActivity
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.Result
import com.batikfy.batikfy.databinding.ActivityDetailArticleBinding
import com.batikfy.batikfy.ui.home.GridArticleAdapter
import com.batikfy.batikfy.utils.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailArticleActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.readMoreBtnArticle.setOnClickListener(this)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val detailArticleViewModel: DetailArticleViewModel by viewModels {
            factory
        }

        val id = intent.getStringExtra(DETAIL_ARTICLE) ?: ""
        if(id != ""){
            detailArticleViewModel.getDetailArticle(id).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Success -> {
                            binding.tvArticleTitle.text = result.data.data.blog.title
                            binding.tvArticleText.text = result.data.data.blog.textBlog
                            binding.tvArticleSource.text = result.data.data.blog.source
                            Glide.with(this@DetailArticleActivity)
                                .load(result.data.data.blog.image)
                                .apply(
                                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                                        .error(R.drawable.ic_error)
                                )
                                .into(binding.ivArticle)
                            binding.loading.visibility = View.GONE
                        }
                        is Result.Error -> {
                            binding.loading.visibility = View.GONE
                            binding.tvArticleTitle.text = resources.getString(R.string.error_occurred)
                            binding.tvArticleText.text = resources.getString(R.string.error_occurred)
                            binding.tvArticleSource.text = resources.getString(R.string.error_occurred)
                            Glide.with(this@DetailArticleActivity)
                                .load(R.drawable.ic_error)
                                .into(binding.ivArticle)
                            Toast.makeText(
                                this@DetailArticleActivity,
                                resources.getString(R.string.error_occurred) + ": " + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Result.Loading -> {
                            binding.tvArticleTitle.text = resources.getString(R.string.loading_data)
                            binding.tvArticleText.text = resources.getString(R.string.loading_data)
                            binding.tvArticleSource.text = resources.getString(R.string.loading_data)
                            Glide.with(this@DetailArticleActivity)
                                .load(R.drawable.ic_loading)
                                .into(binding.ivArticle)
                            binding.loading.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        detailArticleViewModel.getAllArticleData().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Success -> {
                        val selected3Data = result.data.data.blogs.shuffled().take(3)

                        // Init RecyclerView and Adapter
                        val gridArticleAdapter = GridArticleAdapter(selected3Data)

                        binding.rvReadMoreArticle.apply {
                            layoutManager = GridLayoutManager(this@DetailArticleActivity, 1)
                            adapter = gridArticleAdapter
                        }
                        binding.loading.visibility = View.GONE
                    }
                    is Result.Error -> {
                        binding.loading.visibility = View.GONE
                        Toast.makeText(
                            this@DetailArticleActivity,
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
        const val DETAIL_ARTICLE = "detail article"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.read_more_btn_article -> {
                val intent = Intent(this@DetailArticleActivity, MainActivity::class.java)
                intent.putExtra("activeTab", R.id.navigation_explore)
                intent.putExtra("activeFragment", "article")
                startActivity(intent)
            }
        }
    }
}