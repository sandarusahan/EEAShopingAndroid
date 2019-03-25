package com.apiit.eeashopingandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ProductDetails extends AppCompatActivity {
    TextView prodName;
    TextView prodPrice;
    TextView prodDesc;
    ImageView prodImg;
    Button addToCartBtn;
    RecyclerView comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        prodName = (TextView) findViewById(R.id.prodNameDetail);
        prodPrice = (TextView) findViewById(R.id.prodPriceDetail);
        prodDesc = (TextView) findViewById(R.id.prodDescriptionDetail);
        prodImg = (ImageView) findViewById(R.id.prodImgDetail);
        addToCartBtn = (Button) findViewById(R.id.addToCartBtnDetail);
        comments = (RecyclerView) findViewById(R.id.commentsDetail);


        Product prod = (Product) getIntent().getSerializableExtra("product");

        prodName.setText(prod.getpName());
        prodPrice.setText(Double.toString(prod.getpPrice()));
        prodDesc.setText(prod.getpDescription());
        Glide.with(getApplicationContext()).load(prod.getpImg()).apply(new RequestOptions().override(500,500)).centerCrop().into(prodImg);


    }
}
