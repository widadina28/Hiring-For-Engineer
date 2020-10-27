package com.ros.hiringapkforengineer.form.location


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("id_loc")
    val idLoc: String,
    @SerializedName("name_loc")
    val nameLoc: String
)