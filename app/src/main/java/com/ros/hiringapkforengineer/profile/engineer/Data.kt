package com.ros.hiringapkforengineer.profile.engineer


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("cost")
    val cost: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("description_engineer")
    val descriptionEngineer: String,
    @SerializedName("email_account")
    val emailAccount: String,
    @SerializedName("id_engineer")
    val idEngineer: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name_engineer")
    val nameEngineer: String,
    @SerializedName("name_freelance")
    val nameFreelance: String,
    @SerializedName("name_loc")
    val nameLoc: String,
    @SerializedName("name_skill")
    val nameSkill: String,
    @SerializedName("rate")
    val rate: Int,
    @SerializedName("status")
    val status: String
)