package com.zzxzzg.omapperdemo;

import org.omapper.annotations.Implementation;
import org.omapper.annotations.Mappable;
import org.omapper.annotations.Source;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yxwang on 16/9/27.
 */
@Mappable
public class Bean11 {
    @Source(type = Bean10.class,property = "mMap")
    @Implementation(name = HashMap.class)
    Map<String,Bean2> mMap;
}
