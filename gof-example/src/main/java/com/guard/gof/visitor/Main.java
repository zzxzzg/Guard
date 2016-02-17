package com.guard.gof.visitor;

/**
 * Created by yxwang on 16-2-17.
 */
//访问者模式 安装和拆卸并不算是Product的属性，所以放入Product类中并不合适
public class Main {
    public void execute(){
        ObjectStructure structure=new ObjectStructure();
        structure.attach(new ARM());
        structure.attach(new CPU());

        structure.execute(new Install());

        structure.execute(new UnInstall());
    }
}
