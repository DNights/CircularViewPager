package dev.dnights.circularviewpager.activity

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dnights.restfullapisampletest.api.API
import com.dnights.restfullapisampletest.api.AccessKey
import com.dnights.restfullapisampletest.api.data.PhotoData
import dev.dnights.circularviewpager.R
import dev.dnights.circularviewpager.lib.CircularViewPager2Adapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_viewpager2.*
import kotlinx.android.synthetic.main.item_imageview.view.*

class ViewPager2Activity : BaseActivity(){

    private var listPhoto : List<PhotoData> = listOf()

    val circularViewPager2Adapter = object : CircularViewPager2Adapter(10){
        override fun getLayout(): Int {
            return R.layout.item_imageview
        }

        override fun setItemView(rootView: View, position: Int) {
            if (listPhoto.isNotEmpty()) {
                rootView.textView_id.text = listPhoto[position].id

                Glide
                    .with(rootView)
                    .load(listPhoto[position].urls.thumb)
                    .into(rootView.imageView_thumb)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager2)

        callApiWithPhoto()
        initViewPager2()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initViewPager2(){
        viewpager2.adapter = circularViewPager2Adapter
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            private var curPosition = 0

            override fun onPageSelected(position: Int) {
                curPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    val pageCount = listPhoto.size + 2
                    if (curPosition == pageCount - 1) {
                        viewpager2.setCurrentItem(1, false)
                    } else if (curPosition == 0) {
                        viewpager2.setCurrentItem(pageCount - 2, false)
                    }
                }
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
                    circularViewPager2Adapter.setPageListCount(listPhoto.size)
                    circularViewPager2Adapter.notifyDataSetChanged()
                }, {

                }
            ).apply { addDisposable(this) }
    }



}