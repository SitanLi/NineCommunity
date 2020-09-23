package com.mall.ninecommunity.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Administrator
 * created at 2016/3/30 10:50
 * TODO:自定义ProgressBar,可显示text文本
 */
public class TextProgressView extends ProgressBar {

    private String text;
    private Paint mPaint;
    private boolean isCenter = false;
    private Rect rect;

    public TextProgressView(Context context) {
        super(context);
        initText();
    }

    public TextProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText();
    }

    public TextProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initText();
    }


    @Override
    public synchronized void setProgress(int progress) {
        // TODO Auto-generated method stub
        super.setProgress(progress);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        if (null != text) {
            this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
            int x = (getWidth() / 2) - rect.centerX();
            int y = (getHeight() / 2) - rect.centerY();

            if (!isCenter) {
                x = 20;

            }
            canvas.drawText(this.text, x, y, this.mPaint);
        }
    }

    //初始化，画笔
    private void initText() {
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize(15);
        rect = new Rect();

    }

    public void setTextGravity(boolean isCenter) {

        this.isCenter = isCenter;
    }

    //设置文字内容（用于百分比）
    public void setText(int progress) {
        int i = (progress * 100) / this.getMax();
        this.text = String.valueOf(i) + "%";
    }

    //设置文字内容（任何文本）
    public void setText(String msg) {
        this.text = msg;
    }

    //设置文字大小
    public void setTextFontSize(int size){
        this.mPaint.setTextSize(size);
    }
}
