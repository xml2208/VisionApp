package com.xml.visionapp.data.local

import com.xml.visionapp.R
import com.xml.visionapp.data.model.DoctorAdsModel

class DoctorAdsData {
    val list = listOf(
        DoctorAdsModel(
            doctorName = "Raximova Matluba Eshbayevna",
            description = "Candidate of Medical Sciences, Therapist",
            doctorImg = R.drawable.woman,
            doctorPhone = "+998-74-500-22-22"
        ),
        DoctorAdsModel(
            doctorName = "Regional multidisciplinary clinic",
            description = "st. Mashrab, building 39a, Andijan",
            doctorImg = R.drawable.clinic_img,
            doctorPhone = "+998-74-233-77-78"
        ),
    )
}