package com.guard.rxjava.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity {

    Subscriber subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Test3 test3=new Test3();
        test3.collectOperation();


//        requestTimerPingHost();

//        Test1 test=new Test1();
//        //test.test();
//
//        //test.test3();
//
//        test.test4();

//        Test2 test2=new Test2();
//        test2.test3();

//        Test3 test3=new Test3();
//        test3.test2();
//
//        Test4 test4=new Test4();
//        test4.test();
    }

//    public void requestTimerPingHost() {
//        Observable
//                .interval(6, TimeUnit.SECONDS, Schedulers.io())
//                .map(new Func1<Long, Boolean>() {
//                    @Override
//                    public Boolean call(Long aLong) {
//                        return pingHost();
//                    }
//                })
//                .filter(new Func1<Boolean, Boolean>() {
//                    @Override
//                    public Boolean call(Boolean aBoolean) {
//                        return aBoolean == Boolean.TRUE;
//                    }
//                })
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        //cancelTimerPingHost();
//                    }
//                });
//    }
//
//    private boolean pingHost() {
//        boolean result = false;
//
////		try {
////			Process p = Runtime.getRuntime().exec("ping -c 1 -w 5 " + "www.baidu.com");
////			int status = p.waitFor();
////			if (status == 0) {
////				result = true;
////			} else {
////				result = false;
////			}
////		} catch (IOException e) {
////			e.printStackTrace();
////		} catch (InterruptedException e) {
////			e.printStackTrace();
////		}
//        InputStream input = null;
//        BufferedReader in;
//        StringBuffer stringBuffer;
//        try {
//            String ip = "www.baidu.com";//之所以写百度是因为百度比较稳定，一般不会出现问题，也可以ping自己的服务器
//            Process p = Runtime.getRuntime().exec("ping -c 3 -w 5 " + ip);// ping3次
//            // 读取ping的内容
//            input = p.getInputStream();
//            in = new BufferedReader(new InputStreamReader(input));
//            stringBuffer = new StringBuffer();
//            String content = "";
//            while ((content = in.readLine()) != null) {
//                stringBuffer.append(content);
//            }
//            // PING的状态
//            int status = p.waitFor();  //status 为 0 ，ping成功，即为可以对外访问；为2则失败，即联网但不可以上网
//            if (status == 0) {
//                Log.d("TAG", "pingHost: ——>" + stringBuffer.toString());
//                result = true;
//            } else {
//                result = false;
//            }
//        } catch (IOException e) {
//        } catch (InterruptedException e) {
//        } finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//        Log.d("TAG", "pingHost: ———>" + result);
//        return result;
//    }
//
//    public void cancelTimerPingHost() {
//        Log.d("TAG", "cancelTimerPingHost: ———>");
//        if (!subscription.isUnsubscribed()) {
//            subscription.unsubscribe();
//        }
//    }
}
