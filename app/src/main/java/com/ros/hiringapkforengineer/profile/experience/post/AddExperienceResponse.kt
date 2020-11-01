package com.ros.hiringapkforengineer.profile.experience.post


import com.google.gson.annotations.SerializedName

data class AddExperienceResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)