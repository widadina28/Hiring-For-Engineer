package com.ros.hiringapkforengineer.home.experience

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ExperienceApiService {
    @GET("experience/{id}")
    fun getExperienceByID(@Path("id")id:String?) : Call<ExperienceResponse>

}