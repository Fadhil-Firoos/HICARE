package com.capstone.hicare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val identifyDiseaseCard = view.findViewById<CardView>(R.id.IdentifyDisease)
        identifyDiseaseCard.setOnClickListener {
            (activity as MainActivity).navigateToCameraFragment()
        }


        val historyCard = view.findViewById<CardView>(R.id.LastDiagnoseHeader)
        historyCard.setOnClickListener {
            (activity as MainActivity).navigateToHistoryFragment()
        }

        return view
    }
}
