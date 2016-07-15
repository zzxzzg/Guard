package com.zzxzzg.omapperdemo;

import org.omapper.annotations.Implementation;
import org.omapper.annotations.Mappable;
import org.omapper.annotations.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16/7/15.
 */
@Mappable
public class Bean4 {

    /** The name. */
    @Source(type = Bean1.class, property = "name")
    private String name;

    /** The address. */
    @Source(type = Bean1.class, property = "address")
    private String address;

    /** The age. */
    @Source(type = Bean1.class, property = "age")
    private int age;

    /** The emp_id. */
    @Source(type = Bean1.class, property = "emp_id")
    private Integer emp_id;

    /** The positions list. */
    @Source(type = Bean3.class, property = "positionsList")
    @Implementation(name = ArrayList.class)
    private List<String> positionsList;

    /** The company name. */
    @Source(type = Bean3.class, property = "companyName")
    private String companyName;
}
