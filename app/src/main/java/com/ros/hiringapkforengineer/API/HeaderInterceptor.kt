package com.ros.hiringapkforengineer.API

import android.content.Context
import android.util.Log
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(val mcontext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val sharepref = SharedPrefUtil(context = mcontext)
        val token = sharepref.getString(Constant.PREF_TOKEN)
        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }
}