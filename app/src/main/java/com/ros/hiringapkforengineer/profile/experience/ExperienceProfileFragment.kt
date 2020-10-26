package com.ros.hiringapkforengineer.profile.experience

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.databinding.FragmentExperienceProfileBinding
import com.ros.hiringapkforengineer.databinding.FragmentProfileBinding
import com.ros.hiringapkforengineer.home.experience.ExperienceAdapter
import com.ros.hiringapkforengineer.home.experience.ExperienceApiService
import com.ros.hiringapkforengineer.home.experience.ExperienceViewModel
import com.ros.hiringapkforengineer.utils.SharedPrefUtil


class ExperienceProfileFragment : Fragment() {
    private lateinit var binding: FragmentExperienceProfileBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: ExperienceProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExperienceProfileBinding.inflate(inflater)
        sharedpref = SharedPrefUtil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(ExperienceApiService::class.java)
        viewModel = ViewModelProvider(this).get(ExperienceProfileViewModel::class.java)
        viewModel.setSharedPreferences(sharedpref)

        if (service != null){
            viewModel.setServiceExperience(service)
        }
        rv = binding.recyclerExperience
        rv.adapter = ExperienceAdapter(arrayListOf())
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.callApiExpProfile()
        subscribeLiveData()
        return binding.root
    }

    private fun subscribeLiveData(){
        viewModel.isResponseExperience.observe(viewLifecycleOwner, Observer {
            (binding.recyclerExperience.adapter as ExperienceAdapter).addList(it)
        })
    }

}