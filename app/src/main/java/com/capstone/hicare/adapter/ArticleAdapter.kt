package com.capstone.hicare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.hicare.R
import com.capstone.hicare.api.ArticleItem

class ArticleAdapter : ListAdapter<ArticleItem, ArticleAdapter.ArticleViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val newsItem = getItem(position)
        holder.bind(newsItem)
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        private val newsImageView: ImageView = itemView.findViewById(R.id.imgNews)

        fun bind(articleItem: ArticleItem) {
            titleTextView.text = articleItem.name
            itemView.findViewById<TextView>(R.id.tvLink).apply {
                text = "Baca Selengkapnya"
                //setTag(R.id.tvLink, articleItem.url)
                //visibility = if (articleItem.url != null) View.VISIBLE else View.GONE
            }

        }
    }

    class Comparator : DiffUtil.ItemCallback<ArticleItem>() {
        override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
            return oldItem == newItem
        }
    }
}