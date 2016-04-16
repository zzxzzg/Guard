package com.guard.dagger2demo.demo3;

import javax.inject.Inject;

/**
 * Created by yxwang on 16-4-14.
 */
public class Person {
    @Inject
    @Country("china")
    Name mChineseName;

    @Inject
    @Country("foreigner")
    Name mForeignerName;

    @Inject
    public Person(){
        NameComponent component = DaggerNameComponent.builder().nameModule(new NameModule()).build();
        component.inject(this);

        printName();
    }


    public void printName(){
        mChineseName.printName();
        mForeignerName.printName();
    }
}
