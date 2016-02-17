package com.guard.gof.factory;

/**
 * Created by yxwang on 16-2-16.
 */
public class Factory {

    public static int TYPE_PC=0;
    public static int TYPE_PHONE=1;

    public static Product createProcessor(int type){
        if(type==TYPE_PC){
            return new CPU();
        }else if(type == TYPE_PHONE){
            return new ARM();
        }
        return null;
    }
}
