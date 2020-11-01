package com.ros.hiringapkforengineer.profile.edit

import com.ros.hiringapkforengineer.form.FormProfileResponse
import com.ros.hiringapkforengineer.form.job.JobResponse
import com.ros.hiringapkforengineer.form.location.LocationResponse
import com.ros.hiringapkforengineer.profile.engineer.GetProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface EditProfileResponse {
    @Multipart
    @PUT("engineer/{id}")
    fun engineerUpdate(
        @Path("id") id: String?,
        @Part("name_engineer") nameEngineer: RequestBody,
        @Part("id_freelance") idFreelance: RequestBody,
        @Part("id_loc") idLoc: RequestBody,
        @Part("cost") cost: RequestBody,
        @Part("rate") rate: RequestBody,
        @Part("description_engineer") description: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("status") status: RequestBody,
        @Part("id_account") idAcc: RequestBody
    ): Call<Void>

    @GET("location")
    fun getLocation(): Call<LocationResponse>

    @GET("freelance")
    fun getJob(): Call<JobResponse>

    @GET("engineer/account/{id}")
    fun getEngineerByIDAcc(@Path("id") id: String?): Call<GetProfileResponse>
}