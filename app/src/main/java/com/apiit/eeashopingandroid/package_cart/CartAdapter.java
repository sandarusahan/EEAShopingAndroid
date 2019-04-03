package com.apiit.eeashopingandroid.package_cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.apiit.eeashopingandroid.AppSingleton;
import com.apiit.eeashopingandroid.package_product.Product;
import com.apiit.eeashopingandroid.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

public class CartAdapter extends ArrayAdapter<CartItem> {

    Context context;
    CartItem[] cartItems;
    int resourceLayout;
    String url = "http://10.0.3.2:8080/product/";

    public CartAdapter(Context context, int resource, CartItem[] cartItems) {
        super(context, resource, cartItems);
        this.context = context;
        this.cartItems = cartItems;
        this.resourceLayout = resource;
    }



    private class CartItemHolder {
        TextView prodName;
        TextView prodPrice;
        TextView prodAddupPrice;
        ImageView prodPic;
        Button plusBtn;
        Button minusBtn;
        Button removeItemBtn;

        int itemCountHad;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CartItem cartItem = getItem(position);

        final CartItemHolder cartItemHolder;

        final View view;



        if(convertView == null) {

            cartItemHolder = new CartItemHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resourceLayout, parent, false);
            cartItemHolder.prodName = convertView.findViewById(R.id.prod_name_cart);
            cartItemHolder.prodPrice = convertView.findViewById(R.id.prod_price_cart);
            cartItemHolder.prodAddupPrice = convertView.findViewById(R.id.prod_addup_price_cart);
            cartItemHolder.prodPic = convertView.findViewById(R.id.prod_img_cart);
            cartItemHolder.plusBtn = convertView.findViewById(R.id.btn_plus_cart);
            cartItemHolder.minusBtn = convertView.findViewById(R.id.btn_minus_cart);
            cartItemHolder.removeItemBtn = convertView.findViewById(R.id.btn_remove_from_cart);

            view = convertView;

            convertView.setTag(cartItemHolder);
        }
        else {
            cartItemHolder = (CartItemHolder) convertView.getTag();
            view = convertView;
        }
        cartItemHolder.itemCountHad = cartItem.getAmount();

        getProduct(cartItem.getPid(), cartItemHolder);
        cartItemHolder.prodName.setText(cartItem.getName()+" "+"("+cartItemHolder.itemCountHad+")");
        cartItemHolder.prodPrice.setText(Double.toString(cartItem.getPrice()));
        cartItemHolder.prodAddupPrice.setText(Double.toString(cartItem.getPrice() * cartItem.getAmount()));

        cartItemHolder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    cartItemHolder.itemCountHad++;
                    cartItemHolder.prodName.setText(cartItem.getName() + " " + "(" + cartItemHolder.itemCountHad + ")");
                    cartItemHolder.prodAddupPrice.setText(Double.toString(cartItem.getPrice() * cartItemHolder.itemCountHad));

            }
        });
        cartItemHolder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItemHolder.itemCountHad > 0) {
                    cartItemHolder.itemCountHad--;
                    cartItemHolder.prodName.setText(cartItem.getName() + " " + "(" + cartItemHolder.itemCountHad + ")");
                    cartItemHolder.prodAddupPrice.setText(Double.toString(cartItem.getPrice() * cartItemHolder.itemCountHad));
                }
            }
        });

        cartItemHolder.removeItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(cartItemHolder.itemCountHad+" to checkout");
            }
        });

        return view;
    }

    public void getProduct(String pid, final CartItemHolder cartItemHolder) {
        RequestQueue requestQueue = AppSingleton.getInstance(context).getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url+pid,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    Gson gson = new Gson();
                    Product product = gson.fromJson(response.toString(), Product.class);
                    Glide.with(context).load(product.getpImg()).apply(new RequestOptions().override(500,500)).centerCrop().into(cartItemHolder.prodPic);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error while fetching product"+error);
                    }
                });
        AppSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
