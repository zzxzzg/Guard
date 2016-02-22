package com.guard.gof.adapter;

/**
 * Created by yxwang on 16-2-22.
 */
public class Main {
    public void execute(){

        //mode1
        Adapter adapter=new Adapter();

        TargetPerson person=new TargetPerson();

        person.setTarget(adapter);

        person.dothing1();
        person.dothing2();
        person.dothing3();


        //mode 2
        Adapter2 adapter2=new Adapter2(new CurrentPerson());
        TargetPerson person2=new TargetPerson();

        person2.setTarget(adapter2);

        person2.dothing1();
        person2.dothing2();
        person2.dothing3();
    }
}
