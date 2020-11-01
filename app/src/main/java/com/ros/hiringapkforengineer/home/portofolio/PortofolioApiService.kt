package com.ros.hiringapkforengineer.home.portofolio

import com.ros.hiringapkforengineer.profile.portofolio.post.AddPortfolioResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PortofolioApiService {
    @GET("portofolio/{id}")
    fun getPortofolioByID(@Path("id")id:String?) : Call<PortofolioResponse>

    @Multipart
    @POST("portofolio")
    fun postPortfolio(@Part("id_engineer") idEngineer: RequestBody,
    @Part("aplication_name") app_name: RequestBody,
    @Part("link_repo") link_repo: RequestBody,
    @Part image: MultipartBody.Part?,
    @Part("type_porto") type_port: RequestBody) : Call<AddPortfolioResponse>

    @DELETE("portofolio/{id}")
    fun deletePortfolio(@Path("id") id: String?) : Call<Void>
}