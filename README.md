[![](https://jitpack.io/v/DNights/CircularViewPager.svg)](https://jitpack.io/#DNights/CircularViewPager)


# CircularViewPager
Android CircularViewPager

[Origin code]

https://github.com/tompee26/CircularViewPager

## Setup
Add the JitPack repository in your build.gradle (top level module):
```gradle
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

And add next dependencies in the build.gradle of the module:
```gradle
dependencies {
    implementation 'com.github.DNights:CircularViewPager:1.0.3'
}
```

set activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".activity.MainActivity">

   <dev.dnights.circularviewpager.lib.CircularViewPager
       android:id="@+id/mainViewPager"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       app:pageCount="0"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

set MainActivity.kt
```
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
    }
```


LICENSE
```
IntelliJ IDEA   
MIT License

Copyright (c) 2019 hyun dong han

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
