package com.ros.hiringapkforengineer.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ros.hiringapkforengineer.profile.experience.get.ExperienceProfileFragment
import com.ros.hiringapkforengineer.profile.portofolio.get.PortfolioProfileFragment

class VPProfileAdapter(fragment: FragmentManager) :
    FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val fragment = arrayOf(PortfolioProfileFragment(), ExperienceProfileFragment())

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getCount(): Int = fragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Portfolio"
            1 -> "Experience"
            else -> ""
        }
    }
}