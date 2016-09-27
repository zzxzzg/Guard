package com.zzxzzg.omapperdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zzxzzg.omapperdemo.necessary.TestBean1;
import com.zzxzzg.omapperdemo.necessary.TestBean2;
import com.zzxzzg.omapperdemo.necessary.TestBean3;
import com.zzxzzg.omapperdemo.necessary.TestBean4;

import org.omapper.exception.NecessaryFieldEmptyException;
import org.omapper.mapper.CollatingMapper;
import org.omapper.mapper.CollectionMapper;
import org.omapper.mapper.SimpleMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bean9 bean=new Bean9();
        Field[] fields=bean.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            Log.d("sss",field.getType().getCanonicalName()+"   "+field.getDeclaringClass().getCanonicalName()+"  "+field.getGenericType().toString());
        }

        //method1();
        //method2();
        //method3();
        //method5();
        //method6();

        try{
            necessaryTest2();
        }catch (NecessaryFieldEmptyException e){
            e.printStackTrace();
            Log.d("sss","wait for method1 debug");
        }

    }

    public void necessaryTest(){
        TestBean1 bean1=new TestBean1();

        TestBean2 bean2=new TestBean2();
        bean2.setName("yxwang");
        //bean2.setNick("xiaochou");
        bean2.setAge(11);
        bean2.setEmp_id("fck");

        SimpleMapper mapper=new SimpleMapper(TestBean1.class,TestBean2.class);
        mapper.mapBean(bean1, bean2);

        Log.d("sss","wait for method1 debug");
    }

    public void necessaryTest2(){
        TestBean3 bean3=new TestBean3();

        TestBean4 bean4=new TestBean4();
        bean4.bean2s=new ArrayList<>();

        TestBean2 bean2_1=new TestBean2();
        bean2_1.setName("yxwang");
        bean2_1.setNick("xiaochou");
        bean2_1.setAge(11);
        bean2_1.setEmp_id("fck");

        TestBean2 bean2_2=new TestBean2();
        bean2_2.setName("yxwang");
        //bean2_2.setNick("xiaochou");
        bean2_2.setAge(11);
        bean2_2.setEmp_id("fck");

        TestBean2 bean2_3=new TestBean2();
        bean2_3.setName("yxwang");
        bean2_3.setNick("xiaochou");
        bean2_3.setAge(11);
        bean2_3.setEmp_id("fck");

        TestBean2 bean2_4=new TestBean2();
        bean2_4.setName("yxwang");
        bean2_4.setNick("xiaochou");
        bean2_4.setAge(11);
        bean2_4.setEmp_id("fck");

        bean4.bean2s.add(bean2_1);
        bean4.bean2s.add(bean2_2);
        bean4.bean2s.add(bean2_3);
        bean4.bean2s.add(bean2_4);

        SimpleMapper mapper=new SimpleMapper(TestBean3.class,TestBean4.class);
        mapper.mapBean(bean3, bean4);

        Log.d("sss","wait for method1 debug");
    }

    public void method1(){
        Bean1 bean1=new Bean1();
        bean1.setName("aa");
        bean1.setAddress("haha");
        bean1.setAge(10);
        bean1.setEmp_id(16);

        Bean2 bean2=new Bean2();
        SimpleMapper mapper=new SimpleMapper(Bean2.class,Bean1.class);
        mapper.mapBean(bean2, bean1);

        Log.d("sss","wait for method1 debug");
    }

    public void method2(){
        Bean1 bean1=new Bean1();
        bean1.setName("aa");
        bean1.setAddress("haha");
        bean1.setAge(10);
        bean1.setEmp_id(16);

        Bean3 bean3=new Bean3();
        List<String> strs=new ArrayList<>();
        strs.add("1");
        strs.add("2");
        bean3.setPositionsList(strs);
        bean3.setCompanyName("carme");


        Bean4 bean4=new Bean4();
        CollatingMapper collatingMapper=new CollatingMapper(Bean4.class,Bean1.class, Bean3.class);
        collatingMapper.mapBean(bean4, bean1,bean3);

        Log.d("sss","wait for method1 debug");
    }

    public void method3(){
        Bean1 bean1=new Bean1();
        bean1.setName("aa");
        bean1.setAddress("haha");
        bean1.setAge(10);
        bean1.setEmp_id(16);
        List<Bean1> bean1List=new ArrayList<>();
        bean1List.add(bean1);

        Bean6 bean6=new Bean6();
        bean6.setName("gg");
        bean6.setAddress("gaga");
        bean6.setAge(15);
        bean6.setEmp_id(20);
        bean6.setBean1List(bean1List);

        Bean5 bean5=new Bean5();

        SimpleMapper mapper=new SimpleMapper(Bean5.class,Bean6.class);
        mapper.mapBean(bean5, bean6);

        Log.d("sss","wait for method1 debug");

    }

    public void method4(){
        Bean1 bean1=new Bean1();
        bean1.setName("aa");
        bean1.setAddress("haha");
        bean1.setAge(10);
        bean1.setEmp_id(16);

        List<Bean1> bean1List=new ArrayList<>();
        bean1List.add(bean1);


        List<Bean2> bean2List=new ArrayList<>();

        CollectionMapper mapper=new CollectionMapper(Bean2.class,Bean1.class);

        mapper.mapBean(bean2List, bean1List);

        Log.d("sss","wait for method1 debug");
    }

    //将不同的对象映射到同一个对象
    public void method5(){
        Bean8 bean2=new Bean8();
        bean2.setName("aa");
        bean2.setAddress("haha");
        bean2.setAge(10);
        bean2.setEmp_id(16);


        Bean9 bean4=new Bean9();
        bean4.setName("aa");
        bean4.setAddress("haha");
        bean4.setAge(10);
        bean4.setEmp_id(16);

        Bean7 bean1a=new Bean7();
        SimpleMapper mapper=new SimpleMapper(Bean7.class,Bean8.class);
        mapper.mapBean(bean1a, bean2);


        Bean7 bean1b=new Bean7();
        SimpleMapper mapper2=new SimpleMapper(Bean7.class,Bean9.class);
        mapper2.mapBean(bean1b, bean4);

        Log.d("sss","wait for method1 debug");

    }

    public void method6(){
        Bean10 bean10=new Bean10();
        bean10.mMap=new HashMap<>();

        Bean1 bean1_0=new Bean1();
        bean1_0.setName("aa");
        bean1_0.setAddress("haha");
        bean1_0.setAge(10);
        bean1_0.setEmp_id(16);

        Bean1 bean1_1=new Bean1();
        bean1_1.setName("kk");
        bean1_1.setAddress("ttt");
        bean1_1.setAge(11);
        bean1_1.setEmp_id(18);

        bean10.mMap.put("first",bean1_0);
        bean10.mMap.put("second",bean1_1);

        Bean11 bean11=new Bean11();
        SimpleMapper mapper2=new SimpleMapper(Bean11.class,Bean10.class);
        mapper2.mapBean(bean11, bean10);

        Log.d("sss","wait for method1 debug");
    }
}
