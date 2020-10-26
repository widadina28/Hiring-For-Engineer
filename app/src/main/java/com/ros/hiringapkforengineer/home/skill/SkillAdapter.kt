package com.ros.hiringapkforengineer.home.skill

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.home.engineer.EngineerResponse
import kotlinx.android.synthetic.main.item_skill.view.*

class SkillAdapter(var skill: List<SkillModel>) : RecyclerView.Adapter<SkillAdapter.HolderSkill>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderSkill {
        Log.d("skill", "$skill")

        return HolderSkill(
            LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        )
    }

    override fun getItemCount(): Int = skill.size
    override fun onBindViewHolder(holder: HolderSkill, position: Int) {
        holder.skill.text = skill[position].skill
    }

    class HolderSkill(val view: View) : RecyclerView.ViewHolder(view) {
        val skill = view.findViewById<TextView>(R.id.tv_skill)
    }

}