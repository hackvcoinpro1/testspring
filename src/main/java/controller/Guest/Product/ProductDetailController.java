/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Guest.Product;

import model.Pages;
import model.CommentForm;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.*;
import entity.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;
import repository.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import utils.IMGUtils;
import repository.ProductcommentRepository;

@Controller

public class ProductDetailController implements Serializable {

    @Autowired
    GroupcategoriesRepository groupcategoriesRepository;

    @Autowired
    ShopRepository shopRepository;
    @Autowired
    ProductcommentRepository productcommentRepository;
    @Autowired
    ProductsRepository productsRepository;

    @PostConstruct
    public void init() {

    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));

    }

//    ====================================***HandMaping***=======================================================>>
//    ====================================***ACCOUNT***=======================================================>>
//    ====================================***PRODUCT***=======================================================>>
    @RequestMapping(value = {"/Public/setupShowDetailProduct"}, method = RequestMethod.GET)
    public String setupShowDetailProduct(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "itemperpage", defaultValue = "5", required = false) Integer itemperpage,
            HttpServletRequest request, HttpSession session, ModelMap mm) {

        try {
            setupShowDetailProduct(id, mm,itemperpage);
            setupCategoriesBar(mm);
        } catch (Exception e) {
            e.getMessage();
        }
        return "/Public/ProductDetail";
    }
//    =======================================***METHOD***=====================================================>>

//    =======================================***PRODUCT***=====================================================>>
    public void setupShowDetailProduct(int id, ModelMap mm,int itemperpage) {
        Product p = null;
        List<Shop> ls = new ArrayList<>();
        try {
            int page = 1;
            int statuscommentid = 2;//2 = mở
            p = productsRepository.findOne(id);

            ls = shopRepository.findDistinctByUserId_EnabledAndCategoryList_IsActiveAndProductList_IsActiveAndProductList_ProductNameContaining(1, 1, 1, p.getProductName());

            mm.addAttribute("listShopSellProduct", ls);
            mm.addAttribute("product", p);
            mm.addAttribute("commentForm", new CommentForm());

            PageRequest pageRequest;

            pageRequest = new PageRequest(page - 1, itemperpage, Sort.Direction.DESC, "DateCreated");
            Page<Productcomment> pager
                    = productcommentRepository.findByProductId_productIdAndStatusCommentId_Statuscommentid(pageRequest, id, statuscommentid);
            Pages pages = new Pages(pager);
            mm.addAttribute("productcomments", pages);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void setupCategoriesBar(ModelMap mm) {

        try {
            mm.addAttribute("listShops", shopRepository.findByUserId_Enabled(1));
            mm.addAttribute("listGroupcategories", groupcategoriesRepository.findAll());
        } catch (Exception e) {
        }

    }

//    ====================================***ACCOUNT***=======================================================>>
//    =======================================***get/set***=====================================================>>
}
