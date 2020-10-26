package com.ros.hiringapkforengineer.home.experience

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.R
import kotlinx.android.synthetic.main.item_experience.view.*

class ExperienceAdapter(val dataExperience: ArrayList<ExperienceModel>):RecyclerView.Adapter<ExperienceAdapter.HolderExp>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceAdapter.HolderExp {
        return HolderExp(LayoutInflater.from(parent.context).inflate(R.layout.item_experience, parent, false))
    }

    override fun getItemCount(): Int = dataExperience.size

    override fun onBindViewHolder(holder: ExperienceAdapter.HolderExp, position: Int) {
        val experience = dataExperience[position]
        holder.view.position_experience.text=experience.position
        holder.view.name_company.text=experience.companyName
        holder.view.description_experience.text=experience.description
    }

    class HolderExp(val view: View): RecyclerView.ViewHolder(view)

    fun addList(newList: List<ExperienceModel>){
        dataExperience.clear()
        dataExperience.addAll(newList)
        notifyDataSetChanged()
    }
}