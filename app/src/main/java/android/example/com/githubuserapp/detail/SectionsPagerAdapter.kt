package android.example.com.githubuserapp.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        var tabTitle : String
        tabTitle = when(position) {
            0 -> FollowFragment.FOLLOWER
            1 -> FollowFragment.FOLLOWING
            else -> ""
        }
        fragment.arguments = Bundle().apply {
            putString(FollowFragment.TAB_TITLE, tabTitle)
        }
        return fragment
    }

}