package com.ros.hiringapkforengineer.profile


import com.google.gson.annotations.SerializedName

data class GetProfileResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)