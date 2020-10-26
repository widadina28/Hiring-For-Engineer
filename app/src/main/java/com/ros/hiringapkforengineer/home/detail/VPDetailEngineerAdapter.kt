package com.ros.hiringapkforengineer.home.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ros.hiringapkforengineer.home.experience.ExperienceFragment
import com.ros.hiringapkforengineer.home.portofolio.PortofolioFragment

class VPDetailEngineerAdapter(fragment: FragmentManager):
FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    val fragment = arrayOf(PortofolioFragment(), ExperienceFragment())
    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getCount(): Int =fragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Portfolio"
            1 -> "Experience"
            else -> ""
        }
    }
}