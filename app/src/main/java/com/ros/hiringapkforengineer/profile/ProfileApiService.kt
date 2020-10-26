package com.ros.hiringapkforengineer.profile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiService {
    @GET("engineer/account/{id}")
    fun getEngineerByID(@Path("id") id: String?) : Call<GetProfileResponse>
}