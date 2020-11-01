package com.ros.hiringapkforengineer.profile.engineer

import com.ros.hiringapkforengineer.profile.engineer.GetProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiService {
    @GET("engineer/account/{id}")
    fun getEngineerByIDAcc(@Path("id") id: String?): Call<GetProfileResponse>
}