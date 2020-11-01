package com.ros.hiringapkforengineer.profile.portofolio.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.home.portofolio.PortofolioApiService
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPortfolioViewModel : ViewModel() {
    val isPostResponse = MutableLiveData<Boolean>()
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var service: PortofolioApiService

    fun setSharedPreferences(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setServicePortfolio(service: PortofolioApiService) {
        this.service = service
    }

    fun postPortfolio(
        id: RequestBody,
        name: RequestBody,
        link: RequestBody,
        img: MultipartBody.Part?,
        type: RequestBody
    ) {
        service.postPortfolio(id, name, link, img, type)
            .enqueue(object : Callback<AddPortfolioResponse> {
                override fun onFailure(call: Call<AddPortfolioResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<AddPortfolioResponse>,
                    response: Response<AddPortfolioResponse>
                ) {
                    isPostResponse.value = true
                }

            })

    }
}