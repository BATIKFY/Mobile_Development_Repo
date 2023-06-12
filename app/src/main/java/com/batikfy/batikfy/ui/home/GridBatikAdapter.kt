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
import com.batikfy.batikfy.data.local.entity.BatikEntity
import com.batikfy.batikfy.data.remote.response.BatikItem
import com.batikfy.batikfy.ui.detail.batik.DetailBatikActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GridBatikAdapter(private var listBatiks: List<BatikItem>) :
    RecyclerView.Adapter<GridBatikAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: BatikItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_batik_other, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val batikItem = listBatiks[position]
        holder.bind(batikItem)
    }

    override fun getItemCount(): Int {
        return listBatiks.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<BatikItem>) {
        listBatiks = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tv_batik_grid)
        private val tvImage: ImageView = view.findViewById(R.id.iv_batik_grid)

        fun bind(batikItem: BatikItem) {
            tvName.text = batikItem.name
            Glide.with(itemView.context)
                .load(batikItem.image)
                .apply(RequestOptions.circleCropTransform())
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(tvImage)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailBatikActivity::class.java)
                intent.putExtra(DetailBatikActivity.DETAIL_BATIK, batikItem.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}
