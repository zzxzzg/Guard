package com.zzxzzg.omapperdemo.necessary;

import org.omapper.annotations.Mappable;
import org.omapper.annotations.Necessary;
import org.omapper.annotations.Sink;

/**
 * Created by yxwang on 16/9/27.
 */
@Mappable
public class TestBean2 {

    @Sink(type = TestBean1.class,property = "name")
    String name;
    @Sink(type = TestBean1.class,property = "nick")
    String nick;
    @Sink(type = TestBean1.class,property = "age")
    int age;
    @Sink(type = TestBean1.class,property = "emp_id")
    String emp_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }
}
