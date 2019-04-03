package com.apiit.eeashopingandroid.package_cart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.apiit.eeashopingandroid.AppSingleton;
import com.apiit.eeashopingandroid.R;
import com.google.gson.Gson;

import org.json.JSONArray;

public class CartActivity extends AppCompatActivity {

    private ListView cartItemsList;
    private Button checkoutBtn;
    private TextView totalCost;
    private TextView emptyText;

    boolean isEmpty = false;

    String url = "http://10.0.3.2:8080/cart/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemsList = findViewById(R.id.cart_items_list);
        checkoutBtn = findViewById(R.id.btn_checkout);
        totalCost = findViewById(R.id.total_cost);
        emptyText = findViewById(R.id.empty_text);

        cartItemsList.setEmptyView(emptyText);

        getCartItems();


    }

    @Override
    protected void onStart() {
        super.onStart();
        getCartItems();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartItems();

    }

    public void getCartItems() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "all", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        CartItem[] cartItems = gson.fromJson(response.toString(), CartItem[].class);
                        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, R.layout.cart_item_card, cartItems);
//                        ArrayAdapter<CartItem> cartAdapter = new ArrayAdapter<CartItem>(CartActivity.this, R.layout.product_card, cartItems);
                        cartItemsList.setAdapter(cartAdapter);
                        if (cartAdapter.getCount() == 0)
                            isEmpty = true;

                        if(isEmpty)
                        {
                            checkoutBtn.setVisibility(View.GONE);
                            totalCost.setVisibility(View.GONE);
                        }else {
                            checkoutBtn.setVisibility(View.VISIBLE);
                            totalCost.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error while fetching cart items" + error);
                    }
                });

        AppSingleton.getInstance(CartActivity.this).addToRequestQueue(jsonArrayRequest);
    }
}
