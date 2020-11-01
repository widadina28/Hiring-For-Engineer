package com.ros.hiringapkforengineer.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.databinding.ActivityRegisterBinding
import com.ros.hiringapkforengineer.login.LoginActivity
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        sharedpref = SharedPrefUtil(applicationContext)
        val service = ApiClient.getApiClient(this)?.create(RegisterApiService::class.java)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setRegisterService(service)
        }
        setUpListener()
        subscribeLiveData()
    }

    private fun setUpListener() {
        binding.btnSignup.setOnClickListener {
            viewModel.callApi(
                binding.etFullname.text.toString(),
                binding.etEmailR.text.toString(),
                binding.etPasswordR.text.toString()
            )
        }
        binding.tvSignin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun subscribeLiveData() {
        viewModel.isRegisterLivedata.observe(this, Observer {
            if (it) {
                sharedpref.putBoolean(Constant.PREF_REGISTER, true)
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "FAILED!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}