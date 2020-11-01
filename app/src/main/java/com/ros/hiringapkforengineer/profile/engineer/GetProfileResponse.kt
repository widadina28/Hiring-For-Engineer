package com.ros.hiringapkforengineer.profile.engineer


import com.google.gson.annotations.SerializedName
import com.ros.hiringapkforengineer.profile.engineer.Data

data class GetProfileResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)