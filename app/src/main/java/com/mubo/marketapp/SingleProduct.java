package com.mubo.marketapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mubo.marketapp.productList.productData;
import com.mubo.marketapp.productList.productDataBasket;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class SingleProduct extends AppCompatActivity {
    EditText count;
    RelativeLayout dec,inc,back;
    Button addBasket;
    ImageView img;
    TextView desc,price,total_price;
    productData pd;
    Context cx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
        cx=this;
        count = findViewById(R.id.count);
        dec = findViewById(R.id.decrease);
        inc = findViewById(R.id.increase);
        addBasket = findViewById(R.id.basket);
        img = findViewById(R.id.img);

        desc = findViewById(R.id.description);
        price = findViewById(R.id.price);
        total_price = findViewById(R.id.total_price);
        pd=this.getIntent().getParcelableExtra("product");
        if(pd!=null){
            desc.setText(pd.getProduct_description());
            price.setText("₺"+String.valueOf(df.format(Double.parseDouble(pd.getProduct_price()))));
            total_price.setText("₺"+String.valueOf(df.format(Double.parseDouble(pd.getProduct_price()))));
            Picasso.get().load(pd.getProduct_image()).into(img);
        }
        inc.setOnClickListener(inc_cl);
        dec.setOnClickListener(dec_cl);
        addBasket.setOnClickListener(addToBasket);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View v=getLayoutInflater().inflate(R.layout.action_bar_home, null);
        ((TextView)v.findViewById(R.id.title)).setText(pd.getProduct_name());
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
    DecimalFormat df = new DecimalFormat("#.00");
    View.OnClickListener inc_cl=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String s=count.getText().toString();
            int ss=Integer.valueOf(s);
            if(ss>=10){
                Toast.makeText(SingleProduct.this, "Maksimum 10 Ürün Ekleyebilirsiniz.", Toast.LENGTH_SHORT).show();
            }else{
                ss++;
                count.setText(String.valueOf(ss));
                double d=Double.parseDouble(pd.getProduct_price());
                d=d*ss;
                total_price.setText("₺"+String.valueOf(df.format(d)));
            }
        }
    };
    View.OnClickListener dec_cl=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String s=count.getText().toString();
            int ss=Integer.valueOf(s);
            if(ss<=1){

            }else{
                ss--;
                count.setText(String.valueOf(ss));
                double d=Double.parseDouble(pd.getProduct_price());
                d=d*ss;
                total_price.setText("₺"+String.valueOf(df.format(d)));
            }
        }
    };
    View.OnClickListener addToBasket=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String s = count.getText().toString();
            int ss = Integer.valueOf(s);
            BasketDB bd = new BasketDB(cx);
            int ID=bd.contains(pd.getId());
            if (ID == -1) {
                productDataBasket pdb = new productDataBasket();
                pdb.setId(pd.getId());
                pdb.setProduct_image(pd.getProduct_image());
                pdb.setProduct_description(pd.getProduct_description());
                pdb.setProduct_price(pd.getProduct_price());
                pdb.setProduct_name(pd.getProduct_name());
                pdb.setSubCategory(pd.getSubCategory());
                pdb.setCategory(pd.getCategory());
                pdb.setCount(ss);
                bd.productEkle(pdb);
            }else{
                bd.updateCount(String.valueOf(ID),ss);
            }
            List<productDataBasket> pdbs=bd.sepetGetir();
            Log.i("Size",String.valueOf(pdbs.size()));
            Toast.makeText(cx, "Sepete Eklendi", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent();
        setResult(RESULT_OK, mIntent);
        super.onBackPressed();
    }
}