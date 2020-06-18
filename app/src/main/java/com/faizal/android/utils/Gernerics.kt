package com.faizal.android.utils

import android.os.SystemClock
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.faizal.android.database.R

fun imageLoad(view: ImageView?, imageUrl: String?) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(view!!.context).load(imageUrl).placeholder(R.drawable.placeholder)
            .apply(requestOptions).into(view)
}

var mLastClickTime: Long = 0

fun isMultipleCall(): Boolean {
    if (SystemClock.elapsedRealtime() - mLastClickTime < 2500) {
        return true
    }
    mLastClickTime = SystemClock.elapsedRealtime()
    return false
}
