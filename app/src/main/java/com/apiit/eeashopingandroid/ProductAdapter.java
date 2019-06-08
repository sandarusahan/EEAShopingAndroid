package com.apiit.eeashopingandroid;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.apiit.eeashopingandroid.package_cart.CartItem;
import com.apiit.eeashopingandroid.package_product.Product;
import com.apiit.eeashopingandroid.package_product.ProductDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Product[] products;
    private Context context;
    private Product product;
    private Product selectedProduct;
    String url = "http://10.0.3.2:8080/auth/cart/add";
    String producturl = "http://10.0.3.2:8080/product/";
    Session session;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView prod_name;
        TextView prod_id;
        TextView prod_price;
        TextView items_qty;
        Button btn_add_to_cart;
        Button btn_increase;
        Button btn_decrease;
        ImageView img_prod_card_img;

        int itemCount = 1;

        public ProductViewHolder(View v) {
            super(v);
            prod_name = v.findViewById(R.id.tx_prod_name);
            prod_id = v.findViewById(R.id.tx_prod_id);
            items_qty = v.findViewById(R.id.tx_item_qty);
            prod_price = v.findViewById(R.id.tx_prod_price);
            btn_add_to_cart = v.findViewById(R.id.btn_add_to_cart);
            img_prod_card_img = v.findViewById(R.id.img_prod_card_img);
            btn_add_to_cart = v.findViewById(R.id.btn_add_to_cart);
            btn_increase = v.findViewById(R.id.btn_increase_count);
            btn_decrease = v.findViewById(R.id.btn_decrease_count);

            session = new Session(context);

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
                    items_qty.setText(Integer.toString(itemCount)+ " items");

                }
            });
            btn_decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemCount>1){
                        itemCount--;
                        items_qty.setText(Integer.toString(itemCount)+ " items");

                    }
                }
            });

            btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(session.hasUserLoggedIn()){

                        getProduct(prod_id.getText().toString());

                        Snackbar.make(v, itemCount +" of "+prod_name.getText() + " added to the cart", Snackbar.LENGTH_LONG).show();
                    }else{
                        Intent intent = new Intent(context, LoginActivity.class);
                        v.getContext().startActivity(intent);
                    }

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

    public void getProduct(String pid) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                producturl+pid,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new Gson();
                        Product product = gson.fromJson(response.toString(), Product.class);

                        selectedProduct = product;

                        addToCart();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error fetching product"+error);
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public void addToCart(){
        CartItem cartitem = new CartItem();
        cartitem.setProduct(selectedProduct);
        cartitem.setAmount(1);
        cartitem.setUserEmail(session.getUserEmail()); // hardcoded to one user.....................................................

        Gson gson = new Gson();
        String Jsoncart = gson.toJson(cartitem);

        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(Jsoncart);

        JSONObject cartjsonObject = null;
        try{
            cartjsonObject = new JSONObject(json.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                cartjsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error adding product to cart"+error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> cartMap = new HashMap<>();
                cartMap.put("product", selectedProduct.toString());
                cartMap.put("email", session.getUserEmail());
                cartMap.put("amount", "1");

                return cartMap;
            }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    String credentials = session.getUserEmail()+":"+session.getpassword();
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", auth);
                    return headers;
                }
        };

        AppSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }
}
