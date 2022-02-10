package com.mubo.marketapp.productList;

import java.util.ArrayList;

public class categoriesData {
    private String id;
    private String category;
    private ArrayList<subCategoriesData> subcategories;
    private boolean selected;

    public categoriesData(String id, String category, ArrayList<subCategoriesData> subcategories, boolean selected) {
        this.id = id;
        this.category = category;
        this.subcategories = subcategories;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<subCategoriesData> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(ArrayList<subCategoriesData> subcategories) {
        this.subcategories = subcategories;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
