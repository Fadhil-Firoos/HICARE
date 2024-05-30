package com.capstone.hicare.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.hicare.adapter.DiseaseAdapter
import com.capstone.hicare.databinding.FragmentHomeBinding
import com.capstone.hicare.model.Disease
import com.capstone.hicare.model.factory.DiseaseList.Diseases
import com.capstone.hicare.view.detail.DetailActivity.Companion.EXTRA_DISEASE_DETAIL
import com.capstone.hicare.view.detail.DetailActivity.Companion.EXTRA_DISEASE_IMAGE
import com.capstone.hicare.view.detail.DetailActivity.Companion.EXTRA_DISEASE_NAME
import com.capstone.hicare.view.main.MainActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var diseaseAdapter = DiseaseAdapter(Diseases)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.IdentifyDisease.setOnClickListener {
            (activity as MainActivity).navigateToCameraFragment()
        }

        binding.LastDiagnoseHeader.setOnClickListener {
            (activity as MainActivity).navigateToHistoryFragment()
        }

        binding.DiagnoseFromGallery.setOnClickListener {
            (activity as MainActivity).navigateToAnalyzeActivity()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        recyclerView()
    }


    fun recyclerView() {
        binding.apply {
            rvListDiseases.layoutManager = GridLayoutManager(activity, 1)

            rvListDiseases.adapter = diseaseAdapter

            diseaseAdapter.notifyDataSetChanged()
            rvListDiseases.setHasFixedSize(true)

            diseaseAdapter.setOnItemClickCallback(object : DiseaseAdapter.OnItemClickCallBack {
                override fun onItemClicked(data: Disease) {
                    selectedDisease(data)
                }
            })
        }
    }

    private fun selectedDisease(data: Disease) {
        val bundle = Bundle().apply {
            putString(EXTRA_DISEASE_NAME, data.diseaseName)
            data.diseaseImage?.let { putInt(EXTRA_DISEASE_IMAGE, it) }
            data.diseaseDetail?.let { putInt(EXTRA_DISEASE_DETAIL, it) }
        }
    }


        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


