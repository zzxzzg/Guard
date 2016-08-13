package com.zzxzzg.anroidtestdemo;

/**
 * Created by yxwang on 16/8/11.
 */
public class TestUtils {
    public String printWithDanger(int i){
        if (i == 0){
            throw new NullPointerException();
        }else{
            return "Hello JUnit";
        }
    }

    public String print(int a){
        System.out.println("==========current input number is: " + a + "==========");
        return  a > 0? "大":"小";
    }
}
