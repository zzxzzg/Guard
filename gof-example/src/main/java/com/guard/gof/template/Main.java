package com.guard.gof.template;

/**
 * Created by yxwang on 16-2-17.
 */
//模板模式
public class Main {
    public void execute(){
        HongQiao hongQiao=new HongQiao();
        hongQiao.haveDinner();

        WaiPoJia waiPoJia=new WaiPoJia();
        waiPoJia.haveDinner();
    }
}
