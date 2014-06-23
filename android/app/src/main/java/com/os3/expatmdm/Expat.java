package com.os3.expatmdm;

// does some background work

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.HashMap;

public class Expat {
    public static void FetchPatches(HashMap<String, String> gather) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://dashboard.heroku.com/", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
            }
        });
    }
}
