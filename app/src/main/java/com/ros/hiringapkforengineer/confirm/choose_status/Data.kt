package com.ros.hiringapkforengineer.confirm.choose_status


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("description")
    val description: String,
    @SerializedName("id_hire")
    val idHire: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name_company")
    val nameCompany: String,
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("status")
    val status: String
)