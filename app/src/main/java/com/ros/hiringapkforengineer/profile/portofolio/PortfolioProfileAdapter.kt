package com.ros.hiringapkforengineer.profile.portofolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.home.portofolio.PortofolioModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_portfolio_profile.view.*
import kotlinx.android.synthetic.main.item_portofolio.view.iv_img_portfolio

class PortfolioProfileAdapter(
    val dataportofolio: ArrayList<PortofolioModel>,
    val listener: OnAdapterListener
) : RecyclerView.Adapter<PortfolioProfileAdapter.HolderPorto>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PortfolioProfileAdapter.HolderPorto {
        return HolderPorto(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_portfolio_profile, parent, false)
        )
    }

    override fun getItemCount(): Int = dataportofolio.size

    class HolderPorto(val view: View) : RecyclerView.ViewHolder(view)

    fun addList(newList: List<PortofolioModel>) {
        dataportofolio.clear()
        dataportofolio.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HolderPorto, position: Int) {
        val portofolio = dataportofolio[position]
        Picasso.get().load("http://3.80.45.131:8080/uploads/" + portofolio.image)
            .placeholder(R.drawable.ic_picture).into(holder.view.iv_img_portfolio)
        holder.view.delete.setOnClickListener {
            listener.onDelete(portofolio)
        }
    }

    interface OnAdapterListener {
        fun onDelete(portfolio: PortofolioModel)
    }
}