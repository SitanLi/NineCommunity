package com.mall.ninecommunity.view.fragment

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.mall.baselibrary.base.view.BaseFragment
import com.mall.baselibrary.common.Constants
import com.mall.baselibrary.pic.Pic
import com.mall.baselibrary.pic.PicListener
import com.mall.ninecommunity.R
import com.mall.ninecommunity.databinding.FragmentMineBinding
import com.mall.ninecommunity.dialog.SelectPicDialog
import com.mall.ninecommunity.utils.PicUtils
import com.mall.ninecommunity.utils.imageload.setImageCircleUrl
import com.mall.ninecommunity.viewmodels.viewmodel.MineViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MineFragment : BaseFragment<FragmentMineBinding>(), PicListener {

    companion object {
        fun newInstance(): MineFragment = MineFragment()
    }

    private val mineViewModel: MineViewModel by viewModels()

    override fun initLayoutId(): Int = R.layout.fragment_mine

    override fun initView() {
        dataBinding.handlers = MineHandlers()
    }


    override fun initData() {
        val imgUrl = SPUtils.getInstance().getString(Constants.SPKey.HEAD_IMG)
        dataBinding.userIcon.setImageCircleUrl(imgUrl)
    }

    inner class MineHandlers {
        fun toLogin(view: View) {
            val directions = PagerFragmentDirections.actionPagerFragmentToNavLogin()
            view.findNavController().navigate(directions)
        }

        fun setHead(view: View) {
            SelectPicDialog(requireContext(), R.style.public_Theme_dialog, object : SelectPicDialog.OnSelectPicListener {
                override fun selectAlbum() {
                    activity?.let { PicUtils.takePic(it, PicUtils.TAKE_PICTURE, mineViewModel.getBuilder(), this@MineFragment) }
                }

                override fun selectCamera() {
                    activity?.let { PicUtils.takePic(it, PicUtils.TAKE_CAMERA, mineViewModel.getBuilder(), this@MineFragment) }
                }
            }).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activity?.let { Pic.getPicManager().onActivityResult(it, requestCode, resultCode, data) }
    }

    override fun onTakePicSuccess(uri: Uri?) {
        if (uri == null) {
            ToastUtils.showShort("生成照片失败...")
            return
        }
        SPUtils.getInstance().put(Constants.SPKey.HEAD_IMG,uri.toString())
        dataBinding.userIcon.setImageCircleUrl(uri.toString())
    }

    override fun onTakePicFail() {
    }

}