package com.guard.dagger2demo.demo3;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by yxwang on 16-4-14.
 */
public class Name {
    String mFirstName;
    String mLastName;
    String mMiddleName;

    public Name(String firstName,String lastName){
        mFirstName=firstName;
        mLastName=lastName;
    }

    public Name(String firstName,String lastName,String middleName){
        mFirstName=firstName;
        mLastName=lastName;
        mMiddleName=middleName;
    }

    public void printName(){
        if(mMiddleName==null){
            Log.d("sss",mFirstName+" "+mLastName);
        }else{
            Log.d("sss",mFirstName+" "+mMiddleName+" "+mLastName);
        }
    }
}
