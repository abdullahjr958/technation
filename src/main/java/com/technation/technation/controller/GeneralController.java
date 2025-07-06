package com.technation.technation.controller;

import com.technation.technation.component.AuthUtil;
import com.technation.technation.model.Product;
import com.technation.technation.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GeneralController {

    private final ProductService productService;
    private final AuthUtil authUtil;

    @Autowired
    GeneralController(ProductService productService, AuthUtil authUtil){
        this.productService = productService;
        this.authUtil = authUtil;
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model){

        List<Product> searchedProducts = productService.searchProducts(query);

        model.addAttribute("searchedProducts", searchedProducts);
        model.addAttribute("query", query);
        model.addAttribute("authDTO", authUtil.getAuthDTO());
        return "search-result-page";
    }
}
