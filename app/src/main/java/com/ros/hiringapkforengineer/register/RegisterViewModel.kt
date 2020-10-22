package com.ros.hiringapkforengineer.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegisterViewModel: ViewModel(), CoroutineScope {
    val isRegisterLivedata = MutableLiveData<Boolean>()

    private lateinit var service: RegisterApiService
    private lateinit var sharedpref: SharedPrefUtil

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedpref: SharedPrefUtil){
        this.sharedpref =sharedpref
    }

    fun setRegisterService(service: RegisterApiService){
        this.service = service
    }

    fun callApi(name: String, email: String, password:String){
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.registerRequest(name, email, password, "engineer")
                } catch (e: Throwable) {

                }
            }
            Log.d("Response", "$response")
            if (response is RegisterResponse) {
                isRegisterLivedata.value = true
            }
        }

    }
}