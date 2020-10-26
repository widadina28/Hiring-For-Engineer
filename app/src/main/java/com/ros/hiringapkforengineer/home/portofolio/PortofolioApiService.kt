package com.ros.hiringapkforengineer.home.portofolio

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PortofolioApiService {
    @GET("portofolio/{id}")
    fun getPortofolioByID(@Path("id")id:String?) : Call<PortofolioResponse>
}