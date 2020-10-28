package com.ros.hiringapkforengineer.home.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEngineerViewModel : ViewModel() {
    val isResponseDetail = MutableLiveData<EngineerResponseID>()

    private lateinit var service: DetailEngineerApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setSharedPreferences(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setServiceDetail(service: DetailEngineerApiService) {
        this.service = service
    }

    fun callApi() {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        Log.d("idEngDetailHome", "$idEngineer")
        service.getEngineerByID(idEngineer).enqueue(object : Callback<EngineerResponseID> {
            override fun onFailure(call: Call<EngineerResponseID>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<EngineerResponseID>,
                response: Response<EngineerResponseID>
            ) {
                isResponseDetail.value = response.body()
            }
        })
    }

}