package com.mubo.marketapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mubo.marketapp.productList.categoriesData;
import com.mubo.marketapp.productList.categoryAdaptor;
import com.mubo.marketapp.productList.productAdaptor;
import com.mubo.marketapp.productList.productData;
import com.mubo.marketapp.productList.productDataBasket;
import com.mubo.marketapp.productList.subCategoriesData;
import com.mubo.marketapp.productList.subCategoryAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Products extends AppCompatActivity implements Interfaces.apiReturn,Interfaces.checkToken {
    Interfaces.apiReturn ar;
    ApiClass api;
    RecyclerView cat,scat;
    Context cx;
    Activity act;
    categoryAdaptor adp;
    subCategoryAdaptor adp2;
    productAdaptor pa;
    GridView pView;
    EditText search;
    FloatingActionButton fab;
    RelativeLayout rel;
    private ExpandOrCollapse mAnimationManager;
    Ayarlar ayarlar;
    String token="";
    int dataval= -1;
    Interfaces.checkToken ct;
    TokenOp to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.action_bar_home, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
        act=this;
        cx=this;
        ar=this;
        api=new ApiClass();
        ct=this;
        ayarlar=new Ayarlar(cx);
        token=ayarlar.get_pref_string("token");
        mAnimationManager = new ExpandOrCollapse();
        fab=findViewById(R.id.fab);
        rel=findViewById(R.id.rel);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchAktive){
                    fab.setImageResource(R.drawable.search);
                    searchAktive=false;
                    mAnimationManager.collapse(rel, 500, 0);
                    categories.clear();
                    subCategoriesList.clear();
                    productz.clear();
                    selected_productz.clear();
                    search.setText("");
                    hideKeyboard();
                    getProductsByCategory();
                }else{
                    fab.setImageResource(R.drawable.close);
                    searchAktive=true;
                    mAnimationManager.expand(rel, 500, (int) pxFromDp(cx,60));
                    search.requestFocus();
                    showKeyboard();
                }
            }
        });
        cat=findViewById(R.id.categories);
        scat=findViewById(R.id.subCategories);
        pView=findViewById(R.id.products);
        search=findViewById(R.id.inputSearch);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        cat.setLayoutManager(layoutManager);
        scat.setLayoutManager(layoutManager2);
        pView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent ii=new Intent(cx,SingleProduct.class);
                if(searchAktive)
                    ii.putExtra("product",selected_productz.get(i));
                else
                    ii.putExtra("product",productz.get(i));
                startActivityForResult(ii,123);
            }
        });
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    try {
                        search(search.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        dataval=0;
        to=new TokenOp(cx,ct);
        to.verify();
    }
    int resAct=123;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setupBadge();
    }

    void getProductsByCategory(){

        JSONObject rel=new JSONObject();
        try {
            rel.put("relationName","categoryRelations");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    api.getCategoriesWithProducts(rel,ar,token);
                }
            }).start();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    ArrayList<categoriesData> categories=new ArrayList<>();
    ArrayList<productData> productz=new ArrayList<>();
    ArrayList<productData> selected_productz=new ArrayList<>();
    ArrayList<subCategoriesData> subCategoriesList=new ArrayList<>();
    int selectedCategoryPosition=0;
    @Override
    public void returnData(JSONObject j) {
        try {

            JSONArray values=j.getJSONArray("values");
            for(int i=0;i<values.length();++i){
                JSONObject v=values.getJSONObject(i);
                JSONObject content=v.getJSONObject("content");
                String c_id=v.getJSONObject("collection_content").getString("_id");
                String category= v.getJSONObject("collection_content").getJSONObject("content").getString("category_name");
                JSONArray subCategories=content.getJSONArray("subcategory");
                ArrayList<subCategoriesData> subCat=new ArrayList<>();
                for(int c=0;c<subCategories.length();++c){
                    JSONObject scj=subCategories.optJSONObject(c);
                    if(scj!=null) {
                        String s_id = scj.getString("_id");
                        String sc = scj.optJSONObject("content").getString("category_name");
                        subCat.add(new subCategoriesData(s_id,sc,false));
                    }

                }
                categoriesData cd=new categoriesData(c_id,category,subCat,false);
                categories.add(cd);
            }
            Collections.sort(categories, new Comparator<categoriesData>(){
                public int compare(categoriesData o1, categoriesData o2){
                    return o1.getCategory().compareToIgnoreCase(o2.getCategory());
                }
            });
            for(categoriesData c:categories){
                Collections.sort(c.getSubcategories(), new Comparator<subCategoriesData>(){
                    public int compare(subCategoriesData o1, subCategoriesData o2){
                        return o1.getSubCategory().compareToIgnoreCase(o2.getSubCategory());
                    }
                });
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adp=new categoryAdaptor(cx,categories);
                    adp.setClickListener(new categoryAdaptor.ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            categoriesData cd=categories.get(position);
                            selectedCategoryPosition=position;
                            subCategoriesList=new ArrayList<>();
                            subCategoriesList.addAll(categories.get(selectedCategoryPosition).getSubcategories());
                            setAdp2();
                        }
                    });
                    cat.setAdapter(adp);
                    //subCategoriesList=categories.get(selectedCategoryPosition).getSubcategories();
                    if(categories.size()>0)
                        cat.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                View viewItem = cat.getLayoutManager().findViewByPosition(0);
                                viewItem.callOnClick();
                            }
                        },50);

                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnDataSecond(JSONObject j) {
        try {

            JSONArray values=j.getJSONArray("values");
            for(int i=0;i<values.length();++i) {
                JSONObject c_content = values.getJSONObject(i).getJSONObject("collection_content");
                JSONObject content = values.getJSONObject(i).getJSONObject("content");
                String c_name = content
                        .getJSONArray("category")
                        .getJSONObject(0)
                        .getJSONObject("content").getString("category_name");
                String sc_name = content
                        .getJSONArray("subcategory")
                        .getJSONObject(0)
                        .getJSONObject("content").getString("category_name");
                String p_id =c_content.getString("_id");
                JSONObject p_content=c_content.getJSONObject("content");
                String p_name = p_content.getString("product_name");
                String p_image = p_content.getString("product_image");
                String p_desc = p_content.getString("product_description");
                String p_price = p_content.getString("product_price");
                productData pd = new productData(c_name, sc_name, p_name, p_price, p_desc, p_image, p_id);
                productz.add(pd);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pa=new productAdaptor(cx,productz);
                    pView.setAdapter(pa);
                }
            });

        }catch (Exception ex){}
    }
    boolean searchAktive=false;
    @Override
    public void searchReturn(JSONObject j) {
        productz.clear();
        selected_productz.clear();
        try {
            JSONArray result= j.getJSONArray("result");
            JSONArray values=result.getJSONObject(0).getJSONArray("values");
            for(int i=0;i<values.length();++i) {
                JSONObject c_content = values.getJSONObject(i).getJSONObject("collection_content");
                JSONObject content = values.getJSONObject(i).getJSONObject("content");
                String c_name = content
                        .getJSONArray("category")
                        .getJSONObject(0)
                        .getJSONObject("content").getString("category_name");
                String sc_name = content
                        .getJSONArray("subcategory")
                        .getJSONObject(0)
                        .getJSONObject("content").getString("category_name");
                String p_id =c_content.getString("_id");
                JSONObject p_content=c_content.getJSONObject("content");
                String p_name = p_content.getString("product_name");
                String p_image = p_content.getString("product_image");
                String p_desc = p_content.getString("product_description");
                String p_price = p_content.getString("product_price");
                productData pd = new productData(c_name, sc_name, p_name, p_price, p_desc, p_image, p_id);
                productz.add(pd);
            }
            categories.clear();
            subCategoriesList.clear();
            for(productData p:productz){
                boolean c_var=false;
                for(categoriesData cd:categories){
                    if(cd.getCategory().equals(p.getCategory())){
                        boolean sd_var=false;
                        for(subCategoriesData sd:cd.getSubcategories()){
                            if(sd.getSubCategory().equals(p.getSubCategory())){
                                sd_var=true;
                                break;
                            }
                        }
                        if(!sd_var){
                            cd.getSubcategories().add(new subCategoriesData("",p.getSubCategory(),false));
                        }
                        c_var=true;
                        break;
                    }
                }
                if(!c_var) {
                    ArrayList<subCategoriesData> sd=new ArrayList<>();
                    sd.add(new subCategoriesData("",p.getSubCategory(),false));
                    categories.add(new categoriesData("", p.getCategory(),sd,false));
                }
            }
            Collections.sort(categories, new Comparator<categoriesData>(){
                public int compare(categoriesData o1, categoriesData o2){
                    return o1.getCategory().compareToIgnoreCase(o2.getCategory());
                }
            });
            for(categoriesData c:categories){
                Collections.sort(c.getSubcategories(), new Comparator<subCategoriesData>(){
                    public int compare(subCategoriesData o1, subCategoriesData o2){
                        return o1.getSubCategory().compareToIgnoreCase(o2.getSubCategory());
                    }
                });
            }
            searchAktive=true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adp.notifyDataSetChanged();
                    if(categories.size()>0) {
                        cat.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                View viewItem = cat.getLayoutManager().findViewByPosition(0);
                                viewItem.callOnClick();
                            }
                        }, 50);
                    }
                }
            });


        }catch (Exception ex){}
    }

    void setAdp2(){
        adp2=new subCategoryAdaptor(cx,subCategoriesList);
        adp2.setClickListener(new subCategoryAdaptor.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                subCategoriesData cd=subCategoriesList.get(position);
                if(!searchAktive) {
                    try {
                        productz.clear();
                        getProducts(cd.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    selected_productz = new ArrayList<>();
                    for (productData p : productz) {
                        if (p.getSubCategory().equals(cd.getSubCategory()))
                            selected_productz.add(p);
                    }
                    pa = new productAdaptor(cx, selected_productz);
                    pView.setAdapter(pa);
                }
            }
        });
        scat.setAdapter(adp2);
        if(subCategoriesList.size()>0)
            scat.postDelayed(new Runnable() {
                @Override
                public void run() {
                    View viewItem = scat.getLayoutManager().findViewByPosition(0);
                    viewItem.callOnClick();
                }
            },50);
    }
    void getProducts(String subid) throws JSONException {
        JSONObject sendObject=new JSONObject();
        sendObject.put("content.subcategory.id",subid);
        sendObject.put("relationName","productRelations");
        JSONArray fields=new JSONArray();
        fields.put("collection_content");
        fields.put("content");
        sendObject.put("fields",fields);
       /* JSONObject select=new JSONObject();
        JSONArray query_objects=new JSONArray();
        JSONArray fields=new JSONArray();
        JSONObject nested=new JSONObject();
        nested.put("content.subcategory.collectionName","subcategory");
        nested.put("content.subcategory.id",subid);
        JSONObject q1=new JSONObject();
        q1.put("relationName","productRelations");
        query_objects.put(q1);
        query_objects.put(new JSONObject().put("nested",nested));
        fields.put("collection_content");
        fields.put("content");
        select.put("operator","and");
        select.put("query_objects",query_objects);
        select.put("fields",fields);
       *//* select.put("from",start);
        select.put("size",itemcount);
        select.put("sortBy","_id");
        select.put("order","desc");*//*

        JSONObject m=new JSONObject();
        m.put("select",select);*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                api.getProducts(sendObject,ar,token);
            }
        }).start();
    }
    TextView textCartItemCount;
    int mCartItemCount = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_list_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.badge);

        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }
    private void setupBadge() {
        BasketDB db=new BasketDB(cx);
        List<productDataBasket> pdbs=db.sepetGetir();
        mCartItemCount=pdbs.size();
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.badge:
                Intent i=new Intent(cx,Basket.class);
                startActivityForResult(i,123);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    void search(String qq) throws JSONException {
        categories.clear();
        productz.clear();
        subCategoriesList.clear();
        String q="*"+qq.trim()+"*";
        JSONObject searchh=new JSONObject();
        JSONArray search_fields=new JSONArray();
        JSONArray get_fields=new JSONArray();
        get_fields.put("collection_content");
        get_fields.put("content");
        search_fields.put("collection_content.content.product_name");
        searchh.put("search_fields",search_fields);
        searchh.put("from",0);
        searchh.put("size",20);
        searchh.put("query",q);
        searchh.put("get_fields",get_fields);
        JSONObject m=new JSONObject();
        m.put("search",searchh);
        new Thread(new Runnable() {
            @Override
            public void run() {
                api.searchData(m,ar,token);
            }
        }).start();
    }
   void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = act.getCurrentFocus();
        if (view == null) {
            view = new View(act);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
    }
    float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void getToken(String token) {
        switch (dataval){
            case 0:
                getProductsByCategory();
        }
    }
}