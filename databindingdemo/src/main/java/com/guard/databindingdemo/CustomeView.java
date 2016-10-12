package com.guard.databindingdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yxwang on 16/10/10.
 */

@BindingMethods({
        @BindingMethod(type = com.guard.databindingdemo.CustomeView.class,
                attribute = "customeValue",
                method = "setValueCustome"),
})
public class CustomeView extends TextView {

    private String mCustomeValue;

    public CustomeView(Context context) {
        super(context);
    }

    public CustomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);

    }

    public CustomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
    }

    public void initAttrs(Context context, AttributeSet attrs){
        if(attrs==null){
            return;
        }
        //如果使用databinding 那么以下方法可以注释,只需要有对应的setter方法即可
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomeView);
        mCustomeValue = typedArray.getString(R.styleable.CustomeView_customeValue);
        typedArray.recycle();

        setValueCustome(mCustomeValue);

    }

    public void setValueCustome(String string){
        setText(string);
    }

    public void setValueCustome(int stringRes){
        setText(stringRes);
    }

}
