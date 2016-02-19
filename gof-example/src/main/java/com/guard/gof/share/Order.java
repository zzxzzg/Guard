package com.guard.gof.share;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yxwang on 16-2-17.
 */
public class Order {
    private Map<String,Menu> menuList=new HashMap<>();

    public void putMenu(Menu menu){
        if(menuList.containsKey(menu.getName())){
            return;
        }else{
            menuList.put(menu.getName(),menu);
        }
    }



}
