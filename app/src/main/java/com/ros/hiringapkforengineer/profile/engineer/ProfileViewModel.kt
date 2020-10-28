package com.ros.hiringapkforengineer.profile.engineer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    val isResponseGetProfile = MutableLiveData<GetProfileResponse>()
    private lateinit var service: ProfileApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setSharedPreference(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setServiceProfile(service: ProfileApiService) {
        this.service = service
    }

    fun getApiEngineerProfile() {
        service.getEngineerByIDAcc(sharedpref.getString(Constant.PREF_ID_ACC)).enqueue(object : Callback<GetProfileResponse> {
            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<GetProfileResponse>, response: Response<GetProfileResponse>) {
                isResponseGetProfile.value = response.body()
                sharedpref.putString(Constant.PREF_ID_ENGINEER_PROFILE, response.body()?.data?.idEngineer)
            }
        })
    }
}