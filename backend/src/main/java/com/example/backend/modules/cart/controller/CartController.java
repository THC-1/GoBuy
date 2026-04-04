package com.example.backend.modules.cart.controller;

import com.example.backend.common.api.Result;
import com.example.backend.modules.cart.service.CartService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/items")
    public Result<List<Map<String, Object>>> items() {
        return Result.success(cartService.listItems());
    }

    @PostMapping("/items")
    public Result<Void> addItem(@RequestBody Map<String, Object> request) {
        cartService.addItem(request);
        return Result.success();
    }

    @PutMapping("/items/{id}")
    public Result<Void> updateQuantity(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        cartService.updateQuantity(id, request);
        return Result.success();
    }

    @PatchMapping("/items/{id}/selected")
    public Result<Void> updateSelected(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        cartService.updateSelected(id, request);
        return Result.success();
    }

    @PatchMapping("/select-all")
    public Result<Void> selectAll(@RequestBody Map<String, Object> request) {
        cartService.selectAll(request);
        return Result.success();
    }

    @DeleteMapping("/items/{id}")
    public Result<Void> deleteItem(@PathVariable Long id) {
        cartService.deleteItem(id);
        return Result.success();
    }

    @GetMapping("/summary")
    public Result<Map<String, Object>> summary() {
        return Result.success(cartService.summary());
    }
}
