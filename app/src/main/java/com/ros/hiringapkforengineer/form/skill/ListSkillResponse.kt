package com.ros.hiringapkforengineer.form.skill


import com.google.gson.annotations.SerializedName

data class ListSkillResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)