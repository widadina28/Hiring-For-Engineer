package com.ros.hiringapkforengineer.form


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("cost")
    val cost: String,
    @SerializedName("description_engineer")
    val descriptionEngineer: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("id_account")
    val idAccount: String,
    @SerializedName("id_freelance")
    val idFreelance: String,
    @SerializedName("id_loc")
    val idLoc: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name_engineer")
    val nameEngineer: String,
    @SerializedName("rate")
    val rate: String,
    @SerializedName("status")
    val status: String
)