package com.guard.gof.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16-2-17.
 */
public class Array implements Iterator {

    List<String> array=new ArrayList<>();
    private int current=-1;

    @Override
    public boolean hasNext() {
        if(current+1>=array.size()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String next() {
        if(hasNext()){
            return array.get(current+1);
        }
        return null;
    }

}
