package com.ctf.crackme1;

import android.app.Application;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by CwT on 16/3/19.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (c()) {
            Log.d("cc", "is debugger connected");
            q();
        } else if (getApplicationInfo().FLAG_DEBUGGABLE == 0){
            Log.d("cc", "debuggable!!");
            q();
        } else {
            Log.d("cc", "All Right");
        }
    }

    private void q() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 10000);
    }

    public boolean c() {
        try {
            Class clazz = Class.forName("android.os.Debug");
            Method method = clazz.getDeclaredMethod("isDebuggerConnected");
            return (Boolean)method.invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}
