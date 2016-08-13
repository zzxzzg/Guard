package com.zzxzzg.omapperdemo;

import org.omapper.annotations.Implementation;
import org.omapper.annotations.Mappable;
import org.omapper.annotations.Sink;
import org.omapper.annotations.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16/8/4.
 */
@Mappable
public class Bean9 {
    /** The name. */
    @Sink(type = Bean7.class, property = "name")
    private String name;

    /** The address. */
    @Sink(type = Bean7.class, property = "address")
    private String address;

    /** The age. */
    @Sink(type = Bean7.class, property = "age")
    private int age;

    /** The emp_id. */
    @Sink(type = Bean7.class, property = "emp_id")
    private Integer emp_id;

    /** The positions list. */
    @Sink(type = Bean3.class, property = "positionsList")
    @Implementation(name = ArrayList.class)
    private List<String> positionsList;

    /** The company name. */
    @Sink(type = Bean3.class, property = "companyName")
    private String companyName;

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

    public List<String> getPositionsList() {
        return positionsList;
    }

    public void setPositionsList(List<String> positionsList) {
        this.positionsList = positionsList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
