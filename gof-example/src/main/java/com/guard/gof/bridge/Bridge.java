package com.guard.gof.bridge;

/**
 * Created by yxwang on 16-2-22.
 */
public class Bridge {
    private ICar car;
    private IRoad road;


    public ICar getCar() {
        return car;
    }

    public void setCar(ICar car) {
        this.car = car;
    }

    public IRoad getRoad() {
        return road;
    }

    public void setRoad(IRoad road) {
        this.road = road;
    }

    public String print(){
        return car.getName()+" run in " +road.getName();
    }
}
