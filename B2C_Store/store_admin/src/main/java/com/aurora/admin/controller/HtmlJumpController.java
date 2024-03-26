package com.aurora.admin.controller;

import com.aurora.clients.CategoryClient;
import com.aurora.pojo.Category;
import com.aurora.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @ author AuroraCjt
 * @ date 2024/3/24 12:34
 * @ DESCRIPTION 后台管理服务 页面跳转 控制器类
 */
@Slf4j
@Controller
@RequestMapping         //此处的根路径 已经写到application.yml配置中 项目根路径为admin
public class HtmlJumpController {

    @Autowired
    private CategoryClient categoryClient;

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:37
     * @ param
     * @ return login 登录页面
     * @ description 设计欢迎页面跳转controller
     */
    @GetMapping({"/","index.html","index"})
    public String  welcome(){
        log.info("HtmlJumpController.welcome 跳转登录页面!");
        return "login";
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:39
     * @ return
     * @ description 登录成功跳转到index页面!
     */
    @GetMapping("/home")
    public String home(){
        log.info("HtmlJumpController.home登录成功,跳转程序首页!index页面!");
        return "index";
    }
    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:39
     * @ return
     * @ description 跳转用户管理页面
     */
    @GetMapping("/user")
    public String user(){
        log.info("HtmlJumpController.user,跳转用户管理!user页面!");
        return "user/user";
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:40
     * @ return
     * @ description 跳转商品管理页面
     */
    @GetMapping("/product")
    public String product(){
        log.info("HtmlJumpController.product,跳转商品管理!product页面!");
        return "product/product";
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:40
     * @ return
     * @ description 跳转类别管理页面
     */
    @GetMapping("/category")
    public String category(){
        log.info("HtmlJumpController.category,跳转用户管理!category页面!");
        return "category/category";
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:40
     * @ return
     * @ description 跳转订单管理页面
     */
    @GetMapping("/order")
    public String order(){
        log.info("HtmlJumpController.order,跳转用户管理!order页面!");
        return "order/order";
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:40
     * @ return
     * @ description 打开编辑用户页面
     */
    @GetMapping("/user/update/html")
    public String userUpdateHtml(){
        log.info("HtmlJumpController.userUpdateHtml业务结束，结果:{}");
        return "user/edit";
    }


    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:40
     * @ return
     * @ description 打开编辑用户页面
     */
    @GetMapping("/user/save/html")
    public String userSaveHtml(){
        log.info("HtmlJumpController.userSaveHtml业务结束，结果:{}");
        return "user/add";
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:40
     * @ return
     * @ description 打开编辑类别页面
     */
    @GetMapping("/category/update/html")
    public String categoryUpdateHtml(){
        log.info("HtmlJumpController.categoryUpdateHtml业务结束，结果:{}");
        return "category/edit";
    }

    @GetMapping("/category/save/html")
    public String categorySaveHtml(){
        log.info("HtmlJumpController.categorySaveHtml结束，结果:{}");
        return "category/add";
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:40
     * @ return
     * @ description 商品保存页面跳转
     */
    @GetMapping("/product/save/html")
    public String productSaveHtml(Model model){
        log.info("HtmlJumpController.productSaveHtml业务结束，结果:{}");

        //查询类别列表,存入共享域
        R r = categoryClient.list();
        List<LinkedHashMap> data = (List<LinkedHashMap>) r.getData();

        List<Category> categoryList = new ArrayList<>();

        for (LinkedHashMap map : data) {
            Category category = new Category();
            category.setCategoryId((Integer) map.get("category_id"));
            category.setCategoryName((String) map.get("category_name"));
            categoryList.add(category);
        }

        model.addAttribute("clist",categoryList);
        return "product/add";
    }

    /**
     * @ author AuroraCjt
     * @ date 2024/3/24 12:41
     * @ return
     * @ description 商品修改页面跳转
     */
    @GetMapping("/product/update/html")
    public String productUpdateHtml(Model model){
        log.info("HtmlJumpController.productUpdateHtml业务结束，结果:{}");

        //查询类别列表,存入共享域
        R r = categoryClient.list();
        List<LinkedHashMap> data = (List<LinkedHashMap>) r.getData();

        List<Category> categoryList = new ArrayList<>();

        for (LinkedHashMap map : data) {
            Category category = new Category();
            category.setCategoryId((Integer) map.get("category_id"));
            category.setCategoryName((String) map.get("category_name"));
            categoryList.add(category);
        }

        model.addAttribute("clist",categoryList);
        return "product/edit";
    }
}
