package com.guard.rxjava.demo;

import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.BlockingObservable;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by yxwang on 16/7/7.
 */
public class Test3 {
    public void mapOperation(){

        //两个接受不同类型的Subscriber 组件 通过一个map(func1)的中间件竟然都注册到同一个Observable上了.
        //以下这种写法数据竟然被发送了三次……

        Integer []nums=new Integer[]{1,2,3,4};

        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
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
                e.printStackTrace();
            }

            @Override
            public void onNext(Integer s) {
                Log.d("sss","the int value plus 1 is "+(s+1));
            }
        };

        Observable<Integer> observable=Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.d("sss","send data");
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        });

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

    public void flatMapOperation(){
        //flatMap 顺序是不确定的
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

    public void switchMap(){
        //switchMap 和 flatMap 类似,不同点在于 如果第二个observable开始发送数据,那么前一个observable中的数据会被丢弃
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
                switchMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        Log.d("sss",student.name+":");
                        return Observable.from(student.mCourses);
                    }
                }).subscribe(subscribe);
    }

    public void concatMapOperation(){
        //concatMap 和 flatMap 类似,不通电在于 严格按照顺序来进行
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
                concatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        Log.d("sss",student.name+":");
                        return Observable.from(student.mCourses);
                    }
                }).subscribe(subscribe);

    }


    public void flatMapIterableOperation(){
        //flatMapIterable 操作符的作用和flatMap基本相同,不同点在于flatMapIterable返回 Iterable
        //顺序是不确定的
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

        Observable.just(student1,student2,student3).flatMapIterable(new Func1<Student, Iterable<Course>>() {
            @Override
            public Iterable<Course> call(Student student) {
                return student.mCourses;
            }
        }).subscribe(new Action1<Course>() {
            @Override
            public void call(Course course) {
                Log.d("sss",course.name +" is "+course.score);
            }
        });


    }


    public void liftOperation(){
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

    public void composeOperation(){
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

    public void skipOperation(){
        //skip 跳过指定个数的数据
        Observable.just(1,2,3,4,5,6,7).skip(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("sss","the int is "+integer);
            }
        });
    }

    public void skipUntilOperation(){
        //skipUntil 根据一个标志Observable来判断的，当这个标志Observable没有发射数据的时候，
        // 所有源Observable发射的数据都会被跳过；当标志Observable发射了一个数据，则开始正常地发射数据。
        Observable.interval(200,TimeUnit.MILLISECONDS).take(7).
                skipUntil(Observable.timer(300,TimeUnit.MILLISECONDS)).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","skilUntilOperation "+aLong);
            }
        });
    }

    public void skipWhileOperation(){
        //skipWhile 一直放弃数据,直到满足条件,开始发射数据.
        Observable.interval(200,TimeUnit.MILLISECONDS).take(7).skipWhile(new Func1<Long, Boolean>() {
            @Override
            public Boolean call(Long aLong) {
                return aLong==5L;
            }
        }).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","skipWhileOperation "+aLong);
            }
        });
    }

    public void takeOperation(){
        //take 操作符指定输出数量
        Observable<String> observable1= Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("  haha ");
                subscriber.onNext("I am yxwang!");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber1=new Subscriber<String>() {

            //this method is called in current thread!!
            @Override
            public void onStart() {
                super.onStart();
                Log.d("sss","onStart");
            }

            @Override
            public void onCompleted() {
                Log.d("sss","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("sss","onError");
            }

            @Override
            public void onNext(String s) {
                Log.d("sss",s);
            }
        };

        observable1.take(2).subscribe(subscriber1);
    }

    public void takeUntilOperation(){
        //takeUntil 和 skikUntil相反,根据一个标志Observable来判断的，当这个标志Observable没有发射数据的时候，
        // 所有源Observable发射的数据都会被发射；当标志Observable发射了一个数据，则结束发射。
        Observable.interval(200,TimeUnit.MILLISECONDS).take(7).
                takeUntil(Observable.timer(300,TimeUnit.MILLISECONDS)).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","skilUntilOperation "+aLong);
            }
        });
    }

    public void takeWhileOperation(){
        //takeWhile 和skipWhile相反 一直发射数据,直到满足条件,结束发射.
        Observable.interval(200,TimeUnit.MILLISECONDS).take(7).takeWhile(new Func1<Long, Boolean>() {
            @Override
            public Boolean call(Long aLong) {
                return aLong==5L;
            }
        }).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","skipWhileOperation "+aLong);
            }
        });
    }

    public void rangeOperation(){
        //rang 发射大于等于 10 的 5 个数
        Observable.range(10,5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });
    }

    public void deferOperation(){
        //defer方法定义的observable  每次在运行subscribe是都会调用Func0重新生成一个Observable,demo中每次返回的时间都不同
        //而使用just创建的observable 每次调用subscribe都会返回相同值.

        Observable<Long> observable1=Observable.defer(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return Observable.just(System.currentTimeMillis());
            }
        });

        Observable<Long> observable2=Observable.just(System.currentTimeMillis());

        observable1.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","aLong="+aLong);
            }
        });

        observable1.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","aLong="+aLong);
            }
        });

        observable1.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","aLong="+aLong);
            }
        });

        observable2.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","aLong="+aLong);
            }
        });

        observable2.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","aLong="+aLong);
            }
        });

        observable2.subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","aLong="+aLong);
            }
        });
    }

    public void intervalOperation(){
        //interval创建的 observable每隔指定时间发射一个数字,并且默认在computation中运行

         Observable<Long> observable=Observable.interval(1, TimeUnit.SECONDS)
                //interva operates by default on the computation Scheduler,so observe on main Thread
                .observeOn(AndroidSchedulers.mainThread());

    }

    public void repeatOperation(){
        //repeat 重复发送指定次数
        Observable.just(1).repeat(5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });
    }

    public void timerOperation(){
        //timer 在指定时间后发射数字0  并且默认在computation中运行
        Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
    }

    public void bufferOperation(){
        //buffer 将数据缓存后一次性发射

        //buffer(int count)表示缓存count个数据后发射
        //buffer(int count,int skip) 表示每skip个数据缓存count个发送,当count == skip 就相当于buffer(int count)
        //会收到 [1,2] [4,5] [7,8]
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).buffer(2, 3).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {

            }
        });

        //buffer(long time,TimeUnit unti)  将指定时间内收到的数据缓存起来一次性发射
        Observable.interval(1, TimeUnit.SECONDS).buffer(3,TimeUnit.SECONDS)
                .subscribe(new Action1<List<Long>>() {
                    @Override
                    public void call(List<Long> longs) {

                    }
                });
    }

    public void windowOperation(){
        //window和 buffer基本相同,不同点在于返回值不再是一个包含多个元素的集合,
        // 而是一个子Observable,该子Observable会发射收集到的数据
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).window(2,3).subscribe(new Action1<Observable<Integer>>() {
            @Override
            public void call(Observable<Integer> integerObservable) {
                integerObservable.subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {

                    }
                });
            }
        });

    }

    public void groupByOperation(){
        //groupby有两个参数(第二个参数可以省去),第一个参数用来对数据分组,第二个参数用来对数据进行变换
        /*groupBy将原始Observable分解为一个发射多个GroupedObservable的Observable，
        一旦有订阅，每个GroupedObservable就开始缓存数据。因此，如果你忽略这些GroupedObservable中的任何一个，
        这个缓存可能形成一个潜在的内存泄露。因此，如果你不想观察，也不要忽略GroupedObservable。
        你应该使用像take(0)这样会丢弃自己的缓存的操作符。
        如果你取消订阅一个GroupedObservable，那个Observable将会终止。
        如果之后原始的Observable又发射了一个与这个Observable的Key匹配的数据，
        groupBy将会为这个Key创建一个新的GroupedObservable。*/

        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).groupBy(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                if(integer<=3){
                    return "group1";
                }else if(integer<=6){
                    return "group2";
                }else{
                    return "group3";
                }
            }
        }).subscribe(new Subscriber<GroupedObservable<String, Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final GroupedObservable<String, Integer> stringIntegerGroupedObservable) {
                // 数据被分成组, 出现多个子的observable
                stringIntegerGroupedObservable.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("sss",stringIntegerGroupedObservable.getKey()+"  "+integer);
                    }
                });
            }
        });
    }

    public void castOperation(){
        //cast 操作符,将输入的对象直接强转换成对应类型后再发射.

        Observable.just(1,"2",3,4,5,6,7).cast(String.class).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    public void scanOperation(){

        //scan前一次发射的数据作为第二次发射因子,与当前需要发射的数据进行操作后生成数据发射.
        //比如下面 scan(new Func2<Integer, Integer, Integer>)Func2中第一个Integer表示上次发射的值,第二个参数表示
        //当前需要发射的值,第三个参数表示需要返回的值. 下面demo中,由于第一次没有发射,不调用func2直接发射数据,第二个数据运行func2
        //上一次发射1,当前发射2,返回1+2=3,以此类推

        //call get 1
        //arg1 = 1   arg2= 2
        //call get 3
        //arg1 = 3   arg2= 3
        //call get 6
        //arg1 = 6   arg2= 4
        //..
        Observable.just(1,2,3,4,5,6,7).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                Log.d("sss","arg1 = "+integer+"   arg2= "+integer2);
                return integer+integer2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("sss","call get "+ integer);
            }
        });

        //scan的另一种用法,提供一个初始值,类型随意,这里提供 "hello",于是打印如下
        //call get hello
        //arg1 = hello   arg2= 1
        //call get hello1
        //arg1 = hello1   arg2= 2
        //call get hello12
        //...
        //发现call 中多了一个值,就是初始值 hello
        Observable.just(1,2,3,4,5,6,7).scan("hello",new Func2<String, Integer, String>() {
            @Override
            public String call(String integer, Integer integer2) {
                Log.d("sss","arg1 = "+integer+"   arg2= "+integer2);
                return integer+integer2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String integer) {
                Log.d("sss","call get "+ integer);
            }
        });

    }

    public void reductOperation(){
        //reduce 使用方法和scan完全相同,不同在于,reduce只会发射最后计算结果,而scan会发射每次的计算结果
        Observable.just(1,2,3,4,5,6,7).reduce(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                Log.d("sss","arg1 = "+integer+"   arg2= "+integer2);
                return integer+integer2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("sss","call get "+ integer);
            }
        });
    }

    public void collectOperation(){
        //collect 一个很有趣的操作符,通过Func0返回一个对象(只调用一次),将该对象和当前发射的值进行操作(比如保存等),
        // 当所有需要发射的数据都经过这个流程,最终发射这个对象.
        Observable.just(1,2,3,4,5,6,7).collect(new Func0<List<Integer>>() {
            @Override
            public List<Integer> call() {
                return new ArrayList<Integer>();
            }
        }, new Action2<List<Integer>, Integer>() {
            @Override
            public void call(List<Integer> s, Integer integer) {
                s.add(integer);
            }
        }).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> s) {
                Log.d("sss","collectOperation "+s);
            }
        });
    }


    public void filterOperation(){
        //filter操作符用于过滤数据
        Observable<String> observable1= Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("  haha ");
                subscriber.onNext("I am yxwang!");
                subscriber.onCompleted();
            }
        });

        Subscriber<String> subscriber1=new Subscriber<String>() {

            //this method is called in current thread!!
            @Override
            public void onStart() {
                super.onStart();
                Log.d("sss","onStart");
            }

            @Override
            public void onCompleted() {
                Log.d("sss","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("sss","onError");
            }

            @Override
            public void onNext(String s) {
                Log.d("sss",s);
            }
        };

        observable1.filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                if(s.contains("haha")){
                    return false;
                }
                return true;
            }
        }).subscribe(subscriber1);

    }

    public void throttleOperation(){
        //throttleWithTimeout 用来进行时间过滤,如果当前发射的数据和下一个发射的数据间隔小于指定时间,那么过滤当前发射的数据.
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(i);
                    }
                    int sleep = 300;
                    if (i % 3 == 0) {
                        sleep = 100;
                    }
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                subscriber.onCompleted();
            }
        }).throttleWithTimeout(200,TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer aLong) {
                        Log.d("sss","throttleOperation call is "+aLong);
                    }
                });
    }

    public void debounceOperation(){
        //debounce的第一种用法和 throttleWithTimeout 相同.
        //以下是第二种用法  返回Observable 如果该observable会调用onCompleted(正常结束),那么该数据会被发射.
        Observable.just(1,2,3,4,5,6,7,8).debounce(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(final Integer integer) {
                Log.d("sss","Func1 call value is "+integer);
                    return Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            if(integer%2==0 && subscriber.isUnsubscribed()){
                                subscriber.onNext("haha");
                                subscriber.onCompleted();
                            }
                        }
                    });
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("sss","debounceOperation call is "+integer);
            }
        });
    }

    public void distinctOperation(){
        // distinct 去重.有两个版本,带参数的参数意义和groupby相同,也是用来分组的.
        // distinct对重复的判断是元素是否能放入一个Set对象中.
        Observable.just(1,2,3,4,5,6,7,8).distinct(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                if(integer<3){
                    return "1";
                }else{
                    return "2";
                }
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

        Observable.just(1,2,3,3,5,6,7,8).distinct().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });
    }

    public void distinctUntilChangedOperation(){
        // distinctUntilChanged 和distinct基本相同,不同点在于,它只排除连续的重复,如果重复不连续,那么还是会输出.
        Observable.just(1,2,3,4,5,6,7,8).distinctUntilChanged(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                if(integer<3){
                    return "1";
                }else{
                    return "2";
                }
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

        Observable.just(1,2,3,3,5,6,7,8).distinctUntilChanged().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });
    }

    public void elementAtOperation(){
        //elementAt 只有指定index的数据会被发射.
        //fitst 只输出第一个,带参数版本表示返回第一个符合条件的
        //last 只发射最后一个,带参数版本表示返回最后一个符合条件的
        Observable.just(0,1,2,3,4,5,6).elementAt(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

        Observable.just(0,1,2,3,4,5,6).first().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

        Observable.just(0,1,2,3,4,5,6).first(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                if(integer>3) {
                    return true;
                }
                return false;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

        Observable.just(0,1,2,3,4,5,6).last().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });
    }

    public void blockObervableOperation(){
        // BlockingObservable 调用last first等方法会阻塞,直到接收到想要的数据后才继续运行.返回值是一个值,不再是Observable
        BlockingObservable.from(Observable.just(1,2,3,4,5,6,7)).last();
        Observable.just(1,2,3,4,5,6).toBlocking().first();
    }

    public void sampleOperation(){
        //sample  指定时间只发射最后收到的值.
        Observable.interval(100,TimeUnit.MILLISECONDS).sample(300,TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","the call value is "+ aLong);
            }
        });
    }

    public void throttleLastOpertaion(){
        //throttleLast 同 sample
        Observable.interval(100,TimeUnit.MILLISECONDS).throttleLast(300,TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","the call value is "+ aLong);
            }
        });
    }

    public void throttleFirstOperation(){
        //throttleFirst 指定时间只发射第一个收到的值.
        Observable.interval(100,TimeUnit.MILLISECONDS).throttleFirst(300,TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","the call value is "+ aLong);
            }
        });
    }


    public void combineLatestOperation(){
        //combineLatest 接受2~9个observable 或者传入一个 list, 意义和操作方法一样.
        //combineLatest 用来组合多个observable, 任何一个observable有数据过来,都会和其他observable最后接受的数据作为参数传入Func中.

        Observable<Integer> observable1=Observable.just(1,2,3,4,5,6,7,8).subscribeOn(Schedulers.newThread());
        Observable<String> observable2=Observable.just("hello","I","am","yxwang").subscribeOn(Schedulers.newThread());

        //最后一个
        Observable.combineLatest(observable1, observable2, new Func2<Integer, String, String>() {
            @Override
            public String call(Integer integer, String s) {
                return s+integer;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("sss","combineLatestOpertaion "+ s);
            }
        });
    }


    public void joinOperation(){
        //join是个看上去非常复杂的操作符,主要功能是合并两个observable的发射值,难点在于合并的规则.
        //首先需要了解几个参数 left 左值(调用join的对象)
        //右值,join第一个参数
        //左值保留时间,当右值来时,只有还被保留的左值(所有还被保留的)会和新来的右值结合,调用Func2
        //右值保留时间,当左值来时,只有还被保留的右值(所有还被保留的)会和新来的左值结合,调用Func2
        //左值会先发送.当左值来临,发现没有右值,那么左值被忽略,不会调用join参数中的任何方法,右值同理.
        //保留时间是最难以理解的地方,一般来说可以认为是直到返回的Observable调用onComplete.


        Observable<Long> observable1=Observable.interval(200,TimeUnit.MILLISECONDS).take(5);
        //Observable<Long> observable1=Observable.just(0L,1L,2L,3L,4L);
        Observable<String> observable2=Observable.just("hello","I","am","yxwang");

        observable1.join(observable2, new Func1<Long, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Long integer) {
                Log.d("sss","observable 1 "+ integer);
                return Observable.timer(1, TimeUnit.SECONDS).cast(Integer.class);
            }
        }, new Func1<String, Observable<Long>>() {
            @Override
            public Observable<Long> call(String s) {
                Log.d("sss","observable 2 "+ s);
                return Observable.timer(400, TimeUnit.MILLISECONDS);
            }
        }, new Func2<Long, String, String>() {
            @Override
            public String call(Long integer, String s) {
                return s+integer;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("sss","joinOperation "+ s);
            }
        });

//        observable2.join(observable1, new Func1<String, Observable<Long>>() {
//            @Override
//            public Observable<Long> call(String s) {
//                Log.d("sss", "observable 2 " + s);
//                return Observable.timer(400, TimeUnit.MILLISECONDS);
//            }
//        }, new Func1<Long, Observable<Integer>>() {
//            @Override
//            public Observable<Integer> call(Long integer) {
//                Log.d("sss", "observable 1 " + integer);
//                return Observable.timer(1, TimeUnit.SECONDS).cast(Integer.class);
//            }
//        }, new Func2<String, Long, String>() {
//            @Override
//            public String call(String s, Long aLong) {
//                return s+aLong;
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.d("sss","joinOperation "+ s);
//            }
//        });

    }


    public void joinGroupOperation(){
        //和join基本相同,差别在于第三个参数的Func2的参数类型.


        Observable<Long> observable1=Observable.interval(200,TimeUnit.MILLISECONDS).take(5);
        //Observable<Long> observable1=Observable.just(0L,1L,2L,3L,4L);
        Observable<String> observable2=Observable.just("hello","I","am","yxwang");

        observable1.groupJoin(observable2, new Func1<Long, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Long integer) {
                Log.d("sss", "observable 1 " + integer);
                return Observable.timer(1, TimeUnit.SECONDS).cast(Integer.class);
            }
        }, new Func1<String, Observable<Long>>() {
            @Override
            public Observable<Long> call(String s) {
                Log.d("sss", "observable 2 " + s);
                return Observable.timer(400, TimeUnit.MILLISECONDS);
            }
        }, new Func2<Long, Observable<String>, Observable<String>>() {
            @Override
            public Observable<String> call(final Long aLong, Observable<String> stringObservable) {
                return stringObservable.map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s+aLong;
                    }
                });
            }
        }).subscribe(new Action1<Observable<String>>() {
            @Override
            public void call(Observable<String> stringObservable) {
                stringObservable.subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("sss","joinGroupOperation "+s);
                    }
                });
            }
        });


    }

    public void concatOperation(){
        //concat 将多个observable合并,并且遵循严格的先后顺序.第一个没有发送完成,第二个不会发送.
        Observable.concat(Observable.interval(100,TimeUnit.MILLISECONDS).take(5),Observable.just(100L,200L,300L))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.d("sss","concatOperation "+aLong);
                    }
                });
    }


    public void concatDelayErrorOperation(){
        //concatDelayError 和concat相同,不同点在于发生错误后不立即停止,其他observable继续发送,最后才把错误传给subscribe
        List<Observable<Long>> list=new ArrayList<>();
        list.add(Observable.interval(100,TimeUnit.MILLISECONDS).take(4));
        list.add(Observable.just(5L,6L,7L,8L));
        Observable.concatDelayError(list)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long integer) {
                        Log.d("sss","mergeOperation "+integer);
                    }
                });
    }

    public void mergeOperation(){
        //merge 将多个observable合并成一个observable,数据发射顺序不可靠. 任何一个observable遇到错误后都讲停止发送,并且将
        //错误发射给subscribe
        Observable.merge(Observable.interval(100,TimeUnit.MILLISECONDS).take(4),Observable.just(5L,6L,7L,8L))
                .subscribe(new Action1<Long>() {
            @Override
            public void call(Long integer) {
                Log.d("sss","mergeOperation "+integer);
            }
        });
    }

    public void mergeDelayErrorOperation(){
        //mergeDelayError 和merge相同,不同点在于发生错误后不立即停止,其他observable继续发送,最后才把错误传给subscribe
        Observable.mergeDelayError(Observable.interval(100,TimeUnit.MILLISECONDS).take(4),Observable.just(5L,6L,7L,8L))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long integer) {
                        Log.d("sss","mergeOperation "+integer);
                    }
                });
    }

    public void startWithOperation(){
        //startWith 在当前observable数据之前插入 1.  数据  2.一个observable
        //当前observable数据发射会在新插入的observable之后!!
        Observable.just(5L,6L,7L,8L).startWith(Observable.interval(100,TimeUnit.MILLISECONDS).take(3)).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","startWithOperation "+aLong);
            }
        });
    }

    public void switchOnNextOperation(){
        //switchOnNextOperation  如果一个observable发射的数据是observable,那么,可以使用 switchOnNext方法将所有子observable发射的数据合并.
        //如果第二个子observable开发发送数据,那么前一个子observable会被停止!
        Observable<Observable<String>> observable=Observable.create(new Observable.OnSubscribe<Observable<String>>() {
            @Override
            public void call(Subscriber<? super Observable<String>> subscriber) {
                for(int i=0;i<3;i++){
                    subscriber.onNext( Observable.interval(100,TimeUnit.MILLISECONDS).take(5).map(new Func1<Long, String>() {
                        @Override
                        public String call(Long aLong) {
                            return String.valueOf(aLong);
                        }
                    }));
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        });

        Observable.switchOnNext(observable).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("sss","switchOnNextOperation "+s);
            }
        });
    }

    public void zipOperation(){
        //zip 将传入的 observable 数据 一对一匹配起来传入func2(第一个数据对第一个数据,第二个对第二个).
        //当有任意observable发射完数据,那么其他未发射的observable数据将会被舍弃
        //一下输出    0 5    1  6     2 7
        Observable<Long> observable1=Observable.interval(100,TimeUnit.MILLISECONDS).take(5);
        Observable<Long> observable2=Observable.just(5L,6L,7L);

        Observable.zip(observable1, observable2, new Func2<Long, Long, String>() {
            @Override
            public String call(Long aLong, Long aLong2) {
                return aLong+" "+aLong2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("sss","zipOperation " + s);
            }
        });
    }

    public void zipWithOperation(){
        //和 zip功能一样 不同点在于,不是静态方法.
        Observable<Long> observable1=Observable.interval(100,TimeUnit.MILLISECONDS).take(5);
        Observable<Long> observable2=Observable.just(5L,6L,7L);

        observable1.zipWith(observable2, new Func2<Long, Long, String>() {
            @Override
            public String call(Long aLong, Long aLong2) {
                return aLong+" "+aLong2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("sss","zipWithOperation " + s);
            }
        });
    }

    public void OnErrorReturnOpreation(){
        //onErrorReturn  当数据出现错误的时候不再调用subscribe的onError方法,而是继续发送一个预先设置的值之后结束
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                for(Long i=0L;i<7L;i++){
                    if(i == 5L){
                        Long k=Long.parseLong("5s");
                        subscriber.onNext(k);
                    }else {
                        subscriber.onNext(i);
                    }
                }
            }
        }).onErrorReturn(new Func1<Throwable, Long>() {
            @Override
            public Long call(Throwable throwable) {
                return 100L;
            }
        }).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss", "OnErrorReturnOpreation " + aLong);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d("sss","subscribe onError");
            }
        });
    }

    public void onErrorResumeNextOperation(){
        //onErrorResumeNext 和 onErrorReturn类似,不同点在于遇到错误后由另一个Observable替代开始发送数据
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                for(Long i=7L;i<15L;i++){
                    if(i == 13L){
                        Long k=Long.parseLong("5s");
                        subscriber.onNext(k);
                    }else {
                        subscriber.onNext(i);
                    }
                }
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends Long>>() {
            @Override
            public Observable<? extends Long> call(Throwable throwable) {
                return Observable.interval(100,TimeUnit.MILLISECONDS).take(5);
            }
        }).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss", "onErrorResumeNextOperation " + aLong);
            }
        });
    }

    public void onExceptionResumeNextOperation(){
        // onExceptionResumeNext 和 onErrorResumeNext 类似,但是只有在Throwable是Exception时会调用
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                for(Long i=7L;i<15L;i++){
                    if(i == 13L){
                        Long k=Long.parseLong("5s");
                        subscriber.onNext(k);
                    }else {
                        subscriber.onNext(i);
                    }
                }
            }
        }).onExceptionResumeNext(Observable.interval(100,TimeUnit.MILLISECONDS).take(5))
                .subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss", "onErrorResumeNextOperation " + aLong);
            }
        });
    }

    public void retryOperation(){
        //retry 如果遇到错误,那么重新发射所有数据,重试次数为参数,如果最后还是失败了,会调用onError
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                for(Long i=7L;i<15L;i++){
                    if(i == 13L){
                        Long k=Long.parseLong("5s");
                        subscriber.onNext(k);
                    }else {
                        subscriber.onNext(i);
                    }
                }
            }
        }).retry(2).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {

            }
        });
    }

    public void retryWhenOperation(){
        //retryWhen 一旦Func1返回的Observable有发送数据,那么就会retry,retry的次数,和observable发射数据数相同
        //func1只会被调用一次.
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                for(Long i=7L;i<15L;i++){
                    if(i == 13L){
                        Long k=Long.parseLong("5s");
                        subscriber.onNext(k);
                    }else {
                        subscriber.onNext(i);
                    }
                }
            }
        }).retryWhen(new Func1<Observable<? extends Throwable>, Observable<Long>>() {
            @Override
            public Observable<Long> call(Observable<? extends Throwable> observable) {
                Log.d("sss","retryWhenOperation  func called");
                return Observable.interval(1,TimeUnit.SECONDS).take(1);
            }
        }).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","retryWhenOperation  "+aLong);
            }
        });
    }

    public void delayOperation(){
        //delay延迟发射数据
        //delay 有几个重载, delay(long TimeUtil), 直接告诉等待多长时间
        //delay(Func1) 等待到返回的Observable发射第一个数据
        //delay(Func0,Func1),Func0的作用和 delaySubscribe相同,Func1的效果和 delay(Func1)相同

        Observable.just(1,2,3,4,5,6).delay(new Func1<Integer, Observable<Long>>() {
            @Override
            public Observable<Long> call(Integer integer) {
                return Observable.interval(1,TimeUnit.SECONDS).take(3);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("sss","delayOperation "+ integer);
            }
        });
    }

    public void delaySubscribeOperation(){
        //delaySubscribe 和delay效果基本相同,不同在于 delaySubscribe是延迟注册subscribe而不是延迟发射数据.
        Observable.just(1,2,3,4,5,6).delaySubscription(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return Observable.interval(1,TimeUnit.SECONDS).take(3);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("sss","delayOperation "+ integer);
            }
        });
    }

    public void doOperation(){
        //do 方法很多   doOnTerminate表示结束前(onComplete或者onError之前) doAfterTerminate 表示结束之后
        //DoOnEach --- Observable每发射一个数据的时候就会触发这个回调，不仅包括onNext还包括onError和onCompleted。
        //DoOnNext --- 只有onNext的时候才会被触发
        //DoOnError --- 只有onError发生的时候触发回调
        //DoOnComplete --- 只有onComplete发生的时候触发回调
        //DoOnSubscribe和DoOnUnSubscribe --- 在Subscriber进行订阅和反订阅的时候触发回调

        Observable.just(1,2,3,4,5).doOnTerminate(new Action0() {
            @Override
            public void call() {

            }
        });
    }

    public void materializeOperation(){
        //materialize 将 数据封装成 Notification发送
        Observable.just(1,2,3,4,5,6).materialize().subscribe(new Action1<Notification<Integer>>() {
            @Override
            public void call(Notification<Integer> integerNotification) {
                Log.d("ss","value" + integerNotification.getValue() + " type " + integerNotification.getKind());
            }
        });
    }


    public void deaterializeOperation(){
        //dematerialize materialize的反操作,但是数据会变成Object
        //
        Observable.just(1,2,3,4,5,6).materialize().dematerialize().subscribe(new Action1<Object>() {
            @Override
            public void call(Object integer) {

            }
        });
    }

    public void timeIntervalOperation(){
        //timeInterval 封装数据,添加两个数据发送之间的间隔时间,第一个数据表示从subscribe到发送第一个数据之间的时间
        Observable.interval(100,TimeUnit.MILLISECONDS).take(5).timeInterval().subscribe(new Action1<TimeInterval<Long>>() {
            @Override
            public void call(TimeInterval<Long> longTimeInterval) {
                Log.d("sss","timeIntervalOperation "+longTimeInterval.getIntervalInMilliseconds()
                        +"  "+longTimeInterval.getValue());
            }
        });
    }

    public void timeStampOperation(){
        //timeStamp 和timeInterval基本相同,不同在于不是添加时间间隔,而是直接添加该数据的发送时间
        Observable.interval(100,TimeUnit.MILLISECONDS).take(5).timestamp().subscribe(new Action1<Timestamped<Long>>() {
            @Override
            public void call(Timestamped<Long> longTimestamped) {
                Log.d("sss","timeIntervalOperation "+longTimestamped.getTimestampMillis()
                        +"  "+longTimestamped.getValue());
            }
        });
    }

    public void timeoutOpertaion(){
        //timeout有很多重载方法,大致分为以下几种(很多方面和delay类似)
        //timeout(时间),当两个数据之间的间隔大于指定时间,抛出timeout异常
        //timeout(时间,Observable) 不抛出异常,而使用observable发射数据
        //timeout(func1,..)等待到返回的Observable发射第一个数据
        //timeout(func0,func1,..) 当从subscriber到发射第一个数据超过func0,算作超时,两个数据之间间隔超过func1表示超时!
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i=0;i<10;i++){
                    try {
                        Thread.sleep((i+1)*100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).timeout(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return Observable.interval(200,TimeUnit.MILLISECONDS).take(1);
            }
        }, new Func1<Integer, Observable<Long>>() {
            @Override
            public Observable<Long> call(Integer integer) {
                return Observable.interval(200,TimeUnit.MILLISECONDS).take(3);
            }
        },Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(100);
                subscriber.onNext(200);
                subscriber.onCompleted();
            }
        })).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("sss", "timeoutOpertaion " + integer);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                Log.d("sss", "timeoutOpertaion onError  "+throwable.getMessage());
            }
        });
    }

    private class Animal {
        Subscriber<Long> subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long o) {
                Log.d("sss","Animal "+o);
            }
        };

        public Animal() {
            Observable.interval(1000, TimeUnit.MILLISECONDS)
                    .subscribe(subscriber);
        }

        public void relase() {
            subscriber.unsubscribe();
        }
    }

    public void usingOperation(){
        //using  一个很有意思的操作符,使用了using,那么这个observable并不会发送数据,subscribe几乎只是用来启动using的工作
        //using需要三个参数,第一个参数表示 用来创建一个对象(任何你需要的对象)
        //第二个参数返回一个observable表示生命周期,对象在这个observable生命周期内有效,
        //当第二个参数生成的observable生命周期结束后,会调用第三个参数,用来释放对象的一些资源.
        //当结束这个流程之后,调用using的observable才会发送第一条数据(仅发送一条将会使定时器失效).
        //如下会打印
        // animal 1
        // ....
        // animal 15
        // usingOperation 0
        //
        Observable.timer(20,TimeUnit.SECONDS).using(new Func0<Animal>() {
            @Override
            public Animal call() {
                return new Animal();
            }
        }, new Func1<Animal, Observable<Long>>() {
            @Override
            public Observable<Long> call(Animal animal) {
                return Observable.timer(15,TimeUnit.SECONDS);
            }
        }, new Action1<Animal>() {
            @Override
            public void call(Animal animal) {
                animal.relase();
            }
        }).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","usingOperation "+aLong);
            }
        });
    }

    public void allOperation(){
        //all 只有当所有发射的数据都满足 func1 的判断条件(返回ture),才会发射true,否则发射false.
        Observable.just(1,2,3,4,5).all(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                if(integer<10){
                    return true;
                }
                return false;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        });
    }

    public void ambOperation(){
        // amb 使多个observable竞争,谁先发送数据(包括omcomplete 和 onError),就采用谁来发送数据,其他observable将会被丢弃
        Observable<Long> observable1=Observable.just(100L,200L,300L,400L,500L).delay(200,TimeUnit.MILLISECONDS);
        Observable<Long> observable2=Observable.interval(100,TimeUnit.MILLISECONDS).take(4);

        Observable.amb(observable1,observable2).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("sss","ambOperation "+aLong);
            }
        });
    }

    public void containsOperation(){
        //contains 当发射的数据当中包含 指定值,才会最终发射true,否则发射false
        Observable.just(1,2,3,4,5).contains(6).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        });
    }

    public void isEmptyOperation(){
        // isEmpty 如果observable发射过数据,那么最终发射true,否则发射false
        List<String> list=new ArrayList<>();
        Observable.from(list).isEmpty().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        });
    }

    public void defaultIfEmptyOperation(){
        //defaultIfEmpty 如果原observable没有发射数据,那么发射默认值,否则发射原先的数据.
        List<String> list=new ArrayList<>();
        Observable.from(list).defaultIfEmpty("haha").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    public void sequenceEqualOperation(){
        //sequenceEqual 判断两个Observable发射的数据序列是否相同
        // （发射的数据相同，数据的序列相同，结束的状态相同），如果相同返回true，否则返回false
        //sequenceEqual 可以带第三个参数,使用自定义的方法来判断连个数据是否equal
        List<String> list1=new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        List<String> list2=new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("5");

        Observable.sequenceEqual(Observable.from(list1),Observable.from(list2)).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        });

    }

    public void countOperation(){
        //count  Count操作符用来统计源Observable发射了多少个数据，最后将数目给发射出来；
        // 如果源Observable发射错误，则会将错误直接报出来；
        // 在源Observable没有终止前，count是不会发射统计数据的。
        Observable.just(1L,2L,3L,4L,5L).count().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {

            }
        });
    }

    //CompositeSubscription 测试
    //CompositeSubscription 相当于一个集合,unsubscribe会对其中包含的所有Subscription起作用。
    public void compositeSubTest(){
        CompositeSubscription subscription = new CompositeSubscription();
        Subscription s1 = Observable.just(1L,2L,3L,4L,5L).count().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

        Subscription s2 = Observable.just(1L,2L,3L,4L,5L).count().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

        Subscription s3 = Observable.just(1L,2L,3L,4L,5L).count().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });

        subscription.add(s1);
        subscription.add(s2);
        subscription.add(s3);

        subscription.unsubscribe();
    }




}
