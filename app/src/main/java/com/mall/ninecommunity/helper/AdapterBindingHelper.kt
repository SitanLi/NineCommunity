package com.mall.ninecommunity.helper

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.mall.ninecommunity.common.ImageViewEnum
import com.mall.ninecommunity.utils.imageload.setImageCircleUrl
import com.mall.ninecommunity.utils.imageload.setImageDefaultLoadIconUrl
import com.mall.ninecommunity.utils.imageload.setImageRadiusUrl

object AdapterBindingHelper {
    @BindingAdapter(value = ["imageUrl", "imageType","radius"], requireAll = false)
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?, imageType: Enum<ImageViewEnum>?,radius:Int) {
        if (url.isNullOrEmpty())return
        if (imageType == ImageViewEnum.CIRCLE) imageView.setImageCircleUrl(url) else imageView.setImageRadiusUrl(url,radius)
    }

    /**
     * 进度条通用
     */
    @BindingAdapter("fullProgressGone")
    @JvmStatic
    fun fullProgressGone(progressBar: ProgressBar?, progress: MutableLiveData<Int>) {
        val progressNum = progress.value ?: 0
        if (progressNum >= 80) {
            progressBar?.visibility = View.GONE
        } else {
            progressBar?.visibility = View.VISIBLE
            progressBar?.progress = progressNum
        }
    }
}