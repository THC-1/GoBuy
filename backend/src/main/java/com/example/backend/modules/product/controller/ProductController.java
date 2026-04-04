package com.example.backend.modules.product.controller;

import com.example.backend.common.api.Result;
import com.example.backend.modules.product.service.ProductService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/admin/product/categories/tree")
    public Result<List<Map<String, Object>>> categoryTree() {
        return Result.success(productService.getCategoryTree());
    }

    @PostMapping("/api/admin/product/categories")
    public Result<Void> createCategory(@RequestBody Map<String, Object> request) {
        productService.createCategory(request);
        return Result.success();
    }

    @PutMapping("/api/admin/product/categories/{id}")
    public Result<Void> updateCategory(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        productService.updateCategory(id, request);
        return Result.success();
    }

    @DeleteMapping("/api/admin/product/categories/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        productService.deleteCategory(id);
        return Result.success();
    }

    @GetMapping("/api/admin/product/attribute-templates")
    public Result<?> templatePage(
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "10") long pageSize,
            @RequestParam(required = false) String keyword
    ) {
        return Result.success(productService.getTemplatePage(pageNum, pageSize, keyword));
    }

    @GetMapping("/api/admin/product/attribute-templates/{id}")
    public Result<Map<String, Object>> templateDetail(@PathVariable Long id) {
        return Result.success(productService.getTemplateDetail(id));
    }

    @PostMapping("/api/admin/product/attribute-templates")
    public Result<Void> createTemplate(@RequestBody Map<String, Object> request) {
        productService.createTemplate(request);
        return Result.success();
    }

    @PutMapping("/api/admin/product/attribute-templates/{id}")
    public Result<Void> updateTemplate(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        productService.updateTemplate(id, request);
        return Result.success();
    }

    @DeleteMapping("/api/admin/product/attribute-templates/{id}")
    public Result<Void> deleteTemplate(@PathVariable Long id) {
        productService.deleteTemplate(id);
        return Result.success();
    }

    @GetMapping("/api/admin/product/spus")
    public Result<?> spuPage(
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "10") long pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status
    ) {
        return Result.success(productService.getSpuPage(pageNum, pageSize, keyword, status));
    }

    @GetMapping("/api/admin/product/spus/{id}")
    public Result<Map<String, Object>> spuDetail(@PathVariable Long id) {
        return Result.success(productService.getSpuDetail(id));
    }

    @PostMapping("/api/admin/product/spus")
    public Result<Void> createSpu(@RequestBody Map<String, Object> request) {
        productService.createSpu(request);
        return Result.success();
    }

    @PutMapping("/api/admin/product/spus/{id}")
    public Result<Void> updateSpu(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        productService.updateSpu(id, request);
        return Result.success();
    }

    @PatchMapping("/api/admin/product/spus/{id}/status")
    public Result<Void> updateSpuStatus(@PathVariable Long id, @RequestParam Integer status) {
        productService.updateSpuStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/api/admin/product/spus/{id}")
    public Result<Void> deleteSpu(@PathVariable Long id) {
        productService.deleteSpu(id);
        return Result.success();
    }

    @GetMapping("/api/products/recommend")
    public Result<List<Map<String, Object>>> recommendProducts() {
        return Result.success(productService.getRecommendProducts());
    }

    @GetMapping("/api/products/{id}")
    public Result<Map<String, Object>> productDetail(@PathVariable Long id) {
        return Result.success(productService.getProductDetail(id));
    }
}
