package com.apiit.eeashopingandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Product[] products;
    private Context context;
    private Product product;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView prod_name;
        private TextView prod_price;
        private Button btn_add_to_cart;
        private ImageView img_prod_card_img;

        public ProductViewHolder(View v) {
            super(v);
            prod_name = (TextView) v.findViewById(R.id.tx_prod_name);
            prod_price = (TextView) v.findViewById(R.id.tx_prod_price);
            btn_add_to_cart = (Button) v.findViewById(R.id.btn_add_to_cart);
            img_prod_card_img = (ImageView) v.findViewById(R.id.img_prod_card_img);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String productKey = product.getpId();
                    Intent i = new Intent(v.getContext(), ProductDetails.class);
                    i.putExtra("product", productKey);
                    v.getContext().startActivity(i);
                    Toast.makeText(v.getContext(), prod_name.getText(), Toast.LENGTH_SHORT).show();

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
