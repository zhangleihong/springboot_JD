package com.example.springboot_init.po;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Product {
    @Id
    private String id;//主键
    private String keyword;
    private String skuId;
    private String title;
    private Double price;
    private String shop;
    private Integer sellCount;
    private String icon;
    private String[] productClass;
    private String firstClass;
    private String secondClass;
    private List<Comment> commentList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
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

    public String[] getProductClass() {
        return productClass;
    }

    public void setProductClass(String[] productClass) {
        this.productClass = productClass;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(String firstClass) {
        this.firstClass = firstClass;
    }

    public String getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(String secondClass) {
        this.secondClass = secondClass;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", keyword='" + keyword + '\'' +
                ", skuId='" + skuId + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", shop='" + shop + '\'' +
                ", sellCount=" + sellCount +
                ", icon='" + icon + '\'' +
                ", productClass=" + Arrays.toString(productClass) +
                ", firstClass='" + firstClass + '\'' +
                ", secondClass='" + secondClass + '\'' +
                ", commentList=" + commentList +
                '}';
    }
}





//public class Project implements Serializable {
//    @Id
//    private String id;//主键
//    private String keyword;
//    private String skuId;
//    private String title;
//    private String price;
//    private String shop;
//    private String sellCount;
//    private String icon;
//    private String productClass;
//    private List<Comment> commentList;


//    @Field("content")
//    private String content;//吐槽内容
//    private Date publishtime;//发布日期
//    // 添加了一个单字段的索引
//    @Indexed
//    private String userid;//发布人ID
//    private String nickname;//昵称
//    private LocalDateTime createdatetime;//评论的日期时间
//    private Integer likenum;//点赞数
//    private Integer replynum;//回复数
//    private String state;//状态
//    private String parentid;//上级ID
//    private String articleid;
