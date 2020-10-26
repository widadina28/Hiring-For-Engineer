package com.ros.hiringapkforengineer.home.portofolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_portofolio.view.*

class PortofolioAdapter (val dataportofolio: ArrayList<PortofolioModel>): RecyclerView.Adapter<PortofolioAdapter.HolderPorto>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortofolioAdapter.HolderPorto {
        return HolderPorto(LayoutInflater.from(parent.context).inflate(R.layout.item_portofolio, parent, false))
    }

    override fun getItemCount(): Int = dataportofolio.size

    override fun onBindViewHolder(holder: PortofolioAdapter.HolderPorto, position: Int) {
        val portofolio = dataportofolio[position]
        Picasso.get().load("http://3.80.45.131:8080/uploads/" + portofolio.image).placeholder(R.drawable.ic_picture).into(holder.view.iv_img_portfolio)
    }

    class HolderPorto(val view: View): RecyclerView.ViewHolder(view)

    fun addList (newList: List<PortofolioModel>) {
        dataportofolio.clear()
        dataportofolio.addAll(newList)
        notifyDataSetChanged()
    }
}