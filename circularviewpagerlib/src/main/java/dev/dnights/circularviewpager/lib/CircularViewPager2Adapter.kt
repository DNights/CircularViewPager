package dev.dnights.circularviewpager.lib

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class CircularViewPager2Adapter(count: Int) : RecyclerView.Adapter<CircularViewPager2ViewHolder>() {
    private val mPageList: MutableList<Int> = ArrayList()

    init {
        setPageListCount(count = count)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CircularViewPager2ViewHolder {
        return CircularViewPager2ViewHolder(LayoutInflater.from(parent.context).inflate(getLayout(), parent, false))
    }

    override fun getItemCount(): Int = if (mPageList.size > 2) mPageList.size else 0

    override fun onBindViewHolder(holder: CircularViewPager2ViewHolder, position: Int) {
        val viewPosition = when (position) {
            mPageList.size - 1 -> 0
            0 -> mPageList.size - 3
            else -> mPageList[position] - 1
        }

        setItemView(holder.itemView, viewPosition)
    }

    open fun setPageListCount(count : Int){
        mPageList.clear()
        mPageList.add(count + 1)
        for (index in 0 until count) {
            mPageList.add(index + 1)
        }
        mPageList.add(0)
    }

    protected abstract fun getLayout(): Int

    protected abstract fun setItemView(rootView: View, position: Int)

}

open class CircularViewPager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)