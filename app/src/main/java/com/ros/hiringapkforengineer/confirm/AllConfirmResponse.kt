package com.ros.hiringapkforengineer.confirm


import com.google.gson.annotations.SerializedName

data class AllConfirmResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)