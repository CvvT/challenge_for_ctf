package com.ctf.crackme1;

import java.lang.reflect.Method;

/**
 * Created by CwT on 16/3/19.
 */
public class util {

    public static void a(String key) throws Exception {
        Class clazz = Class.forName(StringHolder.get(1, key));
        Method method = clazz.getDeclaredMethod(StringHolder.get(2, key));
        if ((boolean)method.invoke(null))
            System.exit(0);
    }

    public void b(byte[] str) throws Exception {
        for (int i = 0; i < str.length; i++) {
            str[i] += 0x3a;
            if (i != 0)
                str[i] += str[i-1];
            else
                str[i] += str[str.length-1];
        }
    }

    public void d(byte[] str) throws Exception {
        for (int i = 0; i < str.length; i++) {
            str[i] ^= 0x96;
            if (i != 0) {
                str[i] ^= str[i-1];
            }else {
                str[i] ^= str[str.length-1];
            }
        }
    }

    public void e(byte[] str) throws Exception {
        for (int i = 0; i < str.length; i++) {
            str[i] = (byte) (((str[i] >> 4) & 0xf) + ((str[i] & 0xf) << 4));
        }
    }

    public static void c(byte[] str, String key) throws Exception {
        Class clazz = Class.forName(StringHolder.get(0, key));
        Method method = clazz.getDeclaredMethod(StringHolder.get(3, key), byte[].class);
        Object obj = clazz.newInstance();
        method.invoke(obj, str);
    }

    public static void f(byte[] str, String key) throws Exception {
        Class clazz = Class.forName(StringHolder.get(0, key));
        Method method = clazz.getDeclaredMethod(StringHolder.get(4, key), byte[].class);
        Object obj = clazz.newInstance();
        method.invoke(obj, str);
    }

    public static void g(byte[] str, String key) throws Exception {
        Class clazz = Class.forName(StringHolder.get(0, key));
        Method method = clazz.getDeclaredMethod(StringHolder.get(5, key), byte[].class);
        Object obj = clazz.newInstance();
        method.invoke(obj, str);
    }
}
