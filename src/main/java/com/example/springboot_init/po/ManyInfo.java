package com.example.springboot_init.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class ManyInfo {

    private String shopName;
    private String productId;
    private Double starAvg;
    private Integer sellCount;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getStarAvg() {
        return starAvg;
    }

    public void setStarAvg(Double starAvg) {
        this.starAvg = starAvg;
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    @Override
    public String toString() {
        return "manyInfo{" +
                "shopName='" + shopName + '\'' +
                ", productId='" + productId + '\'' +
                ", starAvg=" + starAvg +
                ", sellCount=" + sellCount +
                '}';
    }
}
