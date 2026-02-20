package com.billmaster.product.controller;

import com.billmaster.product.dto.ProductRequest;
import com.billmaster.product.dto.ProductResponse;
import com.billmaster.product.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponse> getBySku(@PathVariable String sku) {
        return ResponseEntity.ok(productService.getProductBySku(sku));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/pdf")
    public void exportProductsToPDF(HttpServletResponse response) throws Exception {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=products.pdf");

        productService.exportProductsToPDF(response);
    }
    @PostMapping("/import/csv")
    public ResponseEntity<String> importCSV(@RequestParam("file") MultipartFile file) {

        String message = productService.importProductsFromCSV(file);

        return ResponseEntity.ok(message);
    }
@PutMapping("/{id}")
public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable String id,
        @RequestBody ProductRequest request) {

    return ResponseEntity.ok(
            productService.updateProduct(id, request)
    );
}

}