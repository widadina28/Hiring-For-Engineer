package com.ros.hiringapkforengineer.confirm.choose_status

import com.ros.hiringapkforengineer.confirm.AllConfirmResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StatusApiService {
    @GET("hire/idhire/{id}")
    fun getHireByIDHire(@Path("id")id:String?): Call<ChooseStatusResponse>
}