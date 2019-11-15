package dev.dnights.circularviewpager.lib

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.PagerAdapter

abstract class LayoutPagerAdapter(count: Int) : PagerAdapter() {
    private var mPageList: MutableList<Int> = ArrayList()

    init {
        setPageListCount(count)
    }

    override fun getCount(): Int {
        return if (mPageList.size > 2) mPageList.size else 0
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewPosition = when (position) {
            mPageList.size - 1 -> 0
            0 -> mPageList.size - 3
            else -> mPageList[position] - 1
        }
        val layout = getLayout(viewPosition)

        val inflater = container.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(layout, null)
        setItemView(view, viewPosition)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

    fun setPageListCount(count : Int){
        mPageList.clear()
        mPageList.add(count + 1)
        for (index in 0 until count) {
            mPageList.add(index + 1)
        }
        mPageList.add(0)
    }

    protected abstract fun getLayout(position: Int): Int

    protected abstract fun setItemView(rootView: View, position: Int)
}