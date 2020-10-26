package com.ros.hiringapkforengineer.home.engineer


import com.google.gson.annotations.SerializedName
import com.ros.hiringapkforengineer.home.engineer.Data

data class EngineerResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)