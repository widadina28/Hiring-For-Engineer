package com.ros.hiringapkforengineer.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.bottomnav.BottomNavActivity
import com.ros.hiringapkforengineer.databinding.ActivityLoginBinding
import com.ros.hiringapkforengineer.register.RegisterActivity
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: ViewModelLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        sharedpref = SharedPrefUtil(applicationContext)
        val service = ApiClient.getApiClient(this)?.create(LoginApiServis::class.java)

        viewModel = ViewModelProvider(this).get(ViewModelLogin::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setLoginService(service)
        }
        setUpListener()
        subscribeLiveData()
    }

    private fun setUpListener(){
        binding.btnLogin.setOnClickListener {
            viewModel.callApi(binding.etEmail.text.toString(), binding.etPassword.text.toString())

        }
        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    override fun onStart() {
        super.onStart()
        if (sharedpref.getBoolean(Constant.PREF_IS_LOGIN))
        {
            startActivity(Intent(this, BottomNavActivity::class.java))
            finish()
        }
    }

    private fun subscribeLiveData(){
        viewModel.isResponseLogin.observe(this, Observer {
            if (it) {

            }
            else {
                Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.isLoginLiveData.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                viewModel.isRegisterLiveData.observe(this, Observer {
                    if (it) {
                        Toast.makeText(this, "To Form", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, RegisterActivity::class.java))
                    }
                    else {
                        startActivity(Intent(this, BottomNavActivity::class.java))
                        finish()
                    }
                })
            } else {
                Toast.makeText(this, "You Don't Have Access", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.isToastLogin.observe(this, Observer {
            if (it) {

            } else {
                Toast.makeText(this, "Fill The Blank!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}