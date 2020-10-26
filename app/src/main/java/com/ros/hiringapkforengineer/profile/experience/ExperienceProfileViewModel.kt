package com.ros.hiringapkforengineer.profile.experience

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.home.experience.ExperienceApiService
import com.ros.hiringapkforengineer.home.experience.ExperienceModel
import com.ros.hiringapkforengineer.home.experience.ExperienceResponse
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExperienceProfileViewModel: ViewModel() {
    val isResponseExperience = MutableLiveData<List<ExperienceModel>>()
    private lateinit var service: ExperienceApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setSharedPreferences(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setServiceExperience(service: ExperienceApiService) {
        this.service = service
    }

    fun callApiExpProfile() {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        service.getExperienceByID(idEngineer).enqueue(object : Callback<ExperienceResponse> {
            override fun onFailure(call: Call<ExperienceResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<ExperienceResponse>, response: Response<ExperienceResponse>) {
                val list = response.body()?.data?.map {
                    ExperienceModel(it.idExperience.orEmpty(), it.position.orEmpty(), it.companyName.orEmpty(),
                        it.description.orEmpty())
                } ?: listOf()
                isResponseExperience.value = list
            }

        })
    }

}