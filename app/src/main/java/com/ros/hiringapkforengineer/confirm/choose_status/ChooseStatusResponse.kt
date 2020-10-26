package com.ros.hiringapkforengineer.confirm.choose_status


import com.google.gson.annotations.SerializedName

data class ChooseStatusResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)