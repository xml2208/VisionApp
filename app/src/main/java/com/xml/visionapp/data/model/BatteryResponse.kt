package com.xml.visionapp.data.model

import com.google.gson.annotations.SerializedName

data class BatteryResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("battery")
    val battery: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("nowOnline")
    val nowOnline: Boolean

)