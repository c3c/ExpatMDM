package com.os3.expatmdm;

// does some background work

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Disclaimer: I haven't touched Java in a loooooooooong time

public class Expat {
    private final static String URL_BASE = "http://expatmdm.herokuapp.com/";
    private final static String URL_FILES = URL_BASE + "files/";

    private static AsyncHttpClient ExpatClient = new AsyncHttpClient();
    public static List<Map<String,String>>
            KernelExploits = new ArrayList<>(),
            RootExploits = new ArrayList<>(),
            Patches = new ArrayList<>();
    public static Context Ctx;

    public static void Expat() {
        KernelExploits.clear();
        RootExploits.clear();
        Patches.clear();
    }

    public static void SetContext(Context ctx) {
        Ctx = ctx;
        File file = new File(ctx.getFilesDir(), "device.db");

        ExpatClient.get("http://expatmdm.herokuapp.com/files/device.db", new FileAsyncHttpResponseHandler(file) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, File response) {
                Log.d("DeviceDBDown", "Downloaded device.db successfully.");
            }
        });
    }

    public static void FetchPatches(HashMap<String, String> gather) {
        Expat();
        RequestParams params = new RequestParams(gather);

        ExpatClient.post("http://expatmdm.herokuapp.com/enroll", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray arr) {
                Log.d("ResponseString", arr.toString());

                try {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Map<String, String> map = (Map)JsonHelper.toMap(obj);
                        populate(map);
                    }
                } catch(Exception e){Log.e("JsonError", e.getMessage());}
            }

            public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable throwable) {
                Log.d("DEMO", "Setting up PoC data");
                // only for demo purposes on HTC One S

                Map<String, String> map = new HashMap<>();
                map.put("updatedAt", "2014-06-30T19:39:51.000Z");
                map.put("patch_entry", "nativePat1");
                map.put("affects", "|3.0 RC4|3.0 RC1|3.0 RC5|3.0 RC2|3.0 RC6|3.0 RC3|3.0 RC7|3.0.1|3.0.2|3.0.3|3.0.4|3.0.5|3.0.6|3.0.7|3.0.8|3.0.9|3.0.10|3.0.11|3.0.12|3.0.13|3.0.14|3.0.15|3.0.16|3.0.17|3.0.18|3.0.19|3.0.20|3.0.21|3.0.22|3.0.23|3.0.24|3.0.25|3.0.26|3.0.27|3.0.28|3.0.29|3.0.30|3.0.31|3.0.32|3.0.33|3.0.34|3.0.35|3.0.36|3.0.37|3.0.38|3.0.39|3.0.40|3.0.41|3.0.42|3.0.43|3.0.44|3.0.45|3.0.46|3.0.47|3.0.48|3.0.49|3.0.50|3.0.51|3.0.52|3.0.53|3.0.54|3.0.55|3.0.56|3.0.57|3.0.58|3.0.59|3.0.60|3.0.61|3.0.62|3.0.63|3.0.64|3.0.65|3.0.66|3.0.67|3.0.68|3.1 RC3|3.1 RC4|3.1|3.1 RC1|3.1 RC2|3.1.1|3.1.2|3.1.3|3.1.4|3.1.5|3.1.6|3.1.7|3.1.8|3.1.9|3.1.10|3.2 RC7|3.2 RC2|3.2 RC3|3.2 RC4|3.2 RC5|3.2 RC6|3.2|3.2.1|3.2.2|3.2.3|3.2.4|3.2.5|3.2.6|3.2.7|3.2.8|3.2.9|3.2.10|3.2.11|3.2.12|3.2.13|3.2.14|3.2.15|3.2.16|3.2.17|3.2.18|3.2.19|3.2.20|3.2.21|3.2.22|3.2.23|3.2.24|3.2.25|3.2.26|3.2.27|3.2.28|3.2.29|3.2.30|3.3 RC6|3.3 RC1|3.3 RC7|3.3 RC2|3.3 RC3|3.3 RC4|3.3 RC5|3.3|3.3.1|3.3.2|3.3.3|3.3.4|3.3.5|3.3.6|3.3.7|3.3.8|3.4 RC6|3.4 RC1|3.4 RC7|3.4 RC2|3.4 RC3|3.4 RC4|3.4 RC5|3.4|3.4.1|3.4.2|3.4.3|3.4.4|3.4.5|3.4.6|3.4.7|3.4.8|3.4.9|3.4.10|3.4.11|3.4.12|3.4.13|3.4.14|3.4.15|3.4.16|3.4.17|3.4.18|3.4.19|3.4.20|3.4.21|3.4.22|3.4.23|3.4.24|3.4.25|3.4.26|3.4.27|3.4.28|3.4.29|3.4.30|3.4.31|3.4.32|3.5.1|3.5.2|3.5.3|3.5.4|");
                map.put("createdAt", "2014-06-30T19:39:51.000Z");
                map.put("description", "get_user and put_user kernel functions on ARM vulnerability");
                map.put("exploit_entry", "nativeExp1");
                map.put("target", "kernel");
                map.put("type", "exec");
                map.put("CVE", "CVE-2013-6282");
                map.put("patch", "pat_cve_2013_6282.so");
                map.put("exploit", "exp_cve_2013_6282.so");

                populate(map);
            }

            private void populate(Map<String, String> map) {
                String  exploit = map.get("exploit"),
                        patch = map.get("patch"),
                        target = map.get("target"),
                        type = map.get("type");

                // First, assemble kernel-level exploits
                if (!TextUtils.isEmpty(type) && type.equals("exec") && !TextUtils.isEmpty(target) && target.equals("kernel") && !TextUtils.isEmpty(exploit)) {
                    KernelExploits.add(map);
                }

                // Next, get root-level exploits (runtime)
                if (!TextUtils.isEmpty(type) && type.equals("exec") && !TextUtils.isEmpty(target) && (target.equals("runtime") || target.equals("root")) && !TextUtils.isEmpty(exploit)) {
                    RootExploits.add(map);
                }

                // Download exploits for both
                if (!TextUtils.isEmpty(exploit)) {
                    ExpatClient.get(URL_FILES + exploit, new FileAsyncHttpResponseHandler(new File(Ctx.getFilesDir(), exploit)));
                }

                // Finally, assemble all patches
                if (!TextUtils.isEmpty(patch)) {
                    Patches.add(map);
                    ExpatClient.get(URL_FILES + patch, new FileAsyncHttpResponseHandler(new File(Ctx.getFilesDir(), patch)));
                }
            }
        });
    }

 //   public native int nativeExpPutUser();

    // Only Kernel exploiting has been implemented at this very moment.
    public static boolean Exploit() {
        // preload printf_to_logcat library ; doesn't seem to work :(( - possibly statically linked libc in implementation?
        //System.loadLibrary("printf");

        try {
            for (Map<String, String> map : KernelExploits) {
                // Is there another way that is a bit more fault tolerant?
                System.load(new File(Ctx.getFilesDir(), map.get("exploit")).getAbsolutePath());

                // If it has an entry point defined, call it, and check its return value.
                // It would be better to have the SO register its functions with the Java app,
                // Then again, that would require an entry point as well, see JNI.java
                String entry = map.get("exploit_entry");
                if (!TextUtils.isEmpty(entry)) {
                    try {
                        Method entryMethod = JNI.class.getMethod(entry);
                        int ret = (int) entryMethod.invoke(new JNI());
                        if (ret != 0) {
                            // are you absolutely sure? try again... (yeah this shouldn't be here, remove in due time)
                            ret = (int) entryMethod.invoke(new JNI());
                            if (ret != 0) {
                                Log.e("Exploit", "Exploit failed. Moving on :)");
                            }
                        } else {
                            return true;
                        }
                    } catch (Exception e){
                        Log.e("Exploit", "Native library error: " + e.getMessage());
                    }
                }
            }
        }
        catch (Exception e) {
            Log.e("Exploit", "Something has gone wrong during the exploiting step... " + e.getMessage());
        }

        return false;
    }

    public static boolean Patch() {
        try {
            for (Map<String, String> map : Patches) {
                System.load(new File(Ctx.getFilesDir(), map.get("patch")).getAbsolutePath());

                String entry = map.get("patch_entry");
                if (!TextUtils.isEmpty(entry)) {
                    try {
                        Method entryMethod = JNI.class.getMethod(entry);
                        int ret = (int) entryMethod.invoke(new JNI());
                        if (ret != 0) {
                            // are you absolutely sure? try again... (yeah this shouldn't be here, remove in due time)
                            ret = (int) entryMethod.invoke(new JNI());
                            if (ret != 0) {
                                Log.e("Patch", "Patching failed. Moving on :)");
                                return false; // this should definitely not be here once we have multiple patches
                            }
                        }
                    } catch (Exception e){
                        Log.e("Patch", "Native library error: " + e.getMessage());
                    }
                }
            }

        }
        catch (Exception e) {
            Log.e("Patch", "Something has gone wrong during the patching step... " + e.getMessage());
            return false;
        }

        return true;
    }
}
