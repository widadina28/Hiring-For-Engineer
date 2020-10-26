package com.ros.hiringapkforengineer.confirm.choose_status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.databinding.ActivityChooseStatusBinding
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import com.squareup.picasso.Picasso

class ChooseStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseStatusBinding
    private lateinit var viewModel: ChooseStatusViewModel
    private lateinit var sharedpref: SharedPrefUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_status)
        sharedpref = SharedPrefUtil(applicationContext)
        val service = ApiClient.getApiClient(this)?.create(StatusApiService::class.java)
        viewModel = ViewModelProvider(this).get(ChooseStatusViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null){
            viewModel.setServiceStatus(service)
            viewModel.callApiStatus()
            subscribeLiveData()
        }
    }

    private fun subscribeLiveData(){
        viewModel.isDetailResponse.observe(this, Observer {
            binding.nameCompanyHire.text = it.data.nameCompany
            binding.nameProjectHire.text = it.data.projectName
            binding.descriptionProjectHire.text = it.data.description
            Picasso.get().load("http://3.80.45.131:8080/uploads/" + it.data.image)
                .placeholder(R.drawable.ic_picture)
                .into(binding.ivProjectHire)
        })
    }
}