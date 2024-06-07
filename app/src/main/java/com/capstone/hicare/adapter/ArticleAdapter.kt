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

class ArticleAdapter(
    private var results: MutableList<MainModel.Result>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.bind(result)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvArticle: TextView = itemView.findViewById(R.id.tvarticle)
        private val imagePlaceholder: ImageView = itemView.findViewById(R.id.imageplaceholder)
        private lateinit var currentResult: MainModel.Result

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(result: MainModel.Result) {
            currentResult = result
            tvArticle.text = result.name
            Glide.with(itemView)
                .load(result.url_image)
                .placeholder(R.drawable.sample)
                .error(R.drawable.sample)
                .centerCrop()
                .into(imagePlaceholder)
        }

        override fun onClick(v: View?) {
            listener.onClick(currentResult)
        }
    }

    fun setData(data: List<MainModel.Result>) {
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: MainModel.Result)
    }
}
