package com.example.springboot_init.service;

import com.example.springboot_init.dao.ProductRepository;
import com.example.springboot_init.dao.ProductRepository;
import com.example.springboot_init.po.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@Slf4j
public class ProductService {
    //注入dao
    @Autowired
    private  ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Integer pageSize = 10;

    /**
     *  保存一个评论
     *  @param  product */
    public void saveProduct(Product product){
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        //设置一些默认初始值。。。
        //调用dao
        productRepository.save(product);
    }

    /**
     *  更新评论
     *  @param  product */
    public void updateComment(Product product){
        //调用dao
        productRepository.save(product);
    }

    /**
     *  根据id删除评论*  @param  id
     */
    public void deleteCommentById(String id){
        //调用dao
        productRepository.deleteById(id);
    }

    /**
     *  查询所有评论* @return
     */
    public List<Product> findProductList(){
        //调用dao
        return productRepository.findAll(); }

    /**
     *  根据id查询评论*  @param  id
     * @return */
    public Product findCommentById(String id){
        //调用dao
        return productRepository.findById(id).get(); }

    public Page<Product> findProductListByFirstClass(String firstClass,int page,int size){
        return productRepository.findByFirstClass(firstClass, PageRequest.of(page-1,size));
    }

    /**
     *  查询所有商品第一类别
     */
    public List<String> findProductByFirstClass(Integer page){

        List<Product> lists= mongoTemplate.findAll(Product.class);
        //System.out.println(lists);
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
    //没用上
    public List<String> findProductBySecondClass(String firstClass){
        Query query = Query.query(Criteria.where("firstClass").is(firstClass));
        //query.addCriteria()
        //query.fields().include("productClass");

        List<Product> lists= mongoTemplate.find(query,Product.class);
        //System.out.println(lists);
        Set<String> mysets = new HashSet<>();
        for(int i=0;i<lists.size();i++){
            mysets.add(lists.get(i).getProductClass()[1]);
        }
        List<String> secondclassSet = new ArrayList<>(mysets);
        //System.out.println(secondclassSet);
        for(int i=0;i<secondclassSet.size();i++){
//            Criteria criteria = Criteria.where("product.").is(first)
//                    .andOperator(Criteria.where("productClass.1").is(second));
//            GroupOperation gop = Aggregation.group().max("price").as("price");
//            Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria), gop);
//            AggregationResults<GoodsData> ar = mongoTemplate.aggregate(aggregation, "GoodsData", GoodsData.class);
//            List<GoodsData> list = ar.getMappedResults();
        }


        return secondclassSet;


    }

    //查找二类
    public List<SecondInfo> findSecondInfo(String firstClass){

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
            //System.out.println("pricesList:"+pricesList);
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


        return secondInfos;


        //return secondclassSet;
    }



    //展示热点商店
    public List<Shop> shopFind(String class1){

        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.match(Criteria.where("firstClass").is(class1)),
                Aggregation.group("shop").sum("sellCount").as("sellCountSum"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "sellCountSum"))

        );

        //System.out.wprintln(mongoTemplate.aggregate(aggregation,"product", HashMap.class));
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
            //将String[] 转化为字符串
            String[] lytype= lists.get(0).getProductClass();
            StringBuilder sb = new StringBuilder();
            if (lytype != null && lytype.length > 0) {
                for (int i = 0; i < lytype.length; i++) {
                    if (i < lytype.length - 1) {
                        sb.append(lytype[i] + ",");
                    } else {
                        sb.append(lytype[i]);
                    }
                }
            }
            String lytype1 = sb.toString();
            shop.setShopProductClass(lytype1);

            Shops.add(shop);




//            private String shopName;
//            private Integer sellCount;
//            private String icon;
//            private String shopProductClass;
        }
        return Shops;


    }



    public List<Product> findProduct(Integer page){
        //Query query = Query.query(Criteria.where("firstClass").is("手机通讯"));
        //query.addCriteria()
        //query.fields().include("productClass");

        //List<Product> lists= mongoTemplate.find(query,Product.class);
        List<Product> lists= mongoTemplate.findAll(Product.class);
        //System.out.println(lists);



        int firstIndex = (page - 1) * pageSize;
        int lastIndex = page * pageSize;
        if (lastIndex > lists.size()){
            lastIndex = lists.size();
        }
        return lists.subList(firstIndex, lastIndex);


    }


    public List<Product> searchProductByTitle(Search search,Integer page){


        Query query = Query.query(Criteria.where("title").regex(".*?\\" + search.getTitle()+ ".*"));

        List<Product> lists= mongoTemplate.find(query,Product.class);
        System.out.println(lists.size());

        int firstIndex = (page - 1) * pageSize;
        int lastIndex = page * pageSize;
        if (lastIndex > lists.size()){
            lastIndex = lists.size();
        }
        return lists.subList(firstIndex, lastIndex);


    }

    public List<ManyInfo> starWithSellCounts(String shopName){
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
            System.out.println(lists.get(i).getTitle());
            ManyInfo manyInfo = new ManyInfo();
            manyInfo.setShopName(shopName);
            manyInfo.setProductId(productId);
            manyInfo.setStarAvg(starAvg);
            manyInfo.setSellCount(sellCount);
            manyInfos.add(manyInfo);
        }
        return manyInfos;
    }

}
