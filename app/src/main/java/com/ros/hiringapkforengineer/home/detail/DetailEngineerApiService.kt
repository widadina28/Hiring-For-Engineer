package com.ros.hiringapkforengineer.home.detail

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailEngineerApiService {
    @GET("engineer/{id}")
    fun getEngineerByID(@Path("id") id: String?) : Call<EngineerResponseID>
}