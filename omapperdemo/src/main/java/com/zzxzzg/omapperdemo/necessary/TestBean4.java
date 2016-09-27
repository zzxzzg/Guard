package com.zzxzzg.omapperdemo.necessary;

import org.omapper.annotations.Mappable;
import org.omapper.annotations.Sink;

import java.util.List;

/**
 * Created by yxwang on 16/9/27.
 */
@Mappable
public class TestBean4 {
    @Sink(type = TestBean3.class,property = "bean1s")
    public List<TestBean2> bean2s;
}
