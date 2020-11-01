package com.ros.hiringapkforengineer.profile.experience.post


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_engineer")
    val idEngineer: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("start")
    val start: String
)