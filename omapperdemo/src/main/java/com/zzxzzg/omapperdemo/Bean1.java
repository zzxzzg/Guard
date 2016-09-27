package com.zzxzzg.omapperdemo;

import org.omapper.annotations.Mappable;
import org.omapper.annotations.Necessary;

/**
 * Created by yxwang on 16/7/15.
 */
public class Bean1 {

    /** The name. */
    @Necessary(necessary = true,nullable = true) //必须映射,但是值允许为空
    private String name;

    /** The address. */
    @Necessary(necessary = false,nullable = true) //不需要映射,映射也可以为空(相当于不加@Necessary)
    private String address;

    /** The age. */
    @Necessary(necessary = true,nullable = false) //必须映射,且值不能为空
    private int age;

    /** The emp_id. */
    @Necessary(necessary = false,nullable = false) //不需要映射,如果映射了就不能为空
    private Integer emp_id;










    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }
}
