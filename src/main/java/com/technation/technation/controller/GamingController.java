package com.technation.technation.controller;

import com.technation.technation.component.AuthUtil;
import com.technation.technation.dto.CarousalImgDTO;
import com.technation.technation.dto.ProductDTO;
import com.technation.technation.dto.SpecsImgDTO;
import com.technation.technation.dto.SpecsValueDTO;
import com.technation.technation.model.CartItem;
import com.technation.technation.model.Category;
import com.technation.technation.service.CategoryService;
import com.technation.technation.service.CarousalImgService;
import com.technation.technation.service.ProductService;
import com.technation.technation.service.SpecsImgService;
import com.technation.technation.service.SpecsValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/gaming")
public class GamingController {

    private final AuthUtil authUtil;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final SpecsValueService specsValueService;
    private final SpecsImgService specsImgService;
    private final CarousalImgService carousalImgService;

    @Autowired
    GamingController(AuthUtil authUtil, CategoryService categoryService, ProductService productService, SpecsValueService specsValueService, SpecsImgService specsImgService, CarousalImgService carousalImgService){
        this.authUtil = authUtil;
        this.categoryService = categoryService;
        this.productService = productService;
        this.specsValueService = specsValueService;
        this.specsImgService = specsImgService;
        this.carousalImgService = carousalImgService;
    }

    @GetMapping("/home")
    public String showGamingHomePage(Model model){
        model.addAttribute("bestSellingList", productService.getGamingBestSelling());
        model.addAttribute("authDTO", authUtil.getAuthDTO());
        return "gaming-home";
    }

    @GetMapping("/category/{categoryId}")
    public String showGamingListingPage(@PathVariable int categoryId, Model model){

        Category category = categoryService.getCategoryById(categoryId);
        model.addAttribute("productList", productService.getProductsByCategoryId(categoryId));
        model.addAttribute("category", category);
        model.addAttribute("authDTO", authUtil.getAuthDTO());

        return "gaming-listing-page";
    }

    @GetMapping("/product-details/{prodId}")
    public String showGamingDetailsPage(@PathVariable int prodId, Model model){

        ProductDTO productDTO = new ProductDTO(productService.getProductById(prodId));

        //See its implementation in Universal Chat
        List<SpecsValueDTO> specsValuesDTO = specsValueService.getSpecsValueByProductId(prodId);
        List<CarousalImgDTO> carousalImagesDTO = carousalImgService.getCarousalImgByProductId(prodId);
        List<SpecsImgDTO> specsImagesDTO = specsImgService.getSpecsImgByProductId(prodId);

        model.addAttribute("product", productDTO);
        model.addAttribute("descriptionPoints", Arrays.asList(productDTO.getDescription().split("\\|")));
        model.addAttribute("specsValuesList", specsValuesDTO);
        model.addAttribute("carousalImages", carousalImagesDTO);
        model.addAttribute("specsImages", specsImagesDTO);
        model.addAttribute("authDTO", authUtil.getAuthDTO());

        model.addAttribute("cartItemObj", new CartItem());

        return "gaming-details-page";
    }
}
