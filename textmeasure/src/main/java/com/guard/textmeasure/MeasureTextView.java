package com.guard.textmeasure;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by yxwang on 15-12-9.
 */
public class MeasureTextView extends TextView {
    public MeasureTextView(Context context) {
        super(context);
    }

    public MeasureTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeasureTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("sss", "the line hight=" + getLineHeight());
        //Log.d("sss", "the letter spacing =" + getLetterSpacing());//api 21
        //setLetterSpacing(3);//设置字间距  单位是 em  一个em表示一种特殊字体的大写字母M的高度
        //Log.d("sss", "the letter spacing =" + getLetterSpacing());//api 21

        TextPaint paint=getPaint();
        //1. 粗略计算文字宽度
        Log.d("sss", "measureText=" + paint.measureText(getText().toString()));
        //精确计算文字宽度
        Log.d("sss", "getTextWidth=" + getTextWidth(paint,getText().toString()));
        //结果一样……

        //关于字高度的测量请百度  FontMetrics



        super.onDraw(canvas);
    }

    public static int getTextWidth(TextPaint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }
}
