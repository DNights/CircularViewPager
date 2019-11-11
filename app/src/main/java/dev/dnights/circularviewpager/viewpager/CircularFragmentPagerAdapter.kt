package dev.dnights.circularviewpager.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


internal abstract class CircularFragmentPagerAdapter(fragmentManager: FragmentManager, count: Int) :
    FragmentStatePagerAdapter(fragmentManager) {
    private val mPageList: MutableList<Int>

    init {
        mPageList = ArrayList()
        mPageList.add(count + 1)
        for (index in 0 until count) {
            mPageList.add(index + 1)
        }
        mPageList.add(0)
    }

    override fun getCount(): Int {
        return if (mPageList.size > 2) mPageList.size else 0
    }

    override fun getItem(position: Int): Fragment {
        if (position == mPageList.size - 1) {
            return getFragment(0)
        } else if (position == 0) {
            return getFragment(mPageList.size - 3)
        }
        return getFragment(mPageList[position] - 1)
    }

    protected abstract fun getFragment(position: Int): Fragment
}