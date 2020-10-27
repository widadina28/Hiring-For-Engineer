package com.ros.hiringapkforengineer.form


import com.google.gson.annotations.SerializedName

data class FormProfileResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)