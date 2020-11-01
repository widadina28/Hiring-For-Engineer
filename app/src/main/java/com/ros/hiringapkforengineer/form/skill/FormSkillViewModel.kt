package com.ros.hiringapkforengineer.form.skill

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.form.PostProfileApiService
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormSkillViewModel : ViewModel() {
    val isListSkillResponse = MutableLiveData<List<SkillModel>>()
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var service: PostProfileApiService

    fun setSharedPref(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setService(service: PostProfileApiService) {
        this.service = service
    }

    fun postSkill1(id: String) {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        service.postSkill(id, idEngineer).enqueue(object : Callback<SkillResponse> {
            override fun onFailure(call: Call<SkillResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<SkillResponse>, response: Response<SkillResponse>) {

            }

        })
    }

    fun postSkill2(id: String) {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        service.postSkill(id, idEngineer).enqueue(object : Callback<SkillResponse> {
            override fun onFailure(call: Call<SkillResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<SkillResponse>, response: Response<SkillResponse>) {

            }

        })
    }

    fun postSkill3(id: String) {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        service.postSkill(id, idEngineer).enqueue(object : Callback<SkillResponse> {
            override fun onFailure(call: Call<SkillResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<SkillResponse>, response: Response<SkillResponse>) {

            }

        })
    }

    fun spinnerSkill() {
        service.getListSkill().enqueue(object : Callback<ListSkillResponse> {
            override fun onFailure(call: Call<ListSkillResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ListSkillResponse>,
                response: Response<ListSkillResponse>
            ) {
                val listSkill = response.body()?.data?.map {
                    SkillModel(it.idSkill.orEmpty(), it.nameSkill.orEmpty())
                } ?: listOf()
                isListSkillResponse.value = listSkill
            }

        })

    }
}