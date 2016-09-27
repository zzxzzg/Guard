package com.zzxzzg.omapperdemo.necessary;

import org.omapper.annotations.Implementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxwang on 16/9/27.
 */
public class TestBean3 {
    @Implementation(name = ArrayList.class)
    List<TestBean1> bean1s;
}
