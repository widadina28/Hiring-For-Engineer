package com.ros.hiringapkforengineer.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.databinding.ActivityDetailEngineerBinding
import com.ros.hiringapkforengineer.home.skill.SkillAdapter
import com.ros.hiringapkforengineer.home.skill.SkillModel
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import com.squareup.picasso.Picasso

class DetailEngineerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEngineerBinding
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: DetailEngineerViewModel
    private lateinit var rvSkill : SkillAdapter
    private lateinit var vpadapter : VPDetailEngineerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_engineer)
        sharedpref = SharedPrefUtil(applicationContext)
        val service = ApiClient.getApiClient(this)?.create(DetailEngineerApiService::class.java)

        viewModel = ViewModelProvider(this).get(DetailEngineerViewModel::class.java)
        viewModel.setSharedPreferences(sharedpref)

        if (service!= null) {
            viewModel.setServiceDetail(service)
        }
        viewModel.callApi()
        subscribeLiveData()

        vpadapter = VPDetailEngineerAdapter(supportFragmentManager)
        binding.viewProfile.adapter=vpadapter
        binding.tabProfile.setupWithViewPager(binding.viewProfile)
    }

    private fun subscribeLiveData(){
        viewModel.isResponseDetail.observe(this, Observer {
            binding.nameProfile.text=it.data.nameEngineer
            binding.jobProfile.text=it.data.nameFreelance
            binding.locationProfile.text=it.data.nameLoc
            binding.statusProfile.text=it.data.status
            binding.emailProfile.text=it.data.emailAccount
            binding.descriptionProfile.text=it.data.descriptionEngineer
            Picasso.get().load("http://3.80.45.131:8080/uploads/" + it.data.image)
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(binding.ivProfile)
            var data = it.data.nameSkill.split(",").map {
                SkillModel(it)
            }
            rvSkill = SkillAdapter(data)
            binding.recyclerviewSkillProfile.adapter = rvSkill
            binding.recyclerviewSkillProfile.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        })
    }
}