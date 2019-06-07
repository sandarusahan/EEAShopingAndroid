package com.apiit.eeashopingandroid.package_checkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apiit.eeashopingandroid.OrderItem;
import com.apiit.eeashopingandroid.R;
import com.apiit.eeashopingandroid.SalesOrder;
import com.apiit.eeashopingandroid.package_cart.CartItem;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    TextView TotalValue;
    EditText cardNumber;
    EditText cardHolderName;
    EditText address;
    EditText expiryDate;
    Button paySecureBtn;

    final String urlProductOrder = "http://10.0.3.2:8080/productOrder/add";

    List<CartItem> carts;
    double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        TotalValue = (TextView) findViewById(R.id.totalValue);
        paySecureBtn = (Button) findViewById(R.id.payBtn);
        cardNumber = findViewById(R.id.cardNumber);
        cardHolderName= findViewById(R.id.cardHolderName);
        address= findViewById(R.id.address);
        expiryDate= findViewById(R.id.expiryDate);

        Bundle bundle = getIntent().getExtras();
        carts = (ArrayList<CartItem>) bundle.getSerializable("cartArr");
        total = calTotal(carts);
        TotalValue.setText("Rs. "+Double.toString(total));



        paySecureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<OrderItem> productOrderItems = new ArrayList<OrderItem>();
                final SalesOrder salesOrder = new SalesOrder();
                salesOrder.setAddress(address.getText().toString());
                salesOrder.setTotalAmount(total);
                salesOrder.setUserId(1); // user hardcoded to 1 .......................................................

                for(CartItem cart:carts){
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cart.getProduct());
                    orderItem.setAmount(cart.getAmount());
                    productOrderItems.add(orderItem);
                }

                salesOrder.setOrderItems(productOrderItems);

                Gson gson = new Gson();
                String Jsonorder = gson.toJson(salesOrder);

                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(Jsonorder);

                JSONObject orderObject = null;
                try{
                    orderObject = new JSONObject(json.toString());
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(CheckoutActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlProductOrder, orderObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println(error.toString());
                            }
                        }

                ){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String, String> cartMap = new HashMap<>();
                        cartMap.put("userId", "1");
                        cartMap.put("address", salesOrder.getAddress());
                        cartMap.put("totalAmount", Double.toString(salesOrder.getTotalAmount()));
                        cartMap.put("productOrderItem", salesOrder.getOrderItems().toString());

                        return cartMap;
                    }
                };

                queue.add(jsonObjectRequest);


            }
        });

    }

    public double calTotal(List<CartItem> cartArr){
        double total = 0;
        for(CartItem cart:cartArr){
            total += (cart.getProduct().getpPrice() * cart.getAmount());
        }

        return total;
    }


}
