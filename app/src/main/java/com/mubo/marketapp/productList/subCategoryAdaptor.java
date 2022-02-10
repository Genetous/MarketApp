package com.mubo.marketapp.productList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mubo.marketapp.R;

import java.util.ArrayList;

public class subCategoryAdaptor extends RecyclerView.Adapter<subCategoryAdaptor.ViewHolder> {

    private ArrayList<subCategoriesData> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context cx;
    View lastview = null;

    public subCategoryAdaptor(Context context, ArrayList<subCategoriesData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.cx = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.subcategory_selector, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String category = mData.get(position).getSubCategory();
        holder.cat.setText(category);
        if(mData.get(position).isSelected()){
            holder.rel.setBackground(cx.getDrawable(R.drawable.subcategory_back_selected));
        }else{
            holder.rel.setBackground(cx.getDrawable(R.drawable.subcategory_back_norm));
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cat;
        RelativeLayout rel;

        ViewHolder(View itemView) {
            super(itemView);
            cat = itemView.findViewById(R.id.c_text);
            rel = itemView.findViewById(R.id.but_rel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            for(subCategoriesData cd:mData){
                cd.setSelected(false);
            }
            mData.get(getAdapterPosition()).setSelected(true);
            if (lastview != null) {
                lastview.setBackground(cx.getDrawable(R.drawable.subcategory_back_norm));
            }
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); ++i) {
                View v = ((ViewGroup) view).getChildAt(i);
                if (v instanceof RelativeLayout) {
                    v.setBackground(cx.getDrawable(R.drawable.subcategory_back_selected));
                    lastview = v;
                    break;
                }
            }
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void remove_lastview() {
        if (lastview != null) {
            lastview.setBackgroundColor(cx.getColor(R.color.white));
            lastview = null;
        }
    }

    subCategoriesData getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
