package com.ros.hiringapkforengineer.login

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiServis {
    @FormUrlEncoded
    @POST("account/login")
    suspend fun loginRequest(@Field("email_account") email: String?,
                             @Field("password") password: String?) : LoginResponse

}