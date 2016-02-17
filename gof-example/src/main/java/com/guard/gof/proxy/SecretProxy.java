package com.guard.gof.proxy;

/**
 * Created by yxwang on 16-2-17.
 */
public class SecretProxy {
    private ISecret mSecret;
    public SecretProxy(){
        mSecret=new SecretLove();
    }

    public void request(){
        mSecret.message();
    }
}
