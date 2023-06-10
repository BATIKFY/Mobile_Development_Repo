package com.batikfy.batikfy.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batikfy.batikfy.R
import com.batikfy.batikfy.data.remote.response.BatikItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GridBatikAdapter(private val listBatiks: List<BatikItem>) :
    RecyclerView.Adapter<GridBatikAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: BatikItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_batik_other, viewGroup, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listBatik = listBatiks[position]
        holder.apply {
            tvName.text = listBatik.id
            Glide.with(itemView.context)
                .load(listBatik.image)
                .apply(RequestOptions.circleCropTransform())
                .into(tvImage)
            itemView.setOnClickListener { onItemClickCallback.onItemClicked(listBatik) }
        }
    }

    override fun getItemCount(): Int {
        return listBatiks.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_batik_grid)
        val tvImage: ImageView = view.findViewById(R.id.iv_batik_grid)
    }
}