package com.ros.hiringapkforengineer.profile.portofolio.post


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("aplication_name")
    val aplicationName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("id_engineer")
    val idEngineer: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("link_repo")
    val linkRepo: String,
    @SerializedName("type_porto")
    val typePorto: String
)