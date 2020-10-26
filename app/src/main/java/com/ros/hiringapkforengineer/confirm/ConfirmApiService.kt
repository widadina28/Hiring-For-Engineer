package com.ros.hiringapkforengineer.confirm

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConfirmApiService {
    @GET("hire/{id}")
    fun getHireByID(@Path("id")id:String?): Call<AllConfirmResponse>
}