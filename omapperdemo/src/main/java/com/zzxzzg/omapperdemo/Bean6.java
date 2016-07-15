package com.zzxzzg.omapperdemo;

import org.omapper.annotations.Mappable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16/7/15.
 */
@Mappable
public class Bean6 {
    /** The name. */
    private String name;

    /** The address. */
    private String address;

    /** The age. */
    private int age;

    /** The emp_id. */
    private Integer emp_id;

    private List<Bean1> bean1List;





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

    public List<Bean1> getBean1List() {
        return bean1List;
    }

    public void setBean1List(List<Bean1> bean1List) {
        this.bean1List = bean1List;
    }
}
