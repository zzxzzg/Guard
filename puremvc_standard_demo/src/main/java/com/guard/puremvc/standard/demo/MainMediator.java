package com.guard.puremvc.standard.demo;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.mediator.Mediator;

/**
 * Created by yxwang on 16-3-4.
 */
public class MainMediator extends Mediator {
    /**
     * Constructor.
     *
     * @param mediatorName
     * @param viewComponent
     */
    public static final String NAME="MainMediator";

    private EditText mUserName;
    private EditText mPassword;
    private Button mLogin;
    private Button mRegister;

    public MainMediator(Activity viewComponent) {
        super(NAME, viewComponent);
        mUserName= (EditText) viewComponent.findViewById(R.id.username);
        mPassword= (EditText) viewComponent.findViewById(R.id.password);
        mLogin= (Button) viewComponent.findViewById(R.id.login);
        mRegister= (Button) viewComponent.findViewById(R.id.register);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(MainFacade.COMMAND_LOGIN,new User(mUserName.getText().toString(),mPassword.getText().toString()));
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(MainFacade.COMMAND_REGISTER,new User(mUserName.getText().toString(),mPassword.getText().toString()));
            }
        });
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{MainFacade.COMMAND_LOGIN_SUCCESS,MainFacade.COMMAND_REGISTER_SUCCESS,MainFacade.COMMAND_LOGIN_FAIL,MainFacade.COMMAND_REGISTER_FAIL};
    }

    @Override
    public void handleNotification(INotification notification) {
        super.handleNotification(notification);
        if(notification.getName().equals(MainFacade.COMMAND_LOGIN_FAIL)){
            Toast.makeText((Activity)getViewComponent(),"login fail",Toast.LENGTH_SHORT).show();
        }else if(notification.getName().equals(MainFacade.COMMAND_LOGIN_SUCCESS)){
            Toast.makeText((Activity)getViewComponent(),"login success",Toast.LENGTH_SHORT).show();
        }else if(notification.getName().equals(MainFacade.COMMAND_REGISTER_FAIL)){
            Toast.makeText((Activity)getViewComponent(),"register fail",Toast.LENGTH_SHORT).show();
        }else if(notification.getName().equals(MainFacade.COMMAND_REGISTER_SUCCESS)){
            Toast.makeText((Activity)getViewComponent(),"register success",Toast.LENGTH_SHORT).show();
        }
    }
}
