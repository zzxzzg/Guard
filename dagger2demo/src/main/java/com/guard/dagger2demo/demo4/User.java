package com.guard.dagger2demo.demo4;


import javax.inject.Inject;

/**
 * Created by yxwang on 16-4-15.
 */
public class User {

    @Inject BeanTypeA beanTypeA;

    @Inject BeanTypeB beanTypeB;

    @Inject RealObj realObj;

    public User(){
        BeanComponent beanComponent=DaggerBeanComponent.builder().beanModule(new BeanModule()).build();
        MainComponent component= DaggerMainComponent.builder().beanComponent(beanComponent).build();
        component.inject(this);
    }
}
