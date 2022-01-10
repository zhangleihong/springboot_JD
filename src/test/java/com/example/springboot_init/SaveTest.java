package com.example.springboot_init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.model.InsertManyOptions; //導入依賴的package包/類
import com.example.springboot_init.dao.CommentRepository;
import com.example.springboot_init.dao.ProductRepository;
import com.example.springboot_init.po.Comment;
import com.example.springboot_init.po.Product;
import com.mongodb.client.MongoCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.schema.JsonSchemaProperty.string;

@SpringBootTest//通过Springboot加载上下文
@RunWith(SpringRunner.class)
public class SaveTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void contextLoads() throws ParseException {

        // String path = JsonTest.class.getClassLoader().getResource("D:\\data\\simple.json").getPath();
        String s = readJsonFile("D:\\QQStore\\1456698268\\FileRecv\\NoSQL实验_电商数据\\电商数据_for_非关系数据库系统.json");
        System.out.println(s.length());

        String[] product_separated = s.split("\n");

        // System.out.println(JSONArray.parseArray(s));

        //获取货物数组
        //JSONArray product_array = JSONArray.parseArray(s);
        //System.out.println(jsonarray);
        List<Product> lp = new ArrayList<>();
        for(int i=0;i<product_separated.length;i++){
            //获取每个货物
            if(i%100==0){
                System.out.println("目前第"+i+"条");
            }
            JSONObject product= JSON.parseObject(product_separated[i]);
            String skuid= (String) product.get("skuId");//获取skuid
            //System.out.println(product.get("price"));

            //--------------新建product对象----------------
            Product p=new Product();
            //--------------comment_items列表--------------
            List<Comment> commentList=new ArrayList<>();
            //-----------------product对象属性设置---------------
            p.setSkuId((String) product.get("skuId"));
            p.setTitle((String) product.get("title"));

            //-------------处理销量数据-------------------
            String sellcount=(String) product.get("sellCount");
            int count=0;
            if(sellcount.contains("万")){
                int n=sellcount.indexOf('万');
                double c=Double.parseDouble(sellcount.substring(0,n));
                c=c*10000;
                count= (int) c;

            }
            else if(sellcount.contains("+")){
                int n=sellcount.indexOf('+');
                count=Integer.parseInt(sellcount.substring(0,n));

            }
            else{

                try {
                    count = Integer.parseInt(sellcount);
                }
                catch (Exception e)
                {
                    count = 0;
                }
            }
            p.setSellCount(count);
            //System.out.println(sellcount+"-----"+count);
            p.setShop((String) product.get("shop"));
            //System.out.println((String)product.get("price"));
            try {
                p.setPrice(Double.parseDouble((String)product.get("price")));
            }
            catch (Exception e)
            {
                p.setPrice(null);
            }



            p.setIcon((String) product.get("icon"));
            p.setKeyword((String) product.get("keyword"));

            String ss=(String)product.get("productClass");
            String[] classes=null;
            if(ss!=null){
                classes=ss.split("-");
                p.setProductClass(classes);
                p.setFirstClass(classes[0]);
                p.setSecondClass(classes[1]);

            }
            else{
                p.setProductClass(classes);
            }

//                p.setClass_1(classes[0]);
//                p.setClass_2(classes[1]);
//                p.setClass_3(classes[2]);
//                p.setClass_4(classes[3]);


            //获取每个货物的评论数组
            JSONArray comment_array= (JSONArray) product.get("commentList");

            List<Comment> comment_items = new ArrayList<Comment>();


            //-----------------评论存储---------------------
            for(int j=0;j<comment_array.size();j++){
                //获取每个评论
                JSONObject comment=comment_array.getJSONObject(j);
                //对每个评论创建Comment对象
                Comment c=new Comment();
                c.setStar((Integer) comment.get("star"));
                c.setContent((String) comment.get("content"));
                c.setSkuId(skuid);
                c.setPlus((Boolean) comment.get("isPlus"));
                //SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                c.setTime((String) comment.get("time"));
                c.setProductType((String) comment.get("productType"));
                c.setNickname((String) comment.get("nickname"));
                //comment_id
                String comment_id=skuid.concat("_").concat(Integer.toString(j));
                c.setCommentId(comment_id);
                //System.out.println(comment_id);

                comment_items.add(c);

                //存评论
                //commentRepository.save(c);

                //对每个评论创建Comment——Item对象
//                Comment_Item item=new Comment_Item();
//                item.setCommentId(comment_id);
//                item.setStar((Integer) comment.get("star"));
//                item.setTime((String) comment.get("time"));
//                item.setProductType((String) comment.get("productType"));
//                comment_items.add(item);
                commentRepository.save(c);
            }

            p.setCommentList(comment_items);

            //lp.add(p);
            productRepository.save(p);



        }
        System.out.println("asassa");
        //mongoTemplate.insert(lp);



        //MongoCollection mongoCollection = MongoDBJDBC.getMongoDatabase("nosql","product");
        //mongoCollection.insertMany(lp);






    }
    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
