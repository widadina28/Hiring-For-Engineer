package com.ros.hiringapkforengineer.form.job


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("id_freelance")
    val idFreelance: String,
    @SerializedName("name_freelance")
    val nameFreelance: String
)