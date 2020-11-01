package com.ros.hiringapkforengineer.profile.experience

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.home.experience.ExperienceModel
import kotlinx.android.synthetic.main.item_experience_profile.view.*

class ExperienceProfileAdapter(
    val dataExperience: ArrayList<ExperienceModel>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<ExperienceProfileAdapter.HolderExp>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExperienceProfileAdapter.HolderExp {
        return HolderExp(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_experience_profile, parent, false)
        )
    }

    override fun getItemCount(): Int = dataExperience.size

    class HolderExp(val view: View) : RecyclerView.ViewHolder(view)

    fun addList(newList: List<ExperienceModel>) {
        dataExperience.clear()
        dataExperience.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HolderExp, position: Int) {
        val experience = dataExperience[position]
        holder.view.position_experience.text = experience.position
        holder.view.name_company_exp_profile.text = experience.companyName
        holder.view.description_experience_profile.text = experience.description
        holder.view.delete.setOnClickListener {
            listener.onDelete(experience)
        }
    }

    interface OnAdapterListener {
        fun onDelete(experience: ExperienceModel)
    }
}