package com.ros.hiringapkforengineer.home.detail


import com.google.gson.annotations.SerializedName

data class EngineerResponseID(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)