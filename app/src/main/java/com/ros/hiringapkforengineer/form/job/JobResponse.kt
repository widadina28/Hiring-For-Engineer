package com.ros.hiringapkforengineer.form.job


import com.google.gson.annotations.SerializedName

data class JobResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)