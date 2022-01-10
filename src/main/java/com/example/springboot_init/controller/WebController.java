package com.example.springboot_init.controller;

import com.example.springboot_init.po.*;
import com.example.springboot_init.service.CommentService;
import com.example.springboot_init.service.FirstClassService;
import com.example.springboot_init.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/product")
public class WebController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private FirstClassService firstClassService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Integer SearchNumber = 0;

    private Integer PageNumber = 0;

    /**
     * 前往博客首页
     * @param modelMap
     * @return
     */
    @RequestMapping("/index")
    public String gotoIndex(ModelMap modelMap){
        Page<Product> page = productService.findProductListByFirstClass("手机通讯",1,5);
        List<Product> productList = page.getContent();
        //System.out.println(page.getContent().get(1).getPrice());
        modelMap.put("products", productList);
        modelMap.put("pages", 1);
        return "index";
    }


    @RequestMapping("/class")
    public String FirstClassByPage(ModelMap modelMap){
        System.out.println("dsf");
        List<String> firstClass = firstClassService.findFirstClass(1);
        modelMap.put("firstClasses",firstClass);
        modelMap.put("pages",1);
        return "firstClass";
    }
    @RequestMapping("/page")
    public String SearchFirstClassByPage(ModelMap modelMap,@RequestParam Integer page){


        //List<String> firstClass = productService.findProductByFirstClass(page);
        List<String> firstClasses = firstClassService.findFirstClass(page);
        modelMap.put("firstClasses",firstClasses);
        modelMap.put("pages",page);
        return "/firstClass";


    }


    @RequestMapping("/api")
    public String SearchSecondClassByPage(@RequestParam("firstClass") String firstClass,ModelMap modelMap){

        List<SecondInfo> secondInfos = productService.findSecondInfo(firstClass);
        modelMap.put("secondInfos",secondInfos);
        //modelMap.put("pages",page);
        return "/secondClass";

    }


    @RequestMapping("/shop")
    public String SearchShopByFirstClass(@RequestParam("firstClass") String firstClass,ModelMap modelMap){

        List<Shop> shops = productService.shopFind(firstClass);
        modelMap.put("shops",shops);
        //modelMap.put("pages",page);
        return "/shop";

    }

    @RequestMapping("/showall")
    public String showALl(ModelMap modelMap,@RequestParam Integer page){

        List<Product> products = productService.findProduct(page);
        modelMap.put("products",products);
        modelMap.put("pages",page);
        return "/showAll";

    }

    @RequestMapping("/search")
    public String adminSearchBlog(Search search, ModelMap modelMap, RedirectAttributes attributes){
        System.out.println(search.toString());
        System.out.println("page:" + search.getPageOrSearch());
        boolean operationBlog = true;
        List<Product> productList;
        //如果是true，说明是点击搜索进行查找，false说明按照上一页下一页进行查找
        if (search.getPageOrSearch()){
            productList = productService.searchProductByTitle(search,search.getPage());
            SearchNumber++;
            PageNumber = 0;
            if (productList.isEmpty()){
                operationBlog = false;
            }
            if (SearchNumber == 1)
                modelMap.put("pages", 1);
            else
                modelMap.put("pages", search.getPage());
        }
        else{
            productList = productService.findProduct(search.getPage());
            SearchNumber = 1;
            PageNumber++;
            if (PageNumber == 0)
                modelMap.put("pages",1);
            else
                modelMap.put("pages", search.getPage());
        }
        //System.out.println("-----blogList-----"+productList.toString());

        modelMap.put("products", productList);
        if (operationBlog){
            attributes.addAttribute("message", "操作成功");
        } else {
            attributes.addAttribute("message", "操作失败");
        }
        return "/showAll :: blogList";
    }


    @RequestMapping("/sellCountShow")
    public String sellCountShow(@RequestParam("skuid") String skuid,ModelMap modelMap){
        System.out.println("来了");

        SellCount sellCount = commentService.sellCountTotal(skuid);
        modelMap.put("sellCount",sellCount);
        return "/pie-sell";

    }
    // 标题：店铺名称 x轴：店铺里的各种商品，y轴：30条评论的平均打星，销量
    @RequestMapping("/starWithSell")
    public String starWithSellCount(@RequestParam("shopName") String shopName,ModelMap modelMap){
        List<ManyInfo> manyInfos = productService.starWithSellCounts(shopName);
        System.out.println("--------"+manyInfos.size());
        System.out.println(manyInfos);
        modelMap.put("manyInfos",manyInfos);

        return "/pictorialBar-dotted";

    }




}
