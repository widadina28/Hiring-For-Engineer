package com.ros.hiringapkforengineer.form.skill


import com.google.gson.annotations.SerializedName

data class SkillResponse(
    @SerializedName("data")
    val `data`: DataX,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)