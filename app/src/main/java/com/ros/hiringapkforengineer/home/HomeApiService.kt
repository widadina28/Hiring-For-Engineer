package com.ros.hiringapkforengineer.home

import com.ros.hiringapkforengineer.home.engineer.EngineerResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeApiService {
    @GET("engineer")
    fun getAllEngineer(): Call<EngineerResponse>
}