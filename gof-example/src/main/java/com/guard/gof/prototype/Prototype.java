package com.guard.gof.prototype;

import java.util.ArrayList;

/**
 * Created by yxwang on 16-2-17.
 */
public class Prototype implements Cloneable {
    private ArrayList list = new ArrayList();

    public Prototype clone(){
        Prototype prototype=null;
        prototype=this.clone();
        prototype.list= (ArrayList) list.clone();
        return prototype;
    }
}
