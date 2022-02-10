package com.mubo.marketapp.productList;

import android.os.Parcel;
import android.os.Parcelable;

public class productData implements Parcelable {
    private String category;
    private String subCategory;
    private String product_name;
    private String product_price;
    private String product_description;
    private String product_image;
    private String id;

    public productData() {
    }

    public productData(String category, String subCategory, String product_name, String product_price, String product_description, String product_image, String id) {
        this.category = category;
        this.subCategory = subCategory;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_description = product_description;
        this.product_image = product_image;
        this.id = id;
    }

    protected productData(Parcel in) {
        category = in.readString();
        subCategory = in.readString();
        product_name = in.readString();
        product_price = in.readString();
        product_description = in.readString();
        product_image = in.readString();
        id = in.readString();
    }

    public static final Creator<productData> CREATOR = new Creator<productData>() {
        @Override
        public productData createFromParcel(Parcel in) {
            return new productData(in);
        }

        @Override
        public productData[] newArray(int size) {
            return new productData[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(category);
        parcel.writeString(subCategory);
        parcel.writeString(product_name);
        parcel.writeString(product_price);
        parcel.writeString(product_description);
        parcel.writeString(product_image);
        parcel.writeString(id);
    }
}
