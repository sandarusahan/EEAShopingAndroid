package com.apiit.eeashopingandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.apiit.eeashopingandroid.package_product.Product;
import com.google.gson.Gson;

import org.json.JSONArray;

public class CategoryViewActivity extends AppCompatActivity {

    ListView product_list;
    Product products[];
    String url = "http://10.0.3.2:8080/product/public/category/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);

        product_list = findViewById(R.id.product_list);

        Intent intent = getIntent();
        int catId = intent.getIntExtra("cat_id", -1);

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + catId, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Gson gson = new Gson();
//                        products = gson.fromJson(response.toString(), Product[].class);
//                        ProductAdapter productAdapter = new ProductAdapter(products, CategoryViewActivity.this);
//
//                        product_list.setAdapter(productAdapter);
//                        categoryListAdapter.notifyDataSetChanged();
//                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Category category = (Category) listView.getItemAtPosition(position);
//                                Intent intent = new Intent(CategoryListActivity.this, CategoryViewActivity.class);
//                                intent.putExtra("cat_id", category.id);
//                                startActivity(intent);
//                            }
//                        });
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });

    }
}
