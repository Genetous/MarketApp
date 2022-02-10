package com.mubo.marketapp.productList;

public class subCategoriesData {
    private String id;
    private String subCategory;
    private boolean selected;

    public subCategoriesData(String id, String subCategory, boolean selected) {
        this.id = id;
        this.subCategory = subCategory;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
