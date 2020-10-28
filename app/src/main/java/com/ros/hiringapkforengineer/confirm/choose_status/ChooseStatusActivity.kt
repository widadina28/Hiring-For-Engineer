package com.ros.hiringapkforengineer.confirm.choose_status

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.databinding.ActivityChooseStatusBinding
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import com.squareup.picasso.Picasso

class ChooseStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseStatusBinding
    private lateinit var viewModel: ChooseStatusViewModel
    private lateinit var sharedpref: SharedPrefUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_status)
        sharedpref = SharedPrefUtil(applicationContext)
        val service = ApiClient.getApiClient(this)?.create(StatusApiService::class.java)
        viewModel = ViewModelProvider(this).get(ChooseStatusViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null){
            viewModel.setServiceStatus(service)
        }
        if (sharedpref.getString(Constant.PREF_STATUS).equals("Pending")){
            binding.btnAccept.visibility = View.VISIBLE
            binding.buttonReject.visibility = View.VISIBLE
        } else {
            binding.btnAccept.visibility = View.GONE
            binding.buttonReject.visibility = View.GONE
        }
        setUpListener()
        viewModel.callApiStatus()

        subscribeLiveData()
    }
    private fun setUpListener(){
        binding.buttonReject.setOnClickListener {
            viewModel.updateReject()
        }
        binding.btnAccept.setOnClickListener {
            viewModel.updateAccept()
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
        viewModel.isUpdateResponseReject.observe(this, Observer {
            if (it){
                Toast.makeText(this, "You Reject The Project", Toast.LENGTH_LONG).show()
                finish()
            }

        })
        viewModel.isUpdateResponseAccept.observe(this, Observer {
            if (it){
                Toast.makeText(this, "You Accept The Project", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }
}