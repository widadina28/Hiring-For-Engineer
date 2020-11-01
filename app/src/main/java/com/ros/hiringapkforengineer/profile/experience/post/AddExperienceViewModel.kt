package com.ros.hiringapkforengineer.profile.experience.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.home.experience.ExperienceApiService
import com.ros.hiringapkforengineer.profile.experience.post.AddExperienceResponse
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddExperienceViewModel : ViewModel() {
    private lateinit var service: ExperienceApiService
    private lateinit var sharedpref: SharedPrefUtil
    val isResponsePostExperience = MutableLiveData<AddExperienceResponse>()

    fun setSharedPreferences(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setServiceExperience(service: ExperienceApiService) {
        this.service = service
    }

    fun postExperience(position: String, name: String, start: String, end: String, desc: String) {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER_PROFILE)
        service.postExperience(idEngineer, position, name, start, end, desc)
            .enqueue(object : Callback<AddExperienceResponse> {
                override fun onFailure(call: Call<AddExperienceResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<AddExperienceResponse>,
                    response: Response<AddExperienceResponse>
                ) {
                    isResponsePostExperience.value = response.body()
                }

            })
    }
}