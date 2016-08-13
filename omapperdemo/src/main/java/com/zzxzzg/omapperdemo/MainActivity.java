package com.zzxzzg.omapperdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.omapper.mapper.CollatingMapper;
import org.omapper.mapper.CollectionMapper;
import org.omapper.mapper.SimpleMapper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //method1();
        //method2();
        //method3();
        method5();
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
}
