package app.siakad.siakadtkadmin.presentation.utils.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val fragmentList: ArrayList<Fragment> = arrayListOf()
    private val fragmentTitleList: ArrayList<String> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList.get(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(title: String, fragment: Fragment) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }
}