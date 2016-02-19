package com.guard.gof.share;

/**
 * Created by yxwang on 16-2-17.
 */
//享元模式
public class Main {
    public void execute(){
        Order order1=new Order();
        order1.putMenu(ShareCellFactory.INSTANCE.factory("dish1"));
        order1.putMenu(ShareCellFactory.INSTANCE.factory("dish2"));
        order1.putMenu(ShareCellFactory.INSTANCE.factory("dish3"));
        order1.putMenu(ShareCellFactory.INSTANCE.factory("dish4"));

        Order order2=new Order();
        order2.putMenu(ShareCellFactory.INSTANCE.factory("dish1"));
        order2.putMenu(ShareCellFactory.INSTANCE.factory("dish3"));
        order2.putMenu(ShareCellFactory.INSTANCE.factory("dish4"));
        order2.putMenu(ShareCellFactory.INSTANCE.factory("dish5"));

    }
}
