package com.ros.hiringapkforengineer.home.experience

import com.ros.hiringapkforengineer.profile.experience.post.AddExperienceResponse
import retrofit2.Call
import retrofit2.http.*

interface ExperienceApiService {
    @GET("experience/{id}")
    fun getExperienceByID(@Path("id")id:String?) : Call<ExperienceResponse>

    @FormUrlEncoded
    @POST("experience")
    fun postExperience(@Field("id_engineer") idEngineer: String?,
                       @Field("position") position: String,
                       @Field("company_name") companyName: String,
                       @Field("start") start: String,
                       @Field("end") end: String,
                       @Field("description") description: String) : Call<AddExperienceResponse>

    @DELETE("experience/{id}")
    fun deleteexperience(@Path("id") id: String) : Call<Void>

}