package com.ros.hiringapkforengineer.search

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.databinding.FragmentSearchBinding
import com.ros.hiringapkforengineer.home.HomeApiService
import com.ros.hiringapkforengineer.home.detail.DetailEngineerActivity
import com.ros.hiringapkforengineer.home.engineer.EngineerAdapter
import com.ros.hiringapkforengineer.home.engineer.EngineerModel
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: SearchViewModel
    private lateinit var sv: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        sharedpref = SharedPrefUtil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(HomeApiService::class.java)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setServiceEngineer(service)
        }

        sv = binding.searchView
        rv = binding.recyclerSearch
        rv.adapter = EngineerAdapter(arrayListOf(), object : EngineerAdapter.OnAdapterListener {
            override fun onClick(engineer: EngineerModel) {
                sharedpref.putString(Constant.PREF_ID_ENGINEER, engineer.id)
                startActivity(Intent(requireContext(), DetailEngineerActivity::class.java))
            }

        })
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.callApiEngSearch()
        subscribeLiveData()

        val id: Int =
            sv.getContext().getResources().getIdentifier("android:id/search_src_text", null, null)
        val textView = sv.findViewById<TextView>(id)

        val id_icon: Int =
            sv.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null)

        textView.setTextColor(Color.BLACK)

        textView.setHintTextColor(Color.GRAY)

        val searchHintIcon: ImageView = sv.findViewById(id_icon) as ImageView
        searchHintIcon.setImageResource(R.drawable.ic_menu_search)

        return binding.root
    }


    private fun subscribeLiveData() {
        viewModel.isResponseAdapter.observe(viewLifecycleOwner, Observer {
            (binding.recyclerSearch.adapter as EngineerAdapter).addList(it)
            sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    var recentSearch: ArrayList<EngineerModel> = ArrayList()
                    for (data in it) {
                        if (data.nameEngineer.contains(query) || data.nameFreelance.contains(query) || data.skill.toString()
                                .contains(query)
                        ) {
                            recentSearch.add(data)
                        }
                    }
                    (binding.recyclerSearch.adapter as EngineerAdapter).addList(recentSearch)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    var recentSearch: ArrayList<EngineerModel> = ArrayList()
                    for (data in it) {
                        if (data.nameEngineer.contains(newText) || data.nameFreelance.contains(
                                newText
                            ) || data.skill.toString().contains(newText)
                        ) {
                            recentSearch.add(data)
                        }
                    }
                    (binding.recyclerSearch.adapter as EngineerAdapter).addList(recentSearch)
                    return false

                }

            })
        })
    }
}