package com.nabawi.gohub.ui.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nabawi.gohub.ui.follower.FollowerFragment
import com.nabawi.gohub.utils.Constans.TAB_TITLES

class FollowPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.getInstance(username)
            1 -> fragment = FollowingFragment.getInstance(username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }
}