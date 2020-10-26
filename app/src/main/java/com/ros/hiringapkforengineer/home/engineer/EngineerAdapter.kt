package com.ros.hiringapkforengineer.home.engineer

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.home.skill.SkillAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_engineer.view.*

class EngineerAdapter (var dataEngineer:ArrayList<EngineerModel>, var listener:OnAdapterListener) : RecyclerView.Adapter<EngineerAdapter.HolderEng>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderEng {
        return HolderEng(
            LayoutInflater.from(parent.context).inflate(R.layout.item_engineer, parent, false)
        )
    }

    override fun getItemCount() = dataEngineer.size



    override fun onBindViewHolder(holder: HolderEng, position: Int) {
        val engineer = dataEngineer[position]
        Picasso.get().load("http://3.80.45.131:8080/uploads/" + engineer.image).placeholder(R.drawable.ic_baseline_person_24).into(holder.view.img_engineer)
        holder.view.tv_name_engineer.text=engineer.nameEngineer
        holder.view.tv_job.text=engineer.nameFreelance
        holder.view.img_engineer.setOnClickListener {
            listener.onClick(engineer)
        }
        val childLayoutManager = LinearLayoutManager(
            holder.view.recycler_skill.context, RecyclerView.HORIZONTAL, false)

//        childLayoutManager.initialPrefetchItemCount = 4

        holder.view.recycler_skill.apply {
            adapter = SkillAdapter(engineer.skill)
            layoutManager = childLayoutManager
            Log.d("engineer", "$engineer")
            setRecycledViewPool(viewPool)
        }
    }

    class HolderEng(val view: View): RecyclerView.ViewHolder(view)

    fun addList(newList: List<EngineerModel>) {
        dataEngineer.clear()
        dataEngineer.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(engineer: EngineerModel)
    }


}
