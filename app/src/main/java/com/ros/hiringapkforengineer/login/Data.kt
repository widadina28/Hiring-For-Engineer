package com.ros.hiringapkforengineer.login


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("email_account")
    val emailAccount: String,
    @SerializedName("id_account")
    val idAccount: Int,
    @SerializedName("name_account")
    val nameAccount: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("token")
    val token: String
)