package com.apiit.eeashopingandroid.package_product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.apiit.eeashopingandroid.AppSingleton;
import com.apiit.eeashopingandroid.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

public class ProductDetails extends AppCompatActivity {
    TextView prodName;
    TextView prodPrice;
    TextView prodDesc;
    ImageView prodImg;
    Button addToCartBtn;
    RecyclerView comments;
    Toolbar toolbar;
    ScrollView scrollView;
    String prodId;
    String url = "http://10.0.3.2:8080/product/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        prodName = (TextView) findViewById(R.id.prodNameDetail);
        prodPrice = (TextView) findViewById(R.id.prodPriceDetail);
        prodDesc = (TextView) findViewById(R.id.prodDescriptionDetail);
        prodImg = (ImageView) findViewById(R.id.prodImgDetail);
        addToCartBtn = (Button) findViewById(R.id.addToCartBtnDetail);
        comments = (RecyclerView) findViewById(R.id.commentsDetail);
        scrollView = (ScrollView) findViewById(R.id.scrollView2);

        prodId = getIntent().getStringExtra("product");
        scrollView.smoothScrollTo(0,0);
        getProduct(prodId);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.refresh){
                    getProduct(prodId);
                }
                return false;
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        getProduct(prodId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getProduct(String pid) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url+pid,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new Gson();
                        Product product = gson.fromJson(response.toString(), Product.class);

                        setValues(product);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error fething product"+error);
                    }
                });
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void setValues(final Product prod) {
        toolbar.setTitle(prod.getpName());
        prodName.setText(prod.getpName());
        prodPrice.setText(Double.toString(prod.getpPrice()));
        prodDesc.setText(prod.getpDescription());
        Glide.with(getApplicationContext()).load(prod.getpImg()).apply(new RequestOptions().override(500,500)).centerCrop().into(prodImg);

        prodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), ViewImageActivity.class);
                i.putExtra("productImg", prod.getpImg());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);

        return true;
    }
}
