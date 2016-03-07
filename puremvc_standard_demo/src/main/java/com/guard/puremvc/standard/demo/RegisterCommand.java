package com.guard.puremvc.standard.demo;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

/**
 * Created by yxwang on 16-3-4.
 */
public class RegisterCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        super.execute(notification);
        RegisterProxy proxy= (RegisterProxy) facade.retrieveProxy(RegisterProxy.NAME);
        User user= (User) notification.getBody();
        if(proxy!=null){
            if(proxy.checkLogin(user.mName,user.mPassword)){
                sendNotification(MainFacade.COMMAND_REGISTER_FAIL);
            }else{
                sendNotification(MainFacade.COMMAND_REGISTER_SUCCESS);
            }
        }
    }
}
