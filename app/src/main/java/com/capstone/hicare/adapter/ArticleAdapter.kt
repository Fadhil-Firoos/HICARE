package com.capstone.hicare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hicare.MainModel
import com.capstone.hicare.R
import kotlin.collections.ArrayList

class ArticleAdapter (var results: ArrayList<MainModel.Result>, val listener: OnAdapterListener):
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.article_adapter, parent, false)
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.tvArticle.text = result.title // Memperbaiki penggunaan tvArticle
        Glide.with(holder.itemView)
            .load(result.image)
            .placeholder(R.drawable.sample)
            .error(R.drawable.sample)
            .centerCrop()
            .into(holder.imagePlaceholder) // Memperbaiki penggunaan imagePlaceholder
        holder.itemView.setOnClickListener { listener.onClick(result) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvArticle: TextView = itemView.findViewById(R.id.tvarticle) // Menambahkan inisialisasi tvArticle
        val imagePlaceholder: ImageView = itemView.findViewById(R.id.imageplaceholder) // Menambahkan inisialisasi imagePlaceholder
    }

    fun setData(data: List<MainModel.Result>) {
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: MainModel.Result)
    }
}
