package com.ros.hiringapkforengineer.form.skill


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("id_skill")
    val idSkill: String,
    @SerializedName("name_skill")
    val nameSkill: String
)