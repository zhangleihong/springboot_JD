package com.example.springboot_init.service;

import com.example.springboot_init.po.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;


import java.text.DecimalFormat;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private MongoTemplate mongoTemplate;



    @Test
    public void testFindAll(){
        System.out.println("adsfgfh");
        List<Product> list = productService.findProductList();
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }

    }
    @Test
    public void testFindByproductClass(){
        Page<Product> page = productService.findProductListByFirstClass("手机通讯",2,3);
        System.out.println(page.getTotalElements());
        System.out.println(page.getContent().get(1).getPrice());
    }
    @Test
    public void findProductByFirstClass(){
        Query query = Query.query(Criteria.where("firstClass").is("手机通讯"));
        //query.addCriteria()
        //query.fields().include("productClass");

        List<Product> lists= mongoTemplate.find(query,Product.class);
        System.out.println(lists);
        Set<String> firstclassSet = new HashSet<>();
        for(int i=0;i<lists.size();i++){
            firstclassSet.add(lists.get(i).getProductClass()[3]);
        }
        System.out.println(firstclassSet);


    }

    @Test
    public void findTem(){
        String firstClass = "手机通讯";
        Query query = Query.query(Criteria.where("firstClass").is(firstClass));
        //query.addCriteria()
        //query.fields().include("productClass");

        List<Product> lists= mongoTemplate.find(query,Product.class);
        //System.out.println(lists);
        Set<String> mysets = new HashSet<>();
        //  对二类去重
        for(int i=0;i<lists.size();i++){
            mysets.add(lists.get(i).getProductClass()[1]);
        }
        List<String> secondclassList = new ArrayList<>(mysets);
        //System.out.println(secondclassSet);
        System.out.println("secondclassSet:"+secondclassList.size());
        ArrayList<Double> pricesList = new ArrayList<>();
        List<SecondInfo> secondInfos = new ArrayList<>();
        for(int i=0;i<secondclassList.size();i++){
            System.out.println("secondclassList.get(i):   "+secondclassList.get(i));
            Query query2 = Query.query(Criteria.where("secondClass").is(secondclassList.get(i)));
            List<Product> lists2= mongoTemplate.find(query2,Product.class);

            for(int j=0;j< lists2.size();j++){
                if((lists2.get(j).getPrice())!=null){
                    pricesList.add(lists2.get(j).getPrice());
                }
            }
            System.out.println("pricesList:"+pricesList);
            Double avg = 0.0;
            pricesList.sort(Comparator.naturalOrder());
            //Collections.sort(pricesList);
            //pricesList.sort();
            for(int k=0;k<pricesList.size();k++){
                avg += pricesList.get(k);
            }
            DecimalFormat df   =new   java.text.DecimalFormat("#.00");
            avg = avg/pricesList.size();
            avg = Double.parseDouble(df.format(avg));
            Double min = pricesList.get(0);
            Double max = pricesList.get(pricesList.size()-1);
            Double mid = pricesList.get(pricesList.size()/2);
            SecondInfo secondInfo = new SecondInfo();
            secondInfo.setSecondClass(secondclassList.get(i));
            secondInfo.setAvg(avg);
            secondInfo.setMin(min);
            secondInfo.setMax(max);
            secondInfo.setMid(mid);
            secondInfos.add(secondInfo);
        }
        System.out.println(secondInfos);

        //return secondInfos;


        //return secondclassSet;
    }


    @Test
    public void shopFind(){

        String class1 = "手机通讯";
        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.match(Criteria.where("firstClass").is(class1)),
                Aggregation.group("shop").sum("sellCount").as("sellCountSum"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "sellCountSum"))

        );

        AggregationResults<HashMap> results = mongoTemplate.aggregate(aggregation,"product",HashMap.class);
        System.out.println(results.getMappedResults().size());

        List<Shop> Shops  = new ArrayList<>();

        for(int j=0;j<5;j++){
            System.out.println(results.getMappedResults().get(j).get("_id"));
            System.out.println(results.getMappedResults().get(j).get("sellCountSum"));

            Query query = Query.query(Criteria.where("shop").is(results.getMappedResults().get(j).get("_id")));


            List<Product> lists= mongoTemplate.find(query,Product.class);
            System.out.println(lists.get(0));

            Shop shop = new Shop();
            shop.setShopName(lists.get(0).getShop());
            shop.setSellCount((Integer) results.getMappedResults().get(j).get("sellCountSum"));
            shop.setIcon(lists.get(0).getIcon());
            shop.setShopProductClass(lists.get(0).getProductClass().toString());

            Shops.add(shop);




//            private String shopName;
//            private Integer sellCount;
//            private String icon;
//            private String shopProductClass;
        }
        //return Shops;


    }
    /*
    季度销售
     */
    @Test
    public void sellCountTotal(){

        SellCount sellCount = new SellCount();

        Query query = Query.query(Criteria.where("skuId").is("100016773624"));
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

    }
    /*
    销量与打星数比较
    // 标题：店铺名称 x轴：店铺里的各种商品，y轴：30条评论的平均打星，销量
     */
    @Test
    public void sellCountwithStar(){
        String shopName = "小米京东自营旗舰店";
        Query query = Query.query(Criteria.where("shop").is(shopName));
        //query.addCriteria()
        //query.fields().include("productClass");
        List<ManyInfo> manyInfos = new ArrayList<>();

        List<Product> lists= mongoTemplate.find(query,Product.class);
        for(int i=0;i<lists.size();i++){
            String productId = lists.get(i).getSkuId();
            Double star = 0.0;
            for (int j=0;j<lists.get(i).getCommentList().size();j++){
                star += lists.get(i).getCommentList().get(j).getStar();
            }
            Double starAvg = star/lists.get(i).getCommentList().size();
            Integer sellCount = lists.get(i).getSellCount();
            ManyInfo manyInfo = new ManyInfo();
            manyInfo.setShopName(shopName);
            manyInfo.setProductId(productId);
            manyInfo.setStarAvg(starAvg);
            manyInfo.setSellCount(sellCount);
            manyInfos.add(manyInfo);
        }
        System.out.println(manyInfos);
    }

}
