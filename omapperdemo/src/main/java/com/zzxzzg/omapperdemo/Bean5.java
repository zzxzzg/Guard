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
public class Bean5 {

    /** The name. */
    @Source(type = Bean6.class, property = "name")
    private String name;

    /** The address. */
    @Source(type = Bean6.class, property = "address")
    private String address;

    /** The age. */
    @Source(type = Bean6.class, property = "age")
    private int age;

    /** The emp_id. */
    @Source(type = Bean6.class, property = "emp_id")
    private Integer emp_id;

    @Source(type = Bean6.class, property = "bean1List")
    @Implementation(name = ArrayList.class)
    private List<Bean2> bean2;
}
