package com.example.backend.modules.order.controller;

import com.example.backend.common.api.Result;
import com.example.backend.modules.order.service.OrderService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/confirm")
    public Result<Map<String, Object>> confirm() {
        return Result.success(orderService.confirmOrder());
    }

    @PostMapping
    public Result<Map<String, Object>> create(@RequestBody Map<String, Object> request) {
        return Result.success(orderService.createOrder(request));
    }

    @GetMapping("/my")
    public Result<List<Map<String, Object>>> myOrders() {
        return Result.success(orderService.listMyOrders());
    }

    @GetMapping("/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id) {
        return Result.success(orderService.getOrderDetail(id));
    }

    @PostMapping("/{id}/pay")
    public Result<Map<String, Object>> pay(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        return Result.success(orderService.pay(id, request));
    }
}
