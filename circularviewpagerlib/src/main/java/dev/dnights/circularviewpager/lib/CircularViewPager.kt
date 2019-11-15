package dev.dnights.circularviewpager.lib

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter

open class CircularViewPager
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    private val listener: CircularViewPagerListener by lazy {
        CircularViewPagerListener()
    }

    var pageCount: Int = 0
        set(value) {
            field = value
            if(adapter != null && adapter is LayoutPagerAdapter){
                (adapter as LayoutPagerAdapter).setPageListCount(value)
            }
        }
        get() = field

    init {
        getAttributes(attrs)
        overScrollMode = View.OVER_SCROLL_NEVER
        offscreenPageLimit = 1
    }

    private fun getAttributes(attrs: AttributeSet?) {
        val typesArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircularViewPager,
            0,
            0)
        pageCount = typesArray.getInteger(R.styleable.CircularViewPager_pageCount, 0)
        typesArray.recycle()
    }

    fun setFragmentPagerAdapter(manager: FragmentManager, listener: GetFragmentItemListener) {
        val adapter = object : CircularFragmentPagerAdapter(manager, pageCount) {
            override fun getFragment(position: Int): Fragment {
                return listener.getFragment(position)
            }
        }
        setAdapter(adapter)
    }

    fun setLayoutPagerAdapter(listener: GetLayoutItemListener) {
        val adapter = object : LayoutPagerAdapter(pageCount) {
            override fun setItemView(rootView: View, position: Int) {
                listener.setItemView(rootView, position)
            }

            override fun getLayout(position: Int): Int {
                return listener.getLayout(position)
            }
        }
        setAdapter(adapter)
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        addOnPageChangeListener(listener)
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

        private var curPosition = 0

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            curPosition = position
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (state == SCROLL_STATE_IDLE) {
                val pageCount = adapter!!.count
                if (curPosition == pageCount - 1) {
                    setCurrentItem(1, false)
                } else if (curPosition == 0) {
                    setCurrentItem(pageCount - 2, false)
                }
            }
        }

    }
}
