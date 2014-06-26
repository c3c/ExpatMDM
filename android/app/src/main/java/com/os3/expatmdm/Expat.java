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
    private static Context Ctx;

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
                } catch(Exception e){Log.e("JsonError", e.getMessage());}
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
