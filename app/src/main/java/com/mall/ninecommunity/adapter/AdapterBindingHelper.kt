package com.mall.ninecommunity.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mall.ninecommunity.common.ImageViewEnum
import com.mall.ninecommunity.utils.imageload.setImageCircleUrl
import com.mall.ninecommunity.utils.imageload.setImageDefaultLoadIconUrl

object AdapterBindingHelper {
    @BindingAdapter(value = ["imageUrl", "imageType"], requireAll = false)
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String, imageType: Enum<ImageViewEnum>?) {
        if (imageType == ImageViewEnum.CIRCLE) imageView.setImageCircleUrl(url) else imageView.setImageDefaultLoadIconUrl(url)
    }
}