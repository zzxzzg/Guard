package com.guard.gof.share;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yxwang on 16-2-17.
 */
public enum ShareCellFactory {
    INSTANCE;
    private Map<String,Menu> menuList=new HashMap<>();

    public Menu factory(String name){
        if(menuList.containsKey(name)){
            return menuList.get(name);
        }else{
            Menu menu=new Menu();
            menu.setName(name);
            menuList.put(name,menu);
            return menu;
        }
    }
}
