package com.ros.hiringapkforengineer.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.form.job.JobModel
import com.ros.hiringapkforengineer.form.job.JobResponse
import com.ros.hiringapkforengineer.form.location.LocationModel
import com.ros.hiringapkforengineer.form.location.LocationResponse
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormProfileViewModel : ViewModel() {
    val isLocationSpinner = MutableLiveData<List<LocationModel>>()
    val isJobSpinner = MutableLiveData<List<JobModel>>()
    val isRequestResponse = MutableLiveData<Boolean>()

    private lateinit var service: PostProfileApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setSharedPref(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setFormService(service: PostProfileApiService) {
        this.service = service
    }

    fun requestEngineer(
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
        service.engineerRequest(name, job, loc, cost, rate, desc, image, status, id)
            .enqueue(object : Callback<FormProfileResponse> {
                override fun onFailure(call: Call<FormProfileResponse>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<FormProfileResponse>,
                    response: Response<FormProfileResponse>
                ) {
                    sharedpref.putString(Constant.PREF_ID_ENGINEER, response.body()?.data?.id)
                    isRequestResponse.value = true
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