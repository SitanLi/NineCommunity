package com.mall.ninecommunity.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import com.mall.baselibrary.base.dialog.BaseDialog
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.DialogSelectPicBinding

/**

 * @Author Administrator
 * @Date 2018/12/13-17:00
 *  选择相册|拍照
 */
class SelectPicDialog(context: Context, themeResId: Int, private var listener: OnSelectPicListener) : BaseDialog<DialogSelectPicBinding>(context, themeResId), View.OnClickListener {
    init {
        initMatchWidthContentView(Gravity.BOTTOM)
        binding.selectAlbum.setOnClickListener(this)
        binding.selectCamera.setOnClickListener(this)
        binding.mCancel.setOnClickListener(this)
    }

    override fun initBindingResId(): Int = R.layout.dialog_select_pic

    override fun onClick(v: View) {
        dismiss()
        when (v.id) {
            R.id.select_album -> listener.selectAlbum()
            R.id.select_camera -> listener.selectCamera()
        }
    }


    interface OnSelectPicListener {
        fun selectAlbum()
        fun selectCamera()
    }
}