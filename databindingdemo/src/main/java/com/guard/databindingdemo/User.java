package com.guard.databindingdemo;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.DataBindingComponent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yxwang on 16-3-14.
 */
public class User {
    private  String firstName;
    private  String lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static class MyDatabindingComponent implements android.databinding.DataBindingComponent{

    }

    public int getHintTextColor(){
        return Color.BLACK;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }

    public int getPadding(){
        return 30;
    }

    public boolean isFriend(){
        return true;
    }

    //如果set方法中需要ColorDrawable 对象,但是我们传入的是int,那么就会调用该方法将int转化为ColorDrawable
    @BindingConversion
    public static ColorDrawable conversionIntToDrawable(int color){
        return new ColorDrawable(color);
    }

    //这个属性竟然无效!  可能和它的set方法不是setTextColorHint有关,系统应该有重新绑定的
    @BindingAdapter("android:textColorHint")
    public static void setTextColorHint(View view,int color){
        Log.d("sss","setTextColorHint");
    }

    //set方法可以使用双参数,old原来的值
    //只有在binding被设置component之后,这里的component才不为空,否则为空
    @BindingAdapter("android:hint")
    public static void setHintWithComponent(MyDatabindingComponent component,View view,String oldHint,String hint){
        Log.d("sss","setHintWithComponent  "+hint+"  "+oldHint);
        ((TextView)view).setHint(hint);
    }

    //优先调用没有component的方法,并且带componet的方法不会被调用
//    @BindingAdapter("android:hint")
//    public static void setHint(View view,String oldHint,String hint){
//        Log.d("sss","setHint  "+hint+"  "+oldHint);
//        ((TextView)view).setHint(hint);
//    }

    @BindingAdapter("kkk")
    public static void setKKK(View view,int color){
        Log.d("sss","setKKK");
    }

    @BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view,int padding){
        Log.d("sss","setPaddingLeft");
        view.setPadding(padding,
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    @BindingAdapter(value = {"android:paddingTop","android:paddingBottom"},requireAll = true)
    public static void setPaddingV(View view,int paddingTop,int paddingBottom){
        view.setPadding(view.getPaddingTop(),
                paddingTop,
                view.getPaddingRight(),
                paddingBottom);
    }

}
