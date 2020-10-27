package com.ros.hiringapkforengineer.form.skill


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_engineer")
    val idEngineer: String,
    @SerializedName("id_skill")
    val idSkill: String
)