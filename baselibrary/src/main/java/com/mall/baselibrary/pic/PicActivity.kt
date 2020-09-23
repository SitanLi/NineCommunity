package com.mall.baselibrary.pic

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author Administrator
 * @Date 2019/10/17-17:29
 * @TODO 读取摄像头|相册图片 activity，需要在manifest注册
 */
class PicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //不接受触摸屏事件
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        Pic.getPicManager().behavior(this, intent, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Pic.getPicManager().onActivityResult(this, requestCode, resultCode, data)
    }
}
