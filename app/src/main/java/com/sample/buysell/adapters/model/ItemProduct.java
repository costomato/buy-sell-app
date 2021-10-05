package com.sample.buysell.adapters.model;

public class ItemProduct {
    private String date, time, productName, unit;
    private float productPrice;
    private int productQty;
    private long ID;

    public ItemProduct(long ID, String date, String time, String productName, float productPrice,
                       String unit, int productQty) {
        this.ID = ID;
        this.date = date;
        this.time = time;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.unit = unit;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getProductName() {
        return productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public int getProductQty() {
        return productQty;
    }
}
