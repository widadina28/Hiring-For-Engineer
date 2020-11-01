package com.ros.hiringapkforengineer.profile.portofolio.get

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.databinding.FragmentPortfolioProfileBinding
import com.ros.hiringapkforengineer.home.portofolio.PortofolioAdapter
import com.ros.hiringapkforengineer.home.portofolio.PortofolioApiService
import com.ros.hiringapkforengineer.home.portofolio.PortofolioModel
import com.ros.hiringapkforengineer.profile.portofolio.PortfolioProfileAdapter
import com.ros.hiringapkforengineer.profile.portofolio.post.AddPortfolioFragment
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil


class PortfolioProfileFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioProfileBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: PortfolioProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioProfileBinding.inflate(inflater)
        sharedpref = SharedPrefUtil(requireContext())
        val service =
            ApiClient.getApiClient(requireContext())?.create(PortofolioApiService::class.java)
        viewModel = ViewModelProvider(this).get(PortfolioProfileViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setServicePortfolio(service)
        }

        rv = binding.recyclerPortfolio
        rv.adapter = PortfolioProfileAdapter(
            arrayListOf(),
            object : PortfolioProfileAdapter.OnAdapterListener {
                override fun onDelete(portfolio: PortofolioModel) {
                    deletePortfolio(portfolio)
                }

            })
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.callApiPort()
        subscribeLiveData()
        setUpListener()
        return binding.root
    }

    fun deletePortfolio(portfolio: PortofolioModel) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.apply {
            setTitle("Delete Confirm")
            setMessage("Are You Sure to Delete This Portfolio?")
            setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
                dialog?.dismiss()
            }
            setPositiveButton("Delete") { dialog: DialogInterface?, which: Int ->
                viewModel.delete(portfolio.id)
                dialog?.dismiss()
            }
        }
        dialog.show()
    }

    fun setUpListener() {
        binding.btnAddPortfolio.setOnClickListener {
            startActivity(Intent(requireContext(), AddPortfolioFragment::class.java))
        }
    }

    private fun subscribeLiveData() {
        viewModel.isResponsePortfolio.observe(viewLifecycleOwner, Observer {
            (binding.recyclerPortfolio.adapter as PortfolioProfileAdapter).addList(it)
        })
        viewModel.isResponseDelete.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Delete Success", Toast.LENGTH_SHORT).show()
            viewModel.callApiPort()
        })
    }
}