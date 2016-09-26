package com.ctf.crackme1;

import android.content.Context;
import android.util.Log;

import java.security.MessageDigest;

/**
 * Created by CwT on 16/3/19.
 */
public class Check {

    static {
        System.loadLibrary("crackme");
    }

    private static native boolean check(byte[] bytes);

    public static boolean check(String password, String key) throws Exception {
        byte[] c = password.getBytes();
        byte[] ans = {0, 1, 2, 3, 4};
        util.a(key);
        util.c(c, key);
        util.f(c, key);
        util.g(c, key);
        //后期通过脚本展开c、f、g（inline）三个函数，并随机调用1000次来增加难度
        return check(c);
//        for (byte one : c)
//            Log.d("res", Integer.toHexString(one));
//        return false;
    }

//    public static boolean check(Context context) {
//        try {
//            byte[] toByteArray = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray();
//            return a(toByteArray).equals(context.getString(R.string.authkey));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public static String a(byte[] bArr) {
        String stringBuffer;
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            byte[] digest = instance.digest();
            StringBuffer stringBuffer2 = new StringBuffer("");
            for (int i : digest) {
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    stringBuffer2.append("0");
                }
                stringBuffer2.append(Integer.toHexString(i));
            }
            stringBuffer = stringBuffer2.toString();
            try {
                Log.v("MD5", stringBuffer);
            } catch (Exception e2) {
                e2.printStackTrace();
                return stringBuffer;
            }
        } catch (Exception e3) {
            stringBuffer = "";
            e3.printStackTrace();
            return stringBuffer;
        }
        return stringBuffer;
    }

    public static String b(Context context) {
        String str = null;
        try {
            byte[] toByteArray = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray();
            if (toByteArray.length != 0) {
                str = a(toByteArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;     //37f531665ef482c4e1964fb56973a9de
    }
}
