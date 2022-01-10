package com.example.springboot_init.service;

import com.example.springboot_init.dao.FirstClassRepository;
import com.example.springboot_init.dao.ProductRepository;
import com.example.springboot_init.po.FirstClass;
import com.example.springboot_init.po.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FirstClassService {
    //注入dao
    @Autowired
    private FirstClassRepository firstClassRepository;
    final int pageSize = 10;



    /**
     * 保存一个评论
     *
     * @param firstClass
     */
    public void saveFirstClass(FirstClass firstClass) {
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        //设置一些默认初始值。。。
        //调用dao
        firstClassRepository.save(firstClass);
    }



    public List<String> findFirstClass(Integer page){
        //Query query = Query.query(Criteria.where("firstClass").is("手机通讯"));
        //query.addCriteria()
        //query.fields().include("productClass");
        System.out.println("234567");
        //List<Product> lists= mongoTemplate.find(query,Product.class);
        List<FirstClass> lists= firstClassRepository.findAll();
        System.out.println(lists);
        Set<String> mysets = new HashSet<>();
        for(int i=0;i<lists.size();i++){
            mysets.add(lists.get(i).getFirstClass());
        }
        List<String> firstclassSet = new ArrayList<>(mysets);

        System.out.println(firstclassSet);


        int firstIndex = (page - 1) * pageSize;
        int lastIndex = page * pageSize;
        if (lastIndex > firstclassSet.size()){
            lastIndex = firstclassSet.size();
        }
        return firstclassSet.subList(firstIndex, lastIndex);


    }
}