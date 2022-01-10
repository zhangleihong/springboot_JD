package com.example.springboot_init;

import com.example.springboot_init.dao.FirstClassRepository;
import com.example.springboot_init.po.FirstClass;
import com.example.springboot_init.po.Product;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@SpringBootTest//通过Springboot加载上下文
@RunWith(SpringRunner.class)
public class FirstTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private FirstClassRepository firstClassRepository;

    @Test
    public void contextLoads() {

        FirstClass ft = new FirstClass();

        List<Product> lists= mongoTemplate.findAll(Product.class);
        //System.out.println(lists);
        Set<String> mysets = new HashSet<>();
        for(int i=0;i<lists.size();i++){
            if((lists.get(i).getFirstClass())!=null)
            mysets.add(lists.get(i).getFirstClass());
        }
        List<String> firstclassSet = new ArrayList<>(mysets);
        System.out.println(firstclassSet);
        for(int i=0;i<firstclassSet.size();i++){
            ft.setFirstClass(firstclassSet.get(i));
            firstClassRepository.save(ft);
        }
    }


}
