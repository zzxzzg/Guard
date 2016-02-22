package com.guard.gof.bridge;

/**
 * Created by yxwang on 16-2-22.
 */
//桥接器模式
public class Main {
    public void execute(){
        Bridge bridge=new Bridge();
        bridge.setCar(new Truck());
        bridge.setRoad(new Street());
        bridge.print();

        bridge.setCar(new Roadster());
        bridge.print();

        bridge.setRoad(new HighWay());
        bridge.print();
    }

}
