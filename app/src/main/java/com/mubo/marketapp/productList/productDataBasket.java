package com.mubo.marketapp.productList;

import android.os.Parcel;
import android.os.Parcelable;

public class productDataBasket implements Parcelable {
    private String category;
    private String subCategory;
    private String product_name;
    private String product_price;
    private String product_description;
    private String product_image;
    private String id;
    private int count;
    private int ID;

    public productDataBasket() {
    }

    public productDataBasket(String category, String subCategory, String product_name, String product_price, String product_description, String product_image, String id, int count, int ID) {
        this.category = category;
        this.subCategory = subCategory;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_description = product_description;
        this.product_image = product_image;
        this.id = id;
        this.count = count;
        this.ID = ID;
    }

    protected productDataBasket(Parcel in) {
        category = in.readString();
        subCategory = in.readString();
        product_name = in.readString();
        product_price = in.readString();
        product_description = in.readString();
        product_image = in.readString();
        id = in.readString();
        count = in.readInt();
        ID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(subCategory);
        dest.writeString(product_name);
        dest.writeString(product_price);
        dest.writeString(product_description);
        dest.writeString(product_image);
        dest.writeString(id);
        dest.writeInt(count);
        dest.writeInt(ID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<productDataBasket> CREATOR = new Creator<productDataBasket>() {
        @Override
        public productDataBasket createFromParcel(Parcel in) {
            return new productDataBasket(in);
        }

        @Override
        public productDataBasket[] newArray(int size) {
            return new productDataBasket[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
