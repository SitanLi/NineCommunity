package com.mall.ninecommunity.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class SearchEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    companion object {
        const val LIMIT = 100L
    }

    private var mListener: ((String) -> Unit)? = null
    private var mStartText: String = ""
    private val mAction = Runnable {
        if (mListener == null) return@Runnable
        if (mStartText != text.toString()) {
            mStartText = text.toString()
            mListener?.invoke(mStartText)
        }
    }

    /**
     * 在LIMIT时间内连续输入不触发文本
     */
    fun setOnTextChangeListener(listener: ((String) -> Unit)?) {
        if (listener == null) return
        mListener = listener
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        removeCallbacks(mAction)
        postDelayed(mAction, LIMIT)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks(mAction)
    }

}