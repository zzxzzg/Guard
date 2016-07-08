package com.guard.rxjava.demo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by yxwang on 16/7/7.
 */
public class Test3 {
    public void test(){

        //两个接受不同类型的Subscriber 组件 通过一个map(func1)的中间件竟然都注册到同一个Observable上了.

        Integer []nums=new Integer[]{1,2,3,4};

        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("sss","the string value is "+s);
            }
        };

        Subscriber<Integer> subscriber2=new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer s) {
                Log.d("sss","the int value plus 1 is "+(s+1));
            }
        };

        Observable<Integer> observable=Observable.from(nums);

        observable.subscribe(subscriber2);

        observable.map(new Func1<Integer, String>() {

            @Override
            public String call(Integer integer) {
                return String.valueOf(integer);
            }
        }).subscribe(subscriber);

        observable.subscribe(subscriber2);
    }

    class Course{
        String name;
        int score;
        public Course(String name,int score){
            this.name=name;
            this.score=score;
        }
    }

    class Student{
        String name;
        List<Course> mCourses=new ArrayList<>();
        public Student(String name,List<Course> courses){
            this.name=name;
            mCourses=courses;
        }
    }

    public void test2(){
        Course course1=new Course("语文",90);
        Course course2=new Course("数学",89);
        Course course3=new Course("英语",91);
        Course course4=new Course("物理",92);
        Course course5=new Course("生物",93);
        Course course6=new Course("化学",94);
        Course course7=new Course("地理",95);

        List<Course> list1=new ArrayList<>();
        list1.add(course1);
        list1.add(course3);
        list1.add(course4);
        list1.add(course5);

        List<Course> list2=new ArrayList<>();
        list2.add(course2);
        list2.add(course4);
        list2.add(course5);
        list2.add(course7);

        List<Course> list3=new ArrayList<>();
        list3.add(course1);
        list3.add(course5);
        list3.add(course6);
        list3.add(course7);

        Student student1=new Student("小张",list1);
        Student student2=new Student("小王",list2);
        Student student3=new Student("小龙",list3);

        Subscriber<Course> subscribe=new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.d("sss",course.name +" is "+course.score);
            }
        };

        Observable.just(student1,student2,student3).
                flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                Log.d("sss",student.name+":");
                return Observable.from(student.mCourses);
            }
        }).subscribe(subscribe);

    }

    public void test3(){
        //lift变换: 通过operator操作,返回一个中间subscriber,该subscriber再处理数据,派发数据到最终接受者.
        Integer []nums=new Integer[]{1,2,3,4};
        Observable<Integer> observable=Observable.from(nums);

        observable.lift(new Observable.Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                return new Subscriber<Integer>(){

                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        subscriber.onNext(integer+"");
                    }
                };
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("sss",s);
            }
        });
    }

    public class LiftAllTransformer implements Observable.Transformer<Integer, String> {
        @Override
        public Observable<String> call(Observable<Integer> observable) {
            return observable.lift(new Observable.Operator<String, Integer>() {
                @Override
                public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                    return new Subscriber<Integer>(){

                        @Override
                        public void onCompleted() {
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onNext(Integer integer) {
                            subscriber.onNext(integer+"");
                        }
                    };
                }
            });
        }
    }

    public void test4(){
        Integer []nums=new Integer[]{1,2,3,4};
        Observable<Integer> observable=Observable.from(nums);
        observable.compose(new LiftAllTransformer()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("sss",s);
            }
        });

        Observable<Integer> observable2=Observable.from(nums);
        observable2.compose(new LiftAllTransformer()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("sss",s);
            }
        });
    }


}
