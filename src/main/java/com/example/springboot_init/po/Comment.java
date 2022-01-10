package com.example.springboot_init.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
/**
 *  文章评论实体类*/
//把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。
//@Document(collection="mongodb 对应 collection 名")
//  若未加 @Document  ，该 bean  save  到 mongo  的 comment  collection
//  若添加 @Document  ，则 save  到 comment  collection @Document(collection="comment")//可以省略，如果省略，则默认使用类名小写映射集合
//复合索引
// @CompoundIndex( def = "{'userid': 1, 'nickname': -1}") public class Comment implements Serializable {
//主键标识，该属性的值会自动对应mongodb的主键字段"_id"，如果该属性名就叫“id”,则该注解可以省略，否则必须写@Id

@Document(collection="comment")
public class Comment implements Serializable {
    @Id
    private String commentId;
    private String skuId;
    private Integer star;
    private String nickname;
    private Boolean isPlus;
    private String content;
    private String productType;
    private String time;


    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getPlus() {
        return isPlus;
    }

    public void setPlus(Boolean plus) {
        isPlus = plus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "skuId='" + skuId + '\'' +
                ", star=" + star +
                ", nickname='" + nickname + '\'' +
                ", isPlus=" + isPlus +
                ", content='" + content + '\'' +
                ", productType='" + productType + '\'' +
                ", time='" + time + '\'' +
                ", commentId='" + commentId + '\'' +
                '}';
    }
}