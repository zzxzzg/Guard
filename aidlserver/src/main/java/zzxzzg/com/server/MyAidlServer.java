package zzxzzg.com.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by apple on 16/4/9.
 */
public class MyAidlServer extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return new MyAidlServerImpl();
    }

    public class MyAidlServerImpl extends IMyAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void registerCallback(ITaskCallback callback) throws RemoteException {

        }

        @Override
        public void unregisterCallback(ITaskCallback callback) throws RemoteException {

        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }
}
