package com.ros.hiringapkforengineer.profile.portofolio.post


import com.google.gson.annotations.SerializedName

data class AddPortfolioResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)