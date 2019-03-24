package com.apiit.eeashopingandroid;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;


public class ProductApi {

    Context context;
    Product[] productsArr;

    public ProductApi(Context context) {
        this.context = context;
//        getProducts();
    }

    RequestQueue requestQueue;
//    Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
//    Network network = new BasicNetwork(new HurlStack());



//    public void getProducts() {
//
//        requestQueue = AppSingleton.getInstance(this.context).getRequestQueue();
//
//        JsonArrayRequest objectRequest = new JsonArrayRequest(
//                Request.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Product[] products;
//                        Gson gson = new Gson();
//                        products = gson.fromJson(response.toString(), Product[].class);
//                        setProductArr(products);
//                        ProductAdapter productAdapter = new ProductAdapter(products, context);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        System.out.println(error);
//                    }
//                }
//        );
//
//        AppSingleton.getInstance(context).addToRequestQueue(objectRequest);
//
//    }

    public void setProductArr(Product[] prods){
        this.productsArr = prods;
    }

    public Product[] getProdArr(){
        return productsArr;
    }

}
