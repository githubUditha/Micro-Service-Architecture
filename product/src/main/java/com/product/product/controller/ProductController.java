package com.product.product.controller;

import com.product.product.dto.ProductDTO;
import com.product.product.model.Product;
import com.product.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value="api/v1/")
public class ProductController {
    private ProductService productService;

    @GetMapping("/getproducts")
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/product/{productId}")
    public ProductDTO getProductById(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }

    @PostMapping("/addproduct")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO){
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/updateproduct")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }

    @DeleteMapping("/deleteproduct")
    public String deleteProduct(Integer productId){
        return productService.deleteProduct(productId);
    }
}
