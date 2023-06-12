package com.batikfy.batikfy.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.remote.response.BlogsItem
import com.batikfy.batikfy.ui.detail.article.DetailArticleActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GridArticleAdapter(private var listBatiks: List<BlogsItem>) :
    RecyclerView.Adapter<GridArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_article, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blogItem = listBatiks[position]
        holder.bind(blogItem)
    }

    override fun getItemCount(): Int {
        return listBatiks.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<BlogsItem>) {
        listBatiks = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tv_article_title)
        private val tvDetail: TextView = view.findViewById(R.id.tv_article_detail)
        private val tvImage: ImageView = view.findViewById(R.id.iv_article)

        fun bind(blogItem: BlogsItem) {
            tvName.text = blogItem.title
            tvDetail.text = blogItem.textBlog
            Glide.with(itemView.context)
                .load(blogItem.image)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(tvImage)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.DETAIL_ARTICLE, blogItem.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}