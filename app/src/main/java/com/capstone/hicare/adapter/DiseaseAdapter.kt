package com.capstone.hicare.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.hicare.model.Disease
import java.util.ArrayList
import java.util.Locale
import com.capstone.hicare.databinding.ItemDiseaseBinding
import com.capstone.hicare.view.detail.DetailActivity
import com.capstone.hicare.view.detail.DetailActivity.Companion.EXTRA_DISEASE_DETAIL
import com.capstone.hicare.view.detail.DetailActivity.Companion.EXTRA_DISEASE_IMAGE
import com.capstone.hicare.view.detail.DetailActivity.Companion.EXTRA_DISEASE_NAME


class DiseaseAdapter (private val diseaseList: ArrayList<Disease>): RecyclerView.Adapter<DiseaseAdapter.RecyclerViewHolder>(),Filterable {
    private lateinit var onItemClickCallBack: OnItemClickCallBack
    private var filterDiseaseList: ArrayList<Disease> = diseaseList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = ItemDiseaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    inner class RecyclerViewHolder(private val binding: ItemDiseaseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(disease: Disease) {
            Glide.with(itemView.context)
                .load(disease.diseaseImage)
                .apply(RequestOptions().override(300, 300))
                .into(binding.ivItemPicture)
            binding.tvItemName.text = disease.diseaseName
            binding.tvSubName.text = disease.diseaseSubName
            itemView.setOnClickListener { onItemClickCallBack.onItemClicked(disease) }
        }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(filterDiseaseList[position])
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val disease = filterDiseaseList[position]
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_DISEASE_NAME, disease.diseaseName)
                putExtra(EXTRA_DISEASE_IMAGE, disease.diseaseImage ?: -1)
                putExtra(EXTRA_DISEASE_DETAIL, disease.diseaseDetail ?: -1)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        Log.d("DiseaseAdapter", "Number of items in adapter: ${filterDiseaseList.size}")
        return filterDiseaseList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val itemSearch = constraint.toString()
                filterDiseaseList = if (itemSearch.isEmpty()) {
                    diseaseList
                } else {
                    val List = ArrayList<Disease>()
                    for (item in diseaseList) {
                        if (item.diseaseName?.toLowerCase(Locale.ROOT)?.contains(
                                itemSearch.toLowerCase(
                                    Locale.ROOT
                                )
                            )!!
                        ) {
                            List.add(item)
                        }
                    }
                    List
                }
                var filterResults = FilterResults()
                filterResults.values = filterDiseaseList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, result: FilterResults?) {
                filterDiseaseList = result?.values as ArrayList<Disease>
                notifyDataSetChanged()
            }

        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallback
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: Disease)
    }
}