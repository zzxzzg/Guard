// IMyAidlInterface.aidl
package zzxzzg.com.server;

// Declare any non-default types here with import statements
import zzxzzg.com.server.ITaskCallback;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void registerCallback(ITaskCallback callback);
    void unregisterCallback(ITaskCallback callback);
}
