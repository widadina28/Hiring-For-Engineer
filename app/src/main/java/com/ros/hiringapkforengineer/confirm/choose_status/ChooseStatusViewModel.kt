package com.ros.hiringapkforengineer.confirm.choose_status

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.confirm.ConfirmApiService
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseStatusViewModel: ViewModel() {
    val isDetailResponse = MutableLiveData<ChooseStatusResponse>()
    val isUpdateResponseReject = MutableLiveData<Boolean>()
    val isUpdateResponseAccept = MutableLiveData<Boolean>()

    private lateinit var service: StatusApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setSharedPreference(sharedpref: SharedPrefUtil){
        this.sharedpref = sharedpref
    }

    fun setServiceStatus(service: StatusApiService){
        this.service = service
    }

    fun callApiStatus(){
        val id = sharedpref.getString(Constant.PREF_ID_HIRE)
        service.getHireByIDHire(id).enqueue(object : Callback<ChooseStatusResponse>{
            override fun onFailure(call: Call<ChooseStatusResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ChooseStatusResponse>,
                response: Response<ChooseStatusResponse>
            ) {
                Log.d("Response detail status", "${response.body()}")
                isDetailResponse.value = response.body()
            }

        })
    }

    fun updateReject(){
        val id = sharedpref.getString(Constant.PREF_ID_HIRE)
        service.getUpdate(id, "Reject").enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isUpdateResponseReject.value = true
            }

        })

    }

    fun updateAccept(){
        val id = sharedpref.getString(Constant.PREF_ID_HIRE)
        service.getUpdate(id, "Confirmed" ).enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isUpdateResponseAccept.value = true
            }

        })

    }

}