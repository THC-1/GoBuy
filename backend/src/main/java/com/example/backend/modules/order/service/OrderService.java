package com.example.backend.modules.order.service;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Map<String, Object> confirmOrder();

    Map<String, Object> createOrder(Map<String, Object> request);

    List<Map<String, Object>> listMyOrders();

    Map<String, Object> getOrderDetail(Long id);

    Map<String, Object> pay(Long id, Map<String, Object> request);
}
