//package com.guard.puremvc.standard.demo;
//
//import org.puremvc.java.interfaces.INotification;
//import org.puremvc.java.patterns.command.SimpleCommand;
//
///**
// * Created by yxwang on 16-3-4.
// */
//public class LoginCommand extends SimpleCommand {
//    @Override
//    public void execute(INotification notification) {
//        super.execute(notification);
//        LoginProxy proxy= (LoginProxy) facade.retrieveProxy(LoginProxy.NAME);
//        User user= (User) notification.getBody();
//        if(proxy!=null){
//            if(proxy.checkLogin(user.mName,user.mPassword)){
//                sendNotification(MainFacade.COMMAND_LOGIN_SUCCESS);
//            }else{
//                sendNotification(MainFacade.COMMAND_LOGIN_FAIL);
//            }
//        }
//    }
//}
