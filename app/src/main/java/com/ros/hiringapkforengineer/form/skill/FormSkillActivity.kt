package com.ros.hiringapkforengineer.form.skill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.bottomnav.BottomNavActivity
import com.ros.hiringapkforengineer.databinding.ActivityFormSkillBinding
import com.ros.hiringapkforengineer.form.PostProfileApiService
import com.ros.hiringapkforengineer.utils.SharedPrefUtil

class FormSkillActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormSkillBinding
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: FormSkillViewModel
    private var selectedSkill1 = ""
    private var selectedSkill2 = ""
    private var selectedSkill3 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_skill)
        sharedpref = SharedPrefUtil(applicationContext)
        val service = ApiClient.getApiClient(this)?.create(PostProfileApiService::class.java)
        viewModel = ViewModelProvider(this).get(FormSkillViewModel::class.java)
        viewModel.setSharedPref(sharedpref)
        if (service != null) {
            viewModel.setService(service)
        }
        setUpListener()
        viewModel.spinnerSkill()
        subscribeLiveData()
    }

    fun setUpListener() {
        binding.btnSubmit.setOnClickListener {
            viewModel.postSkill1("$selectedSkill1")
            viewModel.postSkill2("$selectedSkill2")
            viewModel.postSkill3("$selectedSkill3")
            Toast.makeText(this, "Profile Data Sent!", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, BottomNavActivity::class.java))
            finish()
        }
    }

    fun subscribeLiveData() {
        viewModel.isListSkillResponse.observe(this, Observer {
            val spinnerSkill1 = binding.spinnerSkill1 as Spinner
            val spinnerSkill2 = binding.spinnerSkill2 as Spinner
            val spinnerSkill3 = binding.spinnerSkill3 as Spinner
            spinnerSkill1.adapter =
                ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, it.map {
                    it.nameSkill
                })
            spinnerSkill2.adapter =
                ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, it.map {
                    it.nameSkill
                })
            spinnerSkill3.adapter =
                ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, it.map {
                    it.nameSkill
                })
            spinnerSkill1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    selectedSkill1 = it[position].idSkill.toString()
                }

            }
            spinnerSkill2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    selectedSkill2 = it[position].idSkill.toString()
                }

            }
            spinnerSkill3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    selectedSkill3 = it[position].idSkill.toString()
                }

            }
        })
    }
}