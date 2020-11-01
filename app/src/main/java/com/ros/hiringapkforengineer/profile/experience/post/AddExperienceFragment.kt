package com.ros.hiringapkforengineer.profile.experience.post

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.bottomnav.BottomNavActivity
import com.ros.hiringapkforengineer.databinding.FragmentAddExperienceBinding
import com.ros.hiringapkforengineer.home.experience.ExperienceApiService
import com.ros.hiringapkforengineer.profile.engineer.ProfileFragment
import com.ros.hiringapkforengineer.utils.SharedPrefUtil

class AddExperienceFragment : AppCompatActivity() {
    private lateinit var binding: FragmentAddExperienceBinding
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: AddExperienceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_add_experience)
        sharedpref = SharedPrefUtil(this)
        val service = ApiClient.getApiClient(this)?.create(ExperienceApiService::class.java)
        viewModel = ViewModelProvider(this).get(AddExperienceViewModel::class.java)
        viewModel.setSharedPreferences(sharedpref)
        if (service != null) {
            viewModel.setServiceExperience(service)
        }
        subscribeLiveData()
        setUpListener()
    }

    fun setUpListener() {
        binding.btnAddExp.setOnClickListener {
            viewModel.postExperience(
                binding.etPosition.text.toString(),
                binding.etNameCompanyExp.text.toString(), binding.etStart.text.toString(),
                binding.etEnd.text.toString(), binding.etDescription.text.toString()
            )
        }
    }

    fun subscribeLiveData() {
        viewModel.isResponsePostExperience.observe(this, Observer {
            Toast.makeText(this, "Add Experience Success!", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, BottomNavActivity::class.java))

        })
    }

}