package com.ros.hiringapkforengineer.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ViewModelLogin : ViewModel(), CoroutineScope {
    val isLoginLiveData = MutableLiveData<Boolean>()
    val isRegisterLiveData = MutableLiveData<Boolean>()
    val isToastLogin = MutableLiveData<Boolean>()
    val isResponseLogin = MutableLiveData<Boolean>()

    private lateinit var service: LoginApiServis
    private lateinit var sharedpref: SharedPrefUtil

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedpref: SharedPrefUtil) {
        this.sharedpref = sharedpref
    }

    fun setLoginService(servis: LoginApiServis) {
        this.service = servis
    }

    fun callApi(email: String, password: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.loginRequest(
                        email, password
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is LoginResponse) {
                isResponseLogin.value = true
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    isToastLogin.value = true
                    if (response.data.role == "engineer") {
                        sharedpref.putString(Constant.PREF_TOKEN, response.data.token)
                        val a = sharedpref.putBoolean(Constant.PREF_IS_LOGIN, true)
                        sharedpref.putString(Constant.PREF_ID_ACC, response.data.idAccount)
                        sharedpref.putString(Constant.PREF_NAME, response.data.nameAccount)
                        val b =
                            sharedpref.putString(Constant.PREF_EMAIL, response.data.emailAccount)
                        val reg = sharedpref.getBoolean(Constant.PREF_REGISTER)
                        isLoginLiveData.value = true
                        isRegisterLiveData.value = reg
                    } else {
                        isLoginLiveData.value = false
                    }
                } else {
                    isToastLogin.value = false
                }
            } else {
                isResponseLogin.value = false
            }
        }

    }
}