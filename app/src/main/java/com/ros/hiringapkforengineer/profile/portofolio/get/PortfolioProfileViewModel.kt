package com.ros.hiringapkforengineer.profile.portofolio.get

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.home.portofolio.PortofolioApiService
import com.ros.hiringapkforengineer.home.portofolio.PortofolioModel
import com.ros.hiringapkforengineer.home.portofolio.PortofolioResponse
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PortfolioProfileViewModel : ViewModel() {
    val isResponsePortfolio = MutableLiveData<List<PortofolioModel>>()
    val isResponseDelete = MutableLiveData<Void>()
    private lateinit var service: PortofolioApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setServicePortfolio(service: PortofolioApiService) {
        this.service = service
    }

    fun setSharedPreference(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun callApiPort() {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER_PROFILE)
        service.getPortofolioByID(idEngineer).enqueue(object : Callback<PortofolioResponse> {
            override fun onFailure(call: Call<PortofolioResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<PortofolioResponse>,
                response: Response<PortofolioResponse>
            ) {
                val list = response.body()?.data?.map {
                    PortofolioModel(it.idPortofolio.orEmpty(), it.image.orEmpty())
                } ?: listOf()
                isResponsePortfolio.value = list
            }

        })
    }

    fun delete(id: String) {
        service.deletePortfolio(id).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isResponseDelete.value = response.body()

            }

        })
    }
}