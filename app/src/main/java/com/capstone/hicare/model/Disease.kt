package com.capstone.hicare.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Disease (
    val diseaseName: String?,
    val diseaseImage: Int?,
    val diseaseDetail: Int?,
    val diseaseSubName: String?,
): Parcelable


