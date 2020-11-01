package com.ros.hiringapkforengineer.profile.experience.get

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
import com.ros.hiringapkforengineer.databinding.FragmentExperienceProfileBinding
import com.ros.hiringapkforengineer.home.experience.ExperienceAdapter
import com.ros.hiringapkforengineer.home.experience.ExperienceApiService
import com.ros.hiringapkforengineer.home.experience.ExperienceModel
import com.ros.hiringapkforengineer.profile.experience.ExperienceProfileAdapter
import com.ros.hiringapkforengineer.profile.experience.post.AddExperienceFragment
import com.ros.hiringapkforengineer.utils.SharedPrefUtil


class ExperienceProfileFragment : Fragment() {
    private lateinit var binding: FragmentExperienceProfileBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: ExperienceProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExperienceProfileBinding.inflate(inflater)
        sharedpref = SharedPrefUtil(requireContext())
        val service =
            ApiClient.getApiClient(requireContext())?.create(ExperienceApiService::class.java)
        viewModel = ViewModelProvider(this).get(ExperienceProfileViewModel::class.java)
        viewModel.setSharedPreferences(sharedpref)

        if (service != null) {
            viewModel.setServiceExperience(service)
        }
        rv = binding.recyclerExperience
        rv.adapter = ExperienceProfileAdapter(
            arrayListOf(),
            object : ExperienceProfileAdapter.OnAdapterListener {
                override fun onDelete(experience: ExperienceModel) {
                    deleteExperience(experience)
                }

            })
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.callApiExpProfile()
        subscribeLiveData()
        setUpListener()
        return binding.root
    }


    fun deleteExperience(experience: ExperienceModel) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.apply {
            setTitle("Delete Confirm")
            setMessage("Are You Sure to Delete This Experience?")
            setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
                dialog?.dismiss()
            }
            setPositiveButton("Delete") { dialog: DialogInterface?, which: Int ->
                viewModel.delete(experience.id)
                dialog?.dismiss()
            }
        }
        dialog.show()
    }

    fun setUpListener() {
        binding.btnAddExperience.setOnClickListener {
            Toast.makeText(requireContext(), "Add Experience", Toast.LENGTH_LONG).show()
            startActivity(Intent(requireContext(), AddExperienceFragment::class.java))
        }
    }

    private fun subscribeLiveData() {
        viewModel.isResponseExperience.observe(viewLifecycleOwner, Observer {
            (binding.recyclerExperience.adapter as ExperienceProfileAdapter).addList(it)
        })
        viewModel.isResponseDelete.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Delete Success", Toast.LENGTH_SHORT).show()
            viewModel.callApiExpProfile()
        })
    }

}