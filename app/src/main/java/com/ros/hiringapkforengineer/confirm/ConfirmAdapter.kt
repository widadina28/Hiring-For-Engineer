package com.ros.hiringapkforengineer.confirm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_confirm.view.*
import kotlinx.android.synthetic.main.item_experience.view.*

class ConfirmAdapter (var dataConfirm: ArrayList<ConfirmModel>, var listener: onAdapterListener): RecyclerView.Adapter<ConfirmAdapter.HolderConfirm>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmAdapter.HolderConfirm {
        Log.d("Confirm", "$dataConfirm")
        return HolderConfirm(LayoutInflater.from(parent.context).inflate(R.layout.item_confirm, parent, false
        ))
    }

    override fun getItemCount(): Int = dataConfirm.size

    override fun onBindViewHolder(holder: HolderConfirm, position: Int) {
        val confirm = dataConfirm[position]
        holder.view.description_confirm.text=confirm.description
        holder.view.name_project.text=confirm.nameProject
        holder.view.tv_name_company.text=confirm.nameCompany
        holder.view.status_hire.text=confirm.status
        Picasso.get().load("http://3.80.45.131:8080/uploads/" + confirm.image)
                .placeholder(R.drawable.ic_picture).into(holder.view.iv_img_confirm)
        holder.view.iv_img_confirm.setOnClickListener {
            listener.onClick(confirm)
        }
    }

    class HolderConfirm(val view: View) : RecyclerView.ViewHolder(view)

    fun addList(newList: List<ConfirmModel>){
        dataConfirm.clear()
        dataConfirm.addAll(newList)
        notifyDataSetChanged()
    }

    interface onAdapterListener {
        fun onClick(confirm: ConfirmModel)
    }

}