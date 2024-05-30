package com.capstone.hicare.model.factory

import android.content.res.Resources
import android.content.res.TypedArray
import android.util.Log
import com.capstone.hicare.R
import com.capstone.hicare.model.Disease

object DiseaseList {
    var Diseases = ArrayList<Disease>()
    private lateinit var diseaseImage: TypedArray
    private lateinit var diseaseName: Array<String>
    private lateinit var diseaseDetail: TypedArray
    private lateinit var diseaseSubName: Array<String>

    fun addDisease(resources: Resources): ArrayList<Disease> {
        prepare(resources)
        for (position in diseaseName.indices) {
            val disease = Disease(
                diseaseName[position],
                diseaseImage.getResourceId(position, -1),
                diseaseDetail.getResourceId(position, -1),
                diseaseSubName[position]
            )
            Log.d("DiseaseList", "Number of diseases loaded: ${Diseases.size}")
            Diseases.add(disease)
        }

        return Diseases
    }


    private fun prepare(resources: Resources){
        diseaseName = resources.getStringArray(R.array.DiseaseName)
        diseaseImage = resources.obtainTypedArray(R.array.DiseaseImage)
        diseaseDetail = resources.obtainTypedArray(R.array.disease_layout)
        diseaseSubName = resources.getStringArray(R.array.DiseaseNameIndo)
    }
}