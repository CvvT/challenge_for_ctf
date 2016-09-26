package com.ctf.crackme1;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CwT on 16/3/19.
 */
public class StringHolder {

    private static byte[][] desedStrings = new byte[][]{
            // "com.ctf.crackme1.util"
            new byte[] {5, -87, 99, 90, 11, 5, -127, -5, 78, 28, 1, 12, 57, -40, 124, 27, -78, 3, -35, -128, 31, 10, 93, -127},
            // "android.os.Debug"
            new byte[] {37, -90, 42, 79, 10, 58, -121, 58, -22, -122, -98, -1, 55, 104, 109, -85, 62, -10, 125, -34, -110, 119, -125, -93},
            // isDebuggerConnected
            new byte[] {59, 25, -85, 111, -106, -63, -82, -57, 98, 122, -95, -45, -36, 33, 81, 32, 6, -29, 80, -4, 58, -37, -44, -70},
            // b
            new byte[] {49, 117, -127, -103, 28, 105, -30, 87},
            // d
            new byte[] {-54, 82, 121, 60, 18, 26, -27, -117},
            // e
            new byte[] {120, 35, 90, -107, 18, -88, -110, 115},
    };

    static Map<Integer, String> pools = init();

    private static Map<Integer, String> init() {
        return new HashMap<>(desedStrings.length);
    }

    public static String get(int index, String key) {
        if (index >= desedStrings.length)
            return null;
        if (!pools.containsKey(index)) {
            pools.put(index, DESCrypt.decrypt(desedStrings[index], key));
        }

        return pools.get(index);
    }

//    public static void start(String key) {
//        test("com.ctf.crackme1.util", key);
//        test("android.os.Debug", key);
//        test("isDebuggerConnected", key);
//        test("b", key);
//        test("d", key);
//        test("e", key);
//    }

//    public static void end(String key) {
//        for (int i = 0; i < desedStrings.length; i++) {
//            Log.d("cc", get(i, key));
//        }
//    }
//
//    public static void test(String str, String key) {
//        byte[] c = DESCrypt.encrypt(str, key);
//        for (byte one : c) {
//            Log.d("cc", Integer.toHexString(one));
//        }
//        Log.d("cc", str + "end!!!!!!!!!!!!");
//    }
}
