package com.apiit.eeashopingandroid;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppSingleton {
    private static AppSingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private AppSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized AppSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new AppSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <Product> void addToRequestQueue(Request<Product> req) {
        getRequestQueue().add(req);
    }



}
