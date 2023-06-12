package com.batikfy.batikfy.ui.explore

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.remote.response.BatikItem
import com.batikfy.batikfy.databinding.ItemBatikLoveBinding
import com.batikfy.batikfy.ui.detail.batik.DetailBatikActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ExploreBatikAdapter(private val listBatik: List<BatikItem>) :
    RecyclerView.Adapter<ExploreBatikAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemBatikLoveBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemBatikLoveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(listBatik[position].image)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
            )
            .into(holder.binding.ivBatikLove)
        holder.binding.tvBatikLove.text = listBatik[position].name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailBatikActivity::class.java)
            intent.putExtra(DetailBatikActivity.DETAIL_BATIK, listBatik[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listBatik.size
}