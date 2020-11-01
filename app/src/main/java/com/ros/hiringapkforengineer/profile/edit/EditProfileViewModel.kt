package com.ros.hiringapkforengineer.profile.edit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.form.job.JobModel
import com.ros.hiringapkforengineer.form.job.JobResponse
import com.ros.hiringapkforengineer.form.location.LocationModel
import com.ros.hiringapkforengineer.form.location.LocationResponse
import com.ros.hiringapkforengineer.profile.engineer.GetProfileResponse
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel : ViewModel() {

    val isResponseUpdate = MutableLiveData<Boolean>()
    val isLocationSpinner = MutableLiveData<List<LocationModel>>()
    val isJobSpinner = MutableLiveData<List<JobModel>>()
    val isResponseGetProfile = MutableLiveData<GetProfileResponse>()

    private lateinit var service: EditProfileResponse
    private lateinit var sharedpref: SharedPrefUtil

    fun setSharedPref(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setServiceEdit(service: EditProfileResponse) {
        this.service = service
    }

    fun updateEngineer(
        name: RequestBody,
        job: RequestBody,
        loc: RequestBody,
        cost: RequestBody,
        rate: RequestBody,
        desc: RequestBody,
        image: MultipartBody.Part?,
        status: RequestBody,
        id: RequestBody
    ) {
        val idEng = sharedpref.getString(Constant.PREF_ID_ENGINEER_ACCOUNT)
        service.engineerUpdate(idEng, name, job, loc, cost, rate, desc, image, status, id)
            .enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {

                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    isResponseUpdate.value = true
                }

            })
    }

    fun getDataProfile() {
        val id = sharedpref.getString(Constant.PREF_ID_ACC)
        service.getEngineerByIDAcc(id).enqueue(object : Callback<GetProfileResponse> {
            override fun onResponse(
                call: Call<GetProfileResponse>,
                response: Response<GetProfileResponse>
            ) {
                isResponseGetProfile.value = response.body()
            }

            override fun onFailure(call: Call<GetProfileResponse>, t: Throwable) {

            }

        })
    }

    fun spinnerJob() {
        service.getJob().enqueue(object : Callback<JobResponse> {
            override fun onFailure(call: Call<JobResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<JobResponse>, response: Response<JobResponse>) {
                val listJob = response.body()?.data?.map {
                    JobModel(it.idFreelance.orEmpty(), it.nameFreelance.orEmpty())
                } ?: listOf()
                isJobSpinner.value = listJob
            }

        })

    }

    fun spinnerLoc() {
        service.getLocation().enqueue(object : Callback<LocationResponse> {
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                val listLoc = response.body()?.data?.map {
                    LocationModel(it.idLoc.orEmpty(), it.nameLoc.orEmpty())
                } ?: listOf()
                isLocationSpinner.value = listLoc
            }

        })

    }
}