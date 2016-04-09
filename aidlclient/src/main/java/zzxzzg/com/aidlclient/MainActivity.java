package zzxzzg.com.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zzxzzg.com.server.IMyAidlInterface;
import zzxzzg.com.server.ITaskCallback;

public class MainActivity extends AppCompatActivity {

    private IMyAidlInterface mIMyAidlInterface;
    private ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMyAidlInterface=IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean b=bindService(new Intent("zzxzzg.com.server.IMyAidlInterface"), mConnection, Context.BIND_AUTO_CREATE);
        try {
            mIMyAidlInterface.registerCallback(new ITaskCallback() {
                @Override
                public void actionPerformed(int actionId) throws RemoteException {

                }
    
                @Override
                public IBinder asBinder() {
                    return null;
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
