package com.guard.databindingdemo;

/**
 * Created by yxwang on 16-3-14.
 */
public class User {
    private  String firstName;
    private  String lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }

    public boolean isFriend(){
        return true;
    }
}
