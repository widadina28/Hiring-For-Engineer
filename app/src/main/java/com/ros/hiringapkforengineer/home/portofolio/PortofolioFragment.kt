package com.ros.hiringapkforengineer.home.portofolio

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
import com.ros.hiringapkforengineer.databinding.FragmentExperienceBinding
import com.ros.hiringapkforengineer.databinding.FragmentPortofolioBinding
import com.ros.hiringapkforengineer.utils.SharedPrefUtil


class PortofolioFragment : Fragment() {
    private lateinit var binding: FragmentPortofolioBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: PortofolioViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPortofolioBinding.inflate(inflater)
        sharedpref = SharedPrefUtil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(PortofolioApiService::class.java)
        viewModel = ViewModelProvider(this).get(PortofolioViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)

        if (service!=null) {
            viewModel.setServicePortfolio(service)
        }

        rv = binding.recyclerPortfolio
        rv.adapter = PortofolioAdapter(arrayListOf())
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.callApiPort()
        subscribeLiveData()
        return binding.root
    }
    private fun subscribeLiveData(){
        viewModel.isResponsePortfolio.observe(viewLifecycleOwner, Observer {
            (binding.recyclerPortfolio.adapter as PortofolioAdapter).addList(it)
        })
    }
}