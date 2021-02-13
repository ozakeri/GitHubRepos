package gap.com.githubrepos.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import gap.com.githubrepos.fragment.FollowersFragment
import gap.com.githubrepos.fragment.FollowingFragment
import gap.com.githubrepos.fragment.ReposFragment

class ViewPagerProfileAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> return ReposFragment()
            1 -> return FollowersFragment()
            2 -> return FollowingFragment()
            else -> FollowersFragment()
        }
    }


    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Repository"
            1 -> return "Followers"
            2 -> return "Following"
        }
        return super.getPageTitle(position)
    }
}