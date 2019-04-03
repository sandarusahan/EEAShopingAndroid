package com.apiit.eeashopingandroid;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.apiit.eeashopingandroid.package_cart.CartItem;
import com.apiit.eeashopingandroid.package_product.Product;
import com.apiit.eeashopingandroid.package_product.ProductDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Product[] products;
    private Context context;
    private Product product;
    String url = "http://10.0.3.2:8080/cart/";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView prod_name;
        TextView prod_id;
        TextView prod_price;
        Button btn_add_to_cart;
        Button btn_increase;
        Button btn_decrease;
        ImageView img_prod_card_img;

        int itemCount = 1;

        public ProductViewHolder(View v) {
            super(v);
            prod_name = v.findViewById(R.id.tx_prod_name);
            prod_id = v.findViewById(R.id.tx_prod_id);
            prod_price = v.findViewById(R.id.tx_prod_price);
            btn_add_to_cart = v.findViewById(R.id.btn_add_to_cart);
            img_prod_card_img = v.findViewById(R.id.img_prod_card_img);
            btn_add_to_cart = v.findViewById(R.id.btn_add_to_cart);
            btn_increase = v.findViewById(R.id.btn_increase_count);
            btn_decrease = v.findViewById(R.id.btn_decrease_count);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String productKey = prod_id.getText().toString();
                    Intent i = new Intent(v.getContext(), ProductDetails.class);
                    i.putExtra("product", productKey);
                    v.getContext().startActivity(i);
//                    Toast.makeText(v.getContext(), prod_name.getText(), Toast.LENGTH_SHORT).show();

                }
            });


            btn_increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCount++;
                    btn_add_to_cart.setText("Add "+Integer.toString(itemCount)+ " to cart");

                }
            });
            btn_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemCount>0){
                        itemCount--;
                        btn_add_to_cart.setText("Add "+Integer.toString(itemCount)+ " to cart");

                    }
                }
            });


            btn_add_to_cart.setText("Add "+Integer.toString(itemCount)+ " to cart");

            btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItem cartItem = new CartItem();
                    cartItem.setName(prod_name.getText().toString());
                    cartItem.setAmount(itemCount);
                    cartItem.setPid(prod_id.getText().toString());
                    cartItem.setPrice(Double.parseDouble((prod_price.getText().toString()).substring(3)));
                    cartItem.setUid("User 01");

                    Gson gson = new Gson();
                    String jsonCartItem = gson.toJson(cartItem);

                    JsonObject json = new JsonParser().parse(jsonCartItem).getAsJsonObject();

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url + "add",
                            jsonObject,
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
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> paramMap = new HashMap<>();
                            paramMap.put("pid", prod_id.getText().toString());
                            paramMap.put("amount", Integer.toString(itemCount));
                            paramMap.put("uid", "User 01");
                            paramMap.put("price", prod_price.getText().toString());
                            paramMap.put("name", prod_name.getText().toString());

                            return paramMap;
                        }
                    };
                    AppSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
                    Snackbar.make(v, itemCount +" of "+prod_name.getText() + " added to the cart", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(context, "LOL", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductAdapter(Product[] myDataset, Context context) {
        products = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);

        ProductViewHolder vh = new ProductViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        product = products[position];
        holder.prod_id.setText(product.getpId());
        holder.prod_name.setText(product.getpName());
        holder.prod_price.setText("Rs. " + Double.toString(product.getpPrice()));
        Glide.with(context).load(product.getpImg()).apply(new RequestOptions().override(500,500)).centerCrop().into(holder.img_prod_card_img);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return products.length;
    }
}
