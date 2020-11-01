package com.ros.hiringapkforengineer.confirm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmViewModel : ViewModel() {
    val isConfirmResponse = MutableLiveData<List<ConfirmModel>>()
    private lateinit var service: ConfirmApiService
    private lateinit var sharedpref: SharedPrefUtil

    fun setSharedPreferences(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setServiceConfirm(service: ConfirmApiService) {
        this.service = service
    }

    fun callApiConfirm() {
        val id = sharedpref.getString(Constant.PREF_ID_ENGINEER_ACCOUNT)
        service.getHireByID(id).enqueue(object : Callback<AllConfirmResponse> {
            override fun onFailure(call: Call<AllConfirmResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<AllConfirmResponse>,
                response: Response<AllConfirmResponse>
            ) {
                val list = response.body()?.data?.map {
                    ConfirmModel(
                        it.idHire.orEmpty(),
                        it.nameCompany.orEmpty(),
                        it.projectName.orEmpty(),
                        it.description.orEmpty(),
                        it.image.orEmpty(),
                        it.status.orEmpty()
                    )

                } ?: listOf()
                isConfirmResponse.value = list
            }

        })
    }

}