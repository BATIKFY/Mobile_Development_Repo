package com.batikfy.batikfy.ui.explore

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.remote.response.BlogsItem
import com.batikfy.batikfy.databinding.ItemArticleLoveBinding
import com.batikfy.batikfy.ui.detail.article.DetailArticleActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ExploreArticleAdapter(private val listBlog: List<BlogsItem>) :
    RecyclerView.Adapter<ExploreArticleAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemArticleLoveBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemArticleLoveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listBlog[position].image)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
            )
            .into(holder.binding.ivArticle)
        holder.binding.tvArticleTitle.text = listBlog[position].title
        holder.binding.tvArticleDetail.text = listBlog[position].textBlog
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailArticleActivity::class.java)
            intent.putExtra(DetailArticleActivity.DETAIL_ARTICLE, listBlog[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listBlog.size
}