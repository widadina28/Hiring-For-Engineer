package com.ros.hiringapkforengineer.form

import com.ros.hiringapkforengineer.form.job.JobResponse
import com.ros.hiringapkforengineer.form.location.LocationResponse
import com.ros.hiringapkforengineer.form.skill.ListSkillResponse
import com.ros.hiringapkforengineer.form.skill.SkillResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PostProfileApiService {
    @Multipart
    @POST("engineer")
    fun engineerRequest(@Part("name_engineer") nameEngineer: RequestBody,
    @Part("id_freelance") idFreelance: RequestBody,
    @Part("id_loc") idLoc: RequestBody,
    @Part("cost") cost: RequestBody,
    @Part("rate") rate: RequestBody,
    @Part("description_engineer") description: RequestBody,
    @Part image: MultipartBody.Part?,
    @Part("status") status: RequestBody,
    @Part("id_account") idAcc: RequestBody) : Call<FormProfileResponse>

    @GET("location")
    fun getLocation(): Call<LocationResponse>

    @GET("freelance")
    fun getJob(): Call<JobResponse>

    @FormUrlEncoded
    @POST("expertise")
    fun postSkill(@Field("id_skill") idSkill: String,
                  @Field("id_engineer") idEngineer: String?) : Call<SkillResponse>

    @GET("skill")
    fun getListSkill(): Call<ListSkillResponse>
}