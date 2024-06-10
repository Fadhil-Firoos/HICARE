package com.capstone.hicare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hicare.R
import com.capstone.hicare.history.PredictionHistory

class PredictionHistoryAdapter(private val predictionList: List<PredictionHistory>) :
    RecyclerView.Adapter<PredictionHistoryAdapter.ViewHolder>() {

    private var onDeleteClickListener: OnDeleteClickListener? = null

    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int)
    }

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = predictionList[position]
        if (currentItem.result.isNotEmpty()) {
            holder.bind(currentItem)
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) // Set layout params to match parent and wrap content
        } else {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }
    }

    override fun getItemCount() = predictionList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.img_sample)
        private val resultTextView: TextView = itemView.findViewById(R.id.tvname)
        private val deleteImageView: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(prediction: PredictionHistory) {
            Glide.with(itemView.context)
                .load(prediction.imagePath)
                .placeholder(R.drawable.sample)
                .error(R.drawable.ic_launcher_background)
                .into(imageView)

            resultTextView.text = prediction.result

            deleteImageView.setOnClickListener {
                onDeleteClickListener?.onDeleteClick(adapterPosition)
            }
        }
    }
}