package com.mubo.marketapp.productList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mubo.marketapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class productAdaptor extends BaseAdapter {
    List<productData> data;
    Context cx;
    LayoutInflater layoutInflater;
    pItemRow itemRow;
    public productAdaptor(Context cx,List<productData> data){
        this.cx=cx;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if (itemView == null) {
            layoutInflater = (LayoutInflater) cx.getSystemService(cx.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.product_layout, viewGroup, false);
            itemRow = new pItemRow();
            itemView.setTag(itemRow);
        } else {
            itemView = view;
            try {
                itemRow = (pItemRow) itemView.getTag();
            } catch (Exception ex) {
                String exx = ex.toString();
            }
        }

        itemRow.product_description = (TextView) itemView.findViewById(R.id.description);
        itemRow.product_price = (TextView) itemView.findViewById(R.id.price);
        itemRow.product_name = (TextView) itemView.findViewById(R.id.name);
        itemRow.product_image = (ImageView) itemView.findViewById(R.id.img);
        itemRow.product_price.setText("₺"+data.get(i).getProduct_price());
        itemRow.product_description.setText(data.get(i).getProduct_description());
        itemRow.product_name.setText(data.get(i).getProduct_name());
        Picasso.get().load(data.get(i).getProduct_image()).into(itemRow.product_image);
        return itemView;
    }
    private class pItemRow {
        ImageView product_image;
        TextView product_description;
        TextView product_name;
        TextView product_price;
    }
}
