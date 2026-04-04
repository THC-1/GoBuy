package com.example.backend.modules.cart.service;

import java.util.List;
import java.util.Map;

public interface CartService {

    List<Map<String, Object>> listItems();

    void addItem(Map<String, Object> request);

    void updateQuantity(Long id, Map<String, Object> request);

    void updateSelected(Long id, Map<String, Object> request);

    void selectAll(Map<String, Object> request);

    void deleteItem(Long id);

    Map<String, Object> summary();
}
