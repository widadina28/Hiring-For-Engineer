package com.ros.hiringapkforengineer.form.location


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)