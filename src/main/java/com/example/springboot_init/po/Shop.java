package com.example.springboot_init.po;

public class Shop {
    private String shopName;
    private Integer sellCount;
    private String icon;
    private String shopProductClass;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShopProductClass() {
        return shopProductClass;
    }

    public void setShopProductClass(String shopProductClass) {
        this.shopProductClass = shopProductClass;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopName='" + shopName + '\'' +
                ", sellCount=" + sellCount +
                ", icon='" + icon + '\'' +
                ", shopProductClass='" + shopProductClass + '\'' +
                '}';
    }
}
