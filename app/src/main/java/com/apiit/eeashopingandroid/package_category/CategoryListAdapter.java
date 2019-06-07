package com.apiit.eeashopingandroid.package_category;

import android.content.Context;

import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.apiit.eeashopingandroid.AppSingleton;
import com.apiit.eeashopingandroid.R;
import com.apiit.eeashopingandroid.package_cart.CartAdapter;
import com.apiit.eeashopingandroid.package_cart.CartItem;
import com.google.gson.Gson;

import org.json.JSONArray;


public class CategoryListAdapter extends ArrayAdapter<Category> {
    Context context;
    Category[] categories;
    int resourceLayout;
    String url = "http://10.0.3.2:8080/";

    public CategoryListAdapter(Context context, int resource,  Category[] categories) {
        super(context, resource, categories);
        this.context = context;
        this.categories = categories;
        this.resourceLayout = resource;

    }

    private class CatItemHolder {
        TextView category;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {

        Category category =  getItem(position);

        final CatItemHolder catItemHolder;

        final View view;



        if(convertView == null) {

            catItemHolder = new CatItemHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resourceLayout, parent, false);
            catItemHolder.category = convertView.findViewById(R.id.cat_name);

            view = convertView;

            convertView.setTag(catItemHolder);
        }
        else {
            catItemHolder = (CatItemHolder) convertView.getTag();
            view = convertView;
        }
        catItemHolder.category.setText(category.name);

        return view;
    }
}



