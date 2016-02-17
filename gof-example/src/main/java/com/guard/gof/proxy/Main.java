package com.guard.gof.proxy;

/**
 * Created by yxwang on 16-2-17.
 */
//代理模式
public class Main {
    public void execute(){
        SecretProxy proxy=new SecretProxy();
        proxy.request();
    }
}
