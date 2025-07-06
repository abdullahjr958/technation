package com.technation.technation.service;

import com.technation.technation.dto.ProductDTO;
import com.technation.technation.model.Product;
import com.technation.technation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository prodRepo;

    @Autowired
    ProductService(ProductRepository prodRepo){
        this.prodRepo = prodRepo;
    }

    public Product getProductById(int id){
        return prodRepo.findById(id).orElse(new Product());
    }

    public List<ProductDTO> getProductsByCategoryId(int categoryId){
        return prodRepo.findByCategoryId(categoryId)
                .stream()
                .map(ProductDTO :: new)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByBrandId(int brandId){
        return prodRepo.findByBrandId(brandId)
                .stream()
                .map(ProductDTO :: new)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getBestSelling(){
        List<Product> bestSelling = new ArrayList<>();
        for(int i = 1; i <= 60; i += 7){
            Product product = prodRepo.findById(i).orElse(new Product());
            bestSelling.add(product);
        }
        return bestSelling
                .stream()
                .map(ProductDTO :: new)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getGamingBestSelling(){
        List<Product> gamingBestSelling = new ArrayList<>();
        for(int i = 61; i <= 90; i += 5){
            Product product = prodRepo.findById(i).orElse(new Product());
            gamingBestSelling.add(product);
        }
        return gamingBestSelling
                .stream()
                .map(ProductDTO :: new)
                .collect(Collectors.toList());
    }

    //For getting products user searched for
    public List<Product> searchProducts(String keyword) {
        return prodRepo.findByNameContainingIgnoreCase(keyword);
    }
}
