package zzxzzg.com.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

/**
 * Created by apple on 16/4/9.
 */
public class MyAidlServer extends Service{

    private RemoteCallbackList<ITaskCallback> mCallbacks=new RemoteCallbackList<>();

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
            mCallbacks.register(callback);
        }

        @Override
        public void unregisterCallback(ITaskCallback callback) throws RemoteException {
            mCallbacks.unregister(callback);
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public void callback(){
        int count=mCallbacks.beginBroadcast();
        try {
        for(int i=0;i<count;i++){
                mCallbacks.getBroadcastItem(i).actionPerformed(1);
        }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mCallbacks.finishBroadcast();
    }
}
