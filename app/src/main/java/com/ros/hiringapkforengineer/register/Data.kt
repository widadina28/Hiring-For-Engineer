package com.ros.hiringapkforengineer.register


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("createAT")
    val createAT: String,
    @SerializedName("email_account")
    val emailAccount: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name_account")
    val nameAccount: String,
    @SerializedName("role")
    val role: String
)