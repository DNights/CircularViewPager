package dev.dnights.circularviewpager.lib

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import dev.dnights.circularviewpager.R


class CircularViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {
    private val mListener: CircularViewPagerListener
    private var mPageCount: Int = 0

    init {
        getAttributes(attrs)
        mListener = CircularViewPagerListener()
        overScrollMode = View.OVER_SCROLL_NEVER
        offscreenPageLimit = 1
    }

    private fun getAttributes(attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.CircularViewPager, 0, 0
        )
        mPageCount = a.getInteger(R.styleable.CircularViewPager_pageCount, 0)
        a.recycle()
    }

    fun setFragmentPagerAdapter(manager: FragmentManager, listener: GetFragmentItemListener) {
        val adapter = object : CircularFragmentPagerAdapter(manager, mPageCount) {
            override fun getFragment(position: Int): Fragment {
                return listener.getFragment(position)
            }
        }
        setAdapter(adapter)
    }

    fun setLayoutPagerAdapter(listener: GetLayoutItemListener) {
        val adapter = object : LayoutPagerAdapter(mPageCount) {
            override fun setItemView(rootView: View, position: Int) {
                listener.setItemView(rootView, position)
            }

            override fun getLayout(position: Int): Int {
                return listener.getLayout(position)
            }
        }
        setAdapter(adapter)
    }

    fun setPageCount(count: Int) {
        mPageCount = count
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        addOnPageChangeListener(mListener)
        currentItem = 1
    }

    interface GetFragmentItemListener {
        fun getFragment(position: Int): Fragment
    }

    interface GetLayoutItemListener {
        fun getLayout(position: Int): Int
        fun setItemView(rootView: View, position: Int)
    }

    private inner class CircularViewPagerListener : OnPageChangeListener {

        private val DELAY = 200

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            postDelayed({ loopCurrentItem(position) }, DELAY.toLong())
        }

        override fun onPageScrollStateChanged(state: Int) {}

        private fun loopCurrentItem(position: Int) {
            val pageCount = adapter!!.count
            if (position == pageCount - 1) {
                setCurrentItem(1, false)
            } else if (position == 0) {
                setCurrentItem(pageCount - 2, false)
            }
        }

    }
}