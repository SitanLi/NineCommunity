package com.mall.ninecommunity.utils.imageload

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mall.ninecommunity.R
import com.mall.ninecommunity.utils.glide.BlurTransformation
import com.mall.ninecommunity.utils.glide.GlideCircleWithBorder

fun ImageView.setImageUrlNoPlaceHolder(url: String) {
    Glide.with(this).load(url).error(ImageLoadConfig.instance().rectangleErrorResId).into(this)
}

fun ImageView.setImageCircleUrl(url: String) {
    Glide.with(this).load(url).placeholder(ImageLoadConfig.instance().circleLoadResId).error(ImageLoadConfig.instance().circleErrorResId).apply(RequestOptions.bitmapTransform(CircleCrop())).into(this)
}

fun ImageView.setImageCircleColorUrl(url: String, strokeColor: Int, strokeWidth: Float) {
    Glide.with(this).load(url).placeholder(ImageLoadConfig.instance().circleLoadResId).error(ImageLoadConfig.instance().circleErrorResId).apply(RequestOptions.bitmapTransform(GlideCircleWithBorder(strokeWidth, strokeColor))).into(this)
}

fun ImageView.setImageDefaultUserIconUrl(url: String?, circle: Int = 360) {
    when (circle) {
        0 -> Glide.with(this).load(url).placeholder(ImageLoadConfig.instance().circleLoadResId).error(ImageLoadConfig.instance().circleErrorResId).into(this)
        360 -> setImageCircleUrl(url ?: "")
        else -> Glide.with(this).load(url).placeholder(ImageLoadConfig.instance().circleLoadResId).error(ImageLoadConfig.instance().circleErrorResId).apply(RequestOptions.bitmapTransform(RoundedCorners(circle))).into(this)
    }
}

fun ImageView.setImageDefaultLoadIconUrl(url: String) {
    Glide.with(this).load(url).placeholder(ImageLoadConfig.instance().rectangleLoadResId).error(ImageLoadConfig.instance().rectangleErrorResId).centerCrop().into(this)
}

fun ImageView.setImageDefaultNoLoadIconUrl(url: String) {
    Glide.with(this).load(url).error(ImageLoadConfig.instance().rectangleErrorResId).centerCrop().into(this)
}

fun ImageView.setImageRadiusUrl(url: String, cornerRadiusPixels: Int) {
    val requestOptions = RequestOptions.bitmapTransform(MultiTransformation(CenterCrop(), RoundedCorners(cornerRadiusPixels)))
    Glide.with(this).load(url).error(ImageLoadConfig.instance().circleErrorResId).apply(requestOptions).into(this)
}

fun ImageView.setImageBlurUrl(url: String, radius: Int, sampling: Int) {
    Glide.with(this).load(url).error(ImageLoadConfig.instance().circleErrorResId).apply(RequestOptions.bitmapTransform(BlurTransformation(context, radius, sampling))).into(this)
}

object ImageLoaderUtils {
    fun loadBitmap(context: Context, url: String, callback: (Bitmap?) -> Unit) {
        val listener = Glide.with(context).asBitmap().load(url).error(ImageLoadConfig.instance().rectangleLoadResId).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                callback.invoke(null)
                return false
            }

            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                callback.invoke(resource)
                return false
            }

        })
        listener.submit()
    }

    fun loadBitmap(context: Context, url: String, callback: (Bitmap?) -> Unit, width: Int, height: Int) {
        val listener = Glide.with(context).asBitmap().load(url).error(ImageLoadConfig.instance().rectangleLoadResId).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                callback.invoke(null)
                return false
            }

            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                callback.invoke(resource)
                return false
            }

        })
        listener.submit(width, height)
    }
}