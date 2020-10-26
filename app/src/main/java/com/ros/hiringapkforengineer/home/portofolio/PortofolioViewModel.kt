package com.ros.hiringapkforengineer.home.portofolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PortofolioViewModel:ViewModel() {
    val isResponsePortfolio = MutableLiveData<List<PortofolioModel>>()
    private lateinit var service: PortofolioApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setServicePortfolio(service: PortofolioApiService){
        this.service = service
    }

    fun setSharedPreference(sharedpref: SharedPrefUtil){
        this.sharedpref = sharedpref
    }

    fun callApiPort(){
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        service.getPortofolioByID(idEngineer).enqueue(object : Callback<PortofolioResponse>{
            override fun onFailure(call: Call<PortofolioResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<PortofolioResponse>, response: Response<PortofolioResponse>) {
                val list = response.body()?.data?.map {
                    PortofolioModel(it.idPortofolio.orEmpty(), it.image.orEmpty())
                } ?: listOf()
                isResponsePortfolio.value = list
            }

        })
    }
}