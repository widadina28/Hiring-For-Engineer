package com.ros.hiringapkforengineer.home.experience

import android.os.Bundle
import android.os.RecoverySystem
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
import com.ros.hiringapkforengineer.databinding.FragmentExperienceBinding
import com.ros.hiringapkforengineer.utils.SharedPrefUtil


class ExperienceFragment : Fragment() {
    private lateinit var binding: FragmentExperienceBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: ExperienceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExperienceBinding.inflate(inflater)
        sharedpref = SharedPrefUtil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(ExperienceApiService::class.java)
        viewModel = ViewModelProvider(this).get(ExperienceViewModel::class.java)
        viewModel.setSharedPreferences(sharedpref)

        if (service != null) {
            viewModel.setServiceExperience(service)
        }

        rv = binding.recyclerExperience
        rv.adapter = ExperienceAdapter(arrayListOf())
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.callApi()
        subscribeLiveData()
        return binding.root
    }

    private fun subscribeLiveData() {
        viewModel.isResponseExperience.observe(viewLifecycleOwner, Observer {
            (binding.recyclerExperience.adapter as ExperienceAdapter).addList(it)
        })
    }

}