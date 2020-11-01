package com.ros.hiringapkforengineer.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.home.engineer.EngineerModel
import com.ros.hiringapkforengineer.home.engineer.EngineerResponse
import com.ros.hiringapkforengineer.home.skill.SkillModel
import com.ros.hiringapkforengineer.profile.engineer.GetProfileResponse
import com.ros.hiringapkforengineer.profile.engineer.ProfileApiService
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    val isResponseAdapter = MutableLiveData<List<EngineerModel>>()

    private lateinit var service: HomeApiService
    private lateinit var service2: ProfileApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setSharedPreference(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setServiceEngineer(service: HomeApiService) {
        this.service = service
    }

    fun setServiceEngineerAccount(service2: ProfileApiService){
        this.service2 = service2
    }

    fun callApi() {
        service.getAllEngineer().enqueue(object : Callback<EngineerResponse> {
            override fun onFailure(call: Call<EngineerResponse>, t: Throwable) {
                Log.e("Home", t.message ?: "error")
            }

            override fun onResponse(
                call: Call<EngineerResponse>,
                response: Response<EngineerResponse>
            ) {
                Log.d("response get engineer", "${response.body()}")
                val list = response.body()?.data?.map {
                    it.nameSkill.split(",").map {
                        SkillModel(it)
                        Log.d("it", "$it")
                    }
                    EngineerModel(it.idEngineer.orEmpty(), it.nameEngineer.orEmpty(),
                        it.nameFreelance.orEmpty(), it.image.orEmpty(),
                        it.nameSkill.split(",").map {
                            SkillModel(it)
                        })
                } ?: listOf()
                isResponseAdapter.value = list
            }
        })

        val idEngineerAccount = sharedpref.getString(Constant.PREF_ID_ENGINEER_ACCOUNT)
        Log.d("idEngAcc", "$idEngineerAccount")
        if (idEngineerAccount==null){
            service2.getEngineerByIDAcc(sharedpref.getString(Constant.PREF_ID_ACC)).enqueue(object : Callback<GetProfileResponse>{
                override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {
                }

                override fun onResponse(call: Call<GetProfileResponse>, response: Response<GetProfileResponse>) {
                    val yuhu = sharedpref.putString(Constant.PREF_ID_ENGINEER_ACCOUNT, response.body()?.data?.idEngineer)


                }

            })
        }

        }


    }
