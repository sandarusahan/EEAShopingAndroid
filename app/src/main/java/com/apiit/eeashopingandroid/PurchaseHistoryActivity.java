package com.apiit.eeashopingandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;

public class PurchaseHistoryActivity extends AppCompatActivity {

    private ListView purchase_list;
    String url = "http://10.0.3.2:8080/salesOrder/user/";

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        purchase_list = findViewById(R.id.puchases_list);
        session = new Session(this);
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url+session.getUserEmail(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        SalesOrder[] salesOrders;
                        Gson gson = new Gson();
                        salesOrders = gson.fromJson(response.toString(), SalesOrder[].class);

                        PurchaseHistoryAdapter purchaseHistoryAdapter = new PurchaseHistoryAdapter(PurchaseHistoryActivity.this,R.layout.purchase_history_card, salesOrders );
                        purchase_list.setAdapter(purchaseHistoryAdapter);
                        purchaseHistoryAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error while fetching products "+error);

                    }
                }
        );



        AppSingleton.getInstance(PurchaseHistoryActivity.this).addToRequestQueue(objectRequest);
    }
}
