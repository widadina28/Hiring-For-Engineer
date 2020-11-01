package com.ros.hiringapkforengineer.confirm

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.confirm.choose_status.ChooseStatusActivity
import com.ros.hiringapkforengineer.databinding.FragmentConfirmBinding
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil

class ConfirmFragment : Fragment() {
    private lateinit var binding: FragmentConfirmBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: ConfirmViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmBinding.inflate(inflater)
        sharedpref = SharedPrefUtil(requireContext())
        val service =
            ApiClient.getApiClient(requireContext())?.create(ConfirmApiService::class.java)
        viewModel = ViewModelProvider(this).get(ConfirmViewModel::class.java)
        viewModel.setSharedPreferences(sharedpref)
        if (service != null) {
            viewModel.setServiceConfirm(service)
        }
        rv = binding.recyclerConfirm
        rv.adapter = ConfirmAdapter(arrayListOf(), object : ConfirmAdapter.onAdapterListener {
            override fun onClick(confirm: ConfirmModel) {
                sharedpref.putString(Constant.PREF_ID_HIRE, confirm.id)
                sharedpref.putString(Constant.PREF_STATUS, confirm.status)
                startActivity(Intent(requireContext(), ChooseStatusActivity::class.java))
            }

        })
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewModel.callApiConfirm()
        subscribeLiveData()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        viewModel.callApiConfirm()
    }

    private fun subscribeLiveData() {
        viewModel.isConfirmResponse.observe(viewLifecycleOwner, Observer {
            (binding.recyclerConfirm.adapter as ConfirmAdapter).addList(it)
        })

    }

}