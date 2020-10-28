package com.ros.hiringapkforengineer.home

import android.content.Intent
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
import com.ros.hiringapkforengineer.databinding.FragmentHomeBinding
import com.ros.hiringapkforengineer.home.detail.DetailEngineerActivity
import com.ros.hiringapkforengineer.home.engineer.EngineerAdapter
import com.ros.hiringapkforengineer.home.engineer.EngineerModel
import com.ros.hiringapkforengineer.profile.engineer.ProfileApiService
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        sharedpref = SharedPrefUtil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(HomeApiService::class.java)
        val service2 = ApiClient.getApiClient(requireContext())?.create(ProfileApiService::class.java)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setServiceEngineer(service)
        }

        if (service2 != null) {
            viewModel.setServiceEngineerAccount(service2)
        }

        binding.nameAtHome.setText(sharedpref.getString(Constant.PREF_NAME))

        rv = binding.recyclerviewEngineer
        rv.adapter = EngineerAdapter(arrayListOf(), object : EngineerAdapter.OnAdapterListener {
            override fun onClick(engineer: EngineerModel) {
                val a = sharedpref.putString(Constant.PREF_ID_ENGINEER, engineer.id)
                startActivity(Intent(requireContext(), DetailEngineerActivity::class.java))
            }

        })
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)




        viewModel.callApi()
        subscribeLiveData()
        return binding.root
    }

    private fun subscribeLiveData() {
        viewModel.isResponseAdapter.observe(viewLifecycleOwner, Observer {
            (binding.recyclerviewEngineer.adapter as EngineerAdapter).addList(it)
        })
    }

}