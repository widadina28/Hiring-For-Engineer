package com.ros.hiringapkforengineer.register

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterApiService {
    @FormUrlEncoded
    @POST("account/register")
    suspend fun registerRequest(@Field("name_account") name_account: String?,
                                @Field("email_account") email: String?,
                                @Field("password") password: String?,
                                @Field("role") role: String?) : RegisterResponse
}