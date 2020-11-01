package com.ros.hiringapkforengineer.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.home.HomeApiService
import com.ros.hiringapkforengineer.home.engineer.EngineerModel
import com.ros.hiringapkforengineer.home.engineer.EngineerResponse
import com.ros.hiringapkforengineer.home.skill.SkillModel
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {
    val isResponseAdapter = MutableLiveData<List<EngineerModel>>()

    private lateinit var service: HomeApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setSharedPreference(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setServiceEngineer(service: HomeApiService) {
        this.service = service
    }

    fun callApiEngSearch() {
        service.getAllEngineer().enqueue(object : Callback<EngineerResponse> {
            override fun onFailure(call: Call<EngineerResponse>, t: Throwable) {
                Log.e("Home", t.message ?: "error")
            }

            override fun onResponse(
                call: Call<EngineerResponse>,
                response: Response<EngineerResponse>
            ) {
                val list = response.body()?.data?.map {
                    EngineerModel(it.idEngineer.orEmpty(), it.nameEngineer.orEmpty(),
                        it.nameFreelance.orEmpty(), it.image.orEmpty(),
                        it.nameSkill.split(",").map {
                            SkillModel(it)
                        })
                } ?: listOf()
                isResponseAdapter.value = list
            }
        })
    }
}