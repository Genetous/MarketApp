package com.mubo.marketapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mubo.marketapp.productList.productDataBasket;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class BasketAdaptor extends RecyclerView.Adapter<BasketAdaptor.ViewHolder> {

    private List<productDataBasket> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context cx;
    View lastview = null;
    DecimalFormat df = new DecimalFormat("#.00");
    public BasketAdaptor(Context context, List<productDataBasket> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.cx = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.basket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getProduct_name());
        holder.desc.setText(mData.get(position).getProduct_description());
        holder.count.setText(String.valueOf(mData.get(position).getCount()));
        int count=mData.get(position).getCount();
        double d=Double.parseDouble(mData.get(position).getProduct_price());
        d=d*count;
        String tp="₺"+String.valueOf(df.format(d));
        holder.total_price.setText(tp);
        String url=mData.get(position).getProduct_image();
        if(url.trim().isEmpty())
            holder.img.setImageDrawable(cx.getDrawable(R.drawable.no_image));
        else
            Picasso.get().load(mData.get(position).getProduct_image()).into(holder.img);

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        TextView name,desc,price,total_price;
        ImageView img;
        EditText count;
        RelativeLayout dec,inc;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            price = itemView.findViewById(R.id.price);
            total_price = itemView.findViewById(R.id.total_price);
            img = itemView.findViewById(R.id.img);
            count = itemView.findViewById(R.id.count);
            dec = itemView.findViewById(R.id.decrease);
            inc = itemView.findViewById(R.id.increase);
            inc.setTag("inc");
            dec.setTag("dec");
            dec.setOnClickListener(this);
            inc.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) mClickListener.onItemLongClick(view, getAdapterPosition());
            return true;
        }
    }

    public void remove_lastview() {
        if (lastview != null) {
            lastview.setBackgroundColor(cx.getColor(R.color.white));
            lastview = null;
        }
    }

    productDataBasket getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }


}

