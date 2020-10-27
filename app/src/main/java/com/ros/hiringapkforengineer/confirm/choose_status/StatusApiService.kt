package com.ros.hiringapkforengineer.confirm.choose_status

import com.ros.hiringapkforengineer.confirm.AllConfirmResponse
import retrofit2.Call
import retrofit2.http.*

interface StatusApiService {
    @GET("hire/idhire/{id}")
    fun getHireByIDHire(@Path("id")id:String?): Call<ChooseStatusResponse>

    @FormUrlEncoded
    @PATCH("hire/{id}")
    fun getUpdate(@Path("id")id:String?,
    @Field("status") status:String): Call<Void>
}