package com.guard.puremvc.standard.demo;

import org.puremvc.java.patterns.facade.Facade;

/**
 * Created by yxwang on 16-3-4.
 */
public class MainFacade extends Facade {

    public static final String COMMAND_LOGIN="login";
    public static final String COMMAND_REGISTER="register";
    public static final String COMMAND_LOGIN_SUCCESS="login_success";
    public static final String COMMAND_LOGIN_FAIL="login_fail";
    public static final String COMMAND_REGISTER_SUCCESS="register_success";
    public static final String COMMAND_REGISTER_FAIL="register_fail";

    protected static Facade instance = null;

    protected MainFacade(){
        super();
    }

    public synchronized static Facade getInstance()
    {
        if( instance == null )
            instance = new MainFacade();

        return instance;
    }

    @Override
    protected void initializeController() {
        super.initializeController();
        registerCommand(COMMAND_LOGIN, new LoginCommand());
        registerCommand(COMMAND_REGISTER,new RegisterCommand());
    }

    @Override
    protected void initializeModel() {
        super.initializeModel();
        registerProxy(new LoginProxy());
        registerProxy(new RegisterProxy());
    }

    @Override
    protected void initializeView() {
        super.initializeView();
    }
}
