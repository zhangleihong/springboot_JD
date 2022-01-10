package com.example.springboot_init.service;

import com.example.springboot_init.dao.CommentRepository;
import com.example.springboot_init.po.Comment;
import com.example.springboot_init.po.Product;
import com.example.springboot_init.po.SellCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommentService {
    //注入dao
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *  保存一个评论
     *  @param  comment */
    public void saveComment(Comment comment){
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        //设置一些默认初始值。。。
        //调用dao
        commentRepository.save(comment);
    }

    /**
     *  查询所有评论* @return
     */
    public List<Comment> findCommentList(){
        //调用dao
        return commentRepository.findAll(); }


//    public List<Integer> sellCountTotal(String skuid){
//
//        Query query = Query.query(Criteria.where("skuId").is("100016773624"));
//        List<Comment> lists= mongoTemplate.find(query,Comment.class);
//
//    }
    public SellCount sellCountTotal(String skuId){

        SellCount sellCount = new SellCount();

        Query query = Query.query(Criteria.where("skuId").is(skuId));
        List<Comment> lists= mongoTemplate.find(query,Comment.class);
        Integer one = 0;
        Integer two = 0;
        Integer three = 0;
        Integer four = 0;
        for(int i=0;i<lists.size();i++){
            String timeString = lists.get(i).getTime();

            String [] timeDuan = null;
            timeDuan = timeString.split("-");
            String month = timeDuan[1];
            //System.out.println(month);
            if(month.equals("01")||month.equals("02")||month.equals("03")){
                one += 1;
                //System.out.println("sd");

            }
            else if(month.equals("04")||month.equals("05")||month.equals("06")){
                two += 1;

            }
            else if(month.equals("07")||month.equals("08")||month.equals("09")){
                three += 1;

            }
            else if(month.equals("10")||month.equals("11")||month.equals("12")){
                four += 1;

            }

        }
        sellCount.setSkuId("100016773624");
        sellCount.setFirstQuarter(one);
        sellCount.setSecondQuarter(two);
        sellCount.setThirdQuarter(three);
        sellCount.setForthQuarter(four);
        System.out.println(sellCount);
        return sellCount;

    }





}
