package com.ros.hiringapkforengineer.profile.engineer

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.confirm.ConfirmViewModel
import com.ros.hiringapkforengineer.databinding.FragmentProfileBinding
import com.ros.hiringapkforengineer.home.detail.VPDetailEngineerAdapter
import com.ros.hiringapkforengineer.home.skill.SkillAdapter
import com.ros.hiringapkforengineer.home.skill.SkillModel
import com.ros.hiringapkforengineer.login.LoginActivity
import com.ros.hiringapkforengineer.profile.edit.EditProfileActivity
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var sharedpref : SharedPrefUtil
    private lateinit var viewModel : ProfileViewModel
    private lateinit var rv : RecyclerView
    private lateinit var vpadapter : VPDetailEngineerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        sharedpref = SharedPrefUtil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(ProfileApiService::class.java)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null){
            viewModel.setServiceProfile(service)
        }
        viewModel.getApiEngineerProfile()
        subscribeLiveData()
        setUpLogout()
        vpadapter = VPDetailEngineerAdapter((activity as AppCompatActivity).supportFragmentManager)
        binding.viewProfile.adapter=vpadapter
        binding.tabProfile.setupWithViewPager(binding.viewProfile)
        setUpListener()
        return binding.root
    }
    private fun setUpLogout(){
        binding.tvLogout.setOnClickListener {
           dialog()
        }
    }

    private fun setUpListener(){
        binding.ivEdit.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
    }

    private fun dialog(){
        val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Are You Sure?")
                .setPositiveButton("Logout"){ dialog: DialogInterface?, which: Int ->
                    sharedpref.clear()
                    val intent=Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Cancel")  {dialogInterface, i -> dialogInterface.dismiss()
                }
        dialog.show()
    }

    private fun subscribeLiveData(){
        viewModel.isResponseGetProfile.observe(viewLifecycleOwner, Observer {
            binding.nameProfile.text=it.data.nameEngineer
            binding.jobProfile.text=it.data.nameFreelance
            binding.locationProfile.text=it.data.nameLoc
            binding.statusProfile.text=it.data.status
            binding.descriptionProfile.text=it.data.descriptionEngineer
            Picasso.get().load("http://3.80.45.131:8080/uploads/"+ it.data.image).placeholder(R.drawable.ic_baseline_person_24)
                    .into(binding.ivProfile)
            binding.emailProfile.text=it.data.emailAccount

            var data = it.data.nameSkill.split(",").map {
                SkillModel(it)
            }
            rv = binding.recyclerviewSkillProfile
            rv.adapter = SkillAdapter(data)
            rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
//

        })
    }

}