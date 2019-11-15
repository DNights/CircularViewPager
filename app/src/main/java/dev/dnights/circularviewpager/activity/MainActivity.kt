package dev.dnights.circularviewpager.activity

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.dnights.restfullapisampletest.api.API
import com.dnights.restfullapisampletest.api.AccessKey
import com.dnights.restfullapisampletest.api.data.PhotoData
import dev.dnights.circularviewpager.R
import dev.dnights.circularviewpager.lib.CircularViewPager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_imageview.view.*

class MainActivity : BaseActivity() {

    private var listPhoto : List<PhotoData> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callApiWithPhoto()
        initMainViewPager()
    }

    private fun initMainViewPager() {
        mainViewPager.setLayoutPagerAdapter(object : CircularViewPager.GetLayoutItemListener {

            override fun setItemView(rootView: View, position: Int) {
                if (listPhoto.isNotEmpty()) {
                    rootView.textView_id.text = listPhoto[position].id

                    Glide
                        .with(rootView)
                        .load(listPhoto[position].urls.thumb)
                        .into(rootView.imageView_thumb)
                }
            }

            override fun getLayout(position: Int): Int {
                return R.layout.item_imageview
            }
        })

        mainViewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{

            private var pagerPosition = -1

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    val pageCount = mainViewPager.adapter!!.count
                    if (pageCount == 1) {
                        return
                    }
                    if (pagerPosition == 0) {
                        mainViewPager.setCurrentItem(pageCount - 2, false)
                    } else if (pagerPosition == pageCount - 1) {
                        mainViewPager.setCurrentItem(1, false)
                    }
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                pagerPosition = position
            }
        })
    }

    private fun callApiWithPhoto() {
        API.create().fetchPhotos(AccessKey.accessKey, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    listPhoto = it.body()!!
                    mainViewPager.adapter!!.notifyDataSetChanged()
                    mainViewPager.pageCount = listPhoto.size
                }, {

                }
            ).apply { addDisposable(this) }
    }
}
