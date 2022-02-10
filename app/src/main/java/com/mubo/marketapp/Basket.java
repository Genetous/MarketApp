package com.mubo.marketapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mubo.marketapp.productList.productDataBasket;

import java.text.DecimalFormat;
import java.util.List;

public class Basket extends AppCompatActivity {

    RecyclerView rView;
    TextView total;
    BasketDB bd;
    List<productDataBasket> pdbs;
    BasketAdaptor adaptor;
    View v;
    RelativeLayout back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rView = findViewById(R.id.basketrec);
        total = findViewById(R.id.total_price);
        rView.setLayoutManager(layoutManager);
        rView.addItemDecoration(
                new DividerItemDecoration(getDrawable(R.drawable.divider),
                        false, false));
        bd=new BasketDB(this);
        pdbs=bd.sepetGetir();
        calculate_total();

        adaptor=new BasketAdaptor(this,pdbs);
        adaptor.setClickListener(new BasketAdaptor.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String tag=view.getTag().toString();
                productDataBasket p=pdbs.get(position);
                int new_count=p.getCount();

                if(tag.equals("inc")) {
                    if(new_count<10)
                        new_count+=1;
                }else if(tag.equals("dec")) {
                    if(new_count>1)
                        new_count-=1;
                }
                p.setCount(new_count);
                bd.updateCount(String.valueOf(p.getID()),new_count);
                calculate_total();
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                new AlertDialog.Builder(Basket.this)
                        .setTitle("Ürünü Sil")
                        .setMessage("Ürünü silmek istediğinize emin misiniz?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                productDataBasket p=pdbs.get(position);
                                bd.delete(String.valueOf(p.getID()));
                                pdbs.remove(position);
                                adaptor.notifyDataSetChanged();
                                calculate_total();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        rView.setAdapter(adaptor);
    }
    double totald=0d;
    DecimalFormat df = new DecimalFormat("#.00");
    void calculate_total(){
        totald=0d;
        for(productDataBasket p:pdbs){
            int count=p.getCount();
            double d=Double.parseDouble(p.getProduct_price());
            totald += (d*count);
        }
        String tp="₺"+String.valueOf(df.format(totald));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View v=getLayoutInflater().inflate(R.layout.action_bar_home, null);
        ((TextView)v.findViewById(R.id.title)).setText("Sepet Toplamı ( "+tp+" )");
        back = v.findViewById(R.id.back);
        actionBar.setCustomView(v,
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}