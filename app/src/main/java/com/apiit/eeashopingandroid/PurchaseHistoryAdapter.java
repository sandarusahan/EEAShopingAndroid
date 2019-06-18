package com.apiit.eeashopingandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PurchaseHistoryAdapter extends ArrayAdapter<SalesOrder> {
    Context context;
    SalesOrder[] orders;
    int resourceLayout;
    String url = "http://10.0.3.2:8080/";
    public PurchaseHistoryAdapter(Context context, int resource, SalesOrder[] salesOrders) {
        super(context, resource, salesOrders);
        this.context = context;
        this.orders = salesOrders;
        this.resourceLayout = resource;
    }

    private class OrderHolder {
        TextView order_id;
        TextView order_address;
        TextView item_list;
        TextView order_total;
        TextView order_date;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SalesOrder salesOrder = getItem(position);
        final OrderHolder orderHolder;

        final View view;

        if(convertView == null) {

            orderHolder = new OrderHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resourceLayout, parent, false);

            orderHolder.order_address = convertView.findViewById(R.id.tx_order_address);
            orderHolder.item_list = convertView.findViewById(R.id.tx_item_list);
            orderHolder.order_date = convertView.findViewById(R.id.tx_order_date);
            orderHolder.order_total = convertView.findViewById(R.id.tx_order_total);
            orderHolder.order_id = convertView.findViewById(R.id.tx_order_id);

            view = convertView;

            convertView.setTag(orderHolder);
        }
        else {
            orderHolder = (OrderHolder) convertView.getTag();
            view = convertView;
        }
        String itemList="";
        for(OrderItem item : salesOrder.getOrderItems()){
            itemList = itemList + item.getProduct().getpName() +" - "+ item.getProduct().getpPrice()+"\n";
        }
        orderHolder.order_id.setText(Integer.toString(salesOrder.getOrderId()));
        orderHolder.order_address.setText(salesOrder.getAddress());
        orderHolder.item_list.setText(itemList);
        orderHolder.order_date.setText(salesOrder.getOrderDate().toString());
        orderHolder.order_total.setText("Rs. "+Double.toString(salesOrder.getTotalAmount()));

        return view;


    }
}
