package com.apiit.eeashopingandroid.package_cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.apiit.eeashopingandroid.AppSingleton;
import com.apiit.eeashopingandroid.package_product.Product;
import com.apiit.eeashopingandroid.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemHolder> {

    Context context;
    List<CartItem> cartItems;
    int resourceLayout;
    String url = "http://10.0.3.2:8080/";
    CartItem cartItem;

    public CartAdapter(Context context, int resource, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
        this.resourceLayout = resource;
    }



    public class CartItemHolder extends RecyclerView.ViewHolder {
        TextView prodName;
        TextView prodPrice;
        TextView prodAddupPrice;
        ImageView prodPic;
        Button plusBtn;
        Button minusBtn;
        Button removeItemBtn;

        int itemCountHad;


        public CartItemHolder(@NonNull View itemView) {
            super(itemView);

            prodName = itemView.findViewById(R.id.prod_name_cart);
            prodPrice = itemView.findViewById(R.id.prod_price_cart);
            prodAddupPrice = itemView.findViewById(R.id.prod_addup_price_cart);
            prodPic = itemView.findViewById(R.id.prod_img_cart);
            plusBtn = itemView.findViewById(R.id.btn_plus_cart);
            minusBtn = itemView.findViewById(R.id.btn_minus_cart);
            removeItemBtn = itemView.findViewById(R.id.btn_remove_from_cart);

            plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemCountHad++;
                    prodName.setText(cartItem.getProduct().getpName() + " " + "(" + itemCountHad + ")");
                    prodAddupPrice.setText(Double.toString(cartItem.getProduct().getpPrice() * itemCountHad));

                    if(minusBtn.getVisibility() == View.GONE){
                        minusBtn.setVisibility(View.VISIBLE);
                    }
                }
            });
            minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemCountHad > 1) {
                        itemCountHad--;
                        prodName.setText(cartItem.getProduct().getpName() + " " + "(" + itemCountHad + ")");
                        prodAddupPrice.setText(Double.toString(cartItem.getProduct().getpPrice() * itemCountHad));
                    }else {
                        minusBtn.setVisibility(View.GONE);
                    }
                }
            });


        }
    }


    @NonNull
    @Override
    public CartItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resourceLayout, viewGroup, false);
        CartAdapter.CartItemHolder viewHolder = new CartItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemHolder cartItemHolder, int i) {

        cartItem = cartItems.get(i);
        cartItemHolder.itemCountHad = cartItem.getAmount();

        cartItemHolder.prodName.setText(cartItem.getProduct().getpName()+" "+"("+cartItemHolder.itemCountHad+")");
        cartItemHolder.prodPrice.setText(Double.toString(cartItem.getProduct().getpPrice()));
        cartItemHolder.prodAddupPrice.setText(Double.toString(cartItem.getProduct().getpPrice() * cartItem.getAmount()));
        Glide.with(context).load(cartItem.getProduct().getpImg()).apply(new RequestOptions().override(500,500)).centerCrop().into(cartItemHolder.prodPic);

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }



}
