package com.apiit.eeashopingandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductDetails extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        textView = (TextView) findViewById(R.id.textView2);

        String prodKey = getIntent().getStringExtra("product");

        textView.setText(prodKey);
    }
}
