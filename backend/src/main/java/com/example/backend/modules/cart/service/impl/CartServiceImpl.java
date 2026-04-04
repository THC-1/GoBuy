package com.example.backend.modules.cart.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.backend.common.api.ResultCode;
import com.example.backend.common.exception.BusinessException;
import com.example.backend.common.util.SecurityUtils;
import com.example.backend.modules.cart.entity.CartItem;
import com.example.backend.modules.cart.mapper.CartItemMapper;
import com.example.backend.modules.cart.service.CartService;
import com.example.backend.modules.product.service.ProductService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemMapper cartItemMapper;
    private final ProductService productService;

    public CartServiceImpl(CartItemMapper cartItemMapper, ProductService productService) {
        this.cartItemMapper = cartItemMapper;
        this.productService = productService;
    }

    @Override
    public List<Map<String, Object>> listItems() {
        return cartItemMapper.selectList(
                Wrappers.<CartItem>lambdaQuery()
                        .eq(CartItem::getUserId, currentUserId())
                        .eq(CartItem::getStatus, 1)
                        .orderByDesc(CartItem::getId)
        ).stream().map(this::toCartItemMap).toList();
    }

    @Override
    @Transactional
    public void addItem(Map<String, Object> request) {
        Long skuId = longValue(request.get("skuId"));
        Long spuId = longValue(request.get("productId"));
        if (skuId == 0L) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "skuId不能为空");
        }
        Map<String, Object> sku = productService.getSkuSnapshot(skuId);
        if (spuId == 0L) {
            spuId = longValue(sku.get("spuId"));
        }
        CartItem cartItem = cartItemMapper.selectOne(
                Wrappers.<CartItem>lambdaQuery()
                        .eq(CartItem::getUserId, currentUserId())
                        .eq(CartItem::getSkuId, skuId)
                        .last("limit 1")
        );
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setUserId(currentUserId());
            cartItem.setSpuId(spuId);
            cartItem.setSkuId(skuId);
            cartItem.setQuantity(intValue(request.get("quantity"), 1));
            cartItem.setSelected(1);
            cartItem.setStatus(1);
            cartItem.setCreatedAt(LocalDateTime.now());
            cartItem.setUpdatedAt(LocalDateTime.now());
            cartItemMapper.insert(cartItem);
            return;
        }
        cartItem.setQuantity(cartItem.getQuantity() + intValue(request.get("quantity"), 1));
        cartItem.setSelected(1);
        cartItem.setUpdatedAt(LocalDateTime.now());
        cartItemMapper.updateById(cartItem);
    }

    @Override
    public void updateQuantity(Long id, Map<String, Object> request) {
        CartItem cartItem = requireCartItem(id);
        cartItem.setQuantity(intValue(request.get("quantity"), cartItem.getQuantity()));
        cartItem.setUpdatedAt(LocalDateTime.now());
        cartItemMapper.updateById(cartItem);
    }

    @Override
    public void updateSelected(Long id, Map<String, Object> request) {
        CartItem cartItem = requireCartItem(id);
        cartItem.setSelected(booleanValue(request.get("selected")) ? 1 : 0);
        cartItem.setUpdatedAt(LocalDateTime.now());
        cartItemMapper.updateById(cartItem);
    }

    @Override
    public void selectAll(Map<String, Object> request) {
        boolean selected = booleanValue(request.get("selected"));
        List<CartItem> items = cartItemMapper.selectList(
                Wrappers.<CartItem>lambdaQuery().eq(CartItem::getUserId, currentUserId()).eq(CartItem::getStatus, 1)
        );
        for (CartItem item : items) {
            item.setSelected(selected ? 1 : 0);
            item.setUpdatedAt(LocalDateTime.now());
            cartItemMapper.updateById(item);
        }
    }

    @Override
    public void deleteItem(Long id) {
        cartItemMapper.delete(Wrappers.<CartItem>lambdaQuery().eq(CartItem::getId, id).eq(CartItem::getUserId, currentUserId()));
    }

    @Override
    public Map<String, Object> summary() {
        List<Map<String, Object>> items = listItems();
        int selectedCount = items.stream()
                .filter(item -> Boolean.TRUE.equals(item.get("selected")))
                .mapToInt(item -> (Integer) item.get("quantity"))
                .sum();
        BigDecimal totalPrice = items.stream()
                .filter(item -> Boolean.TRUE.equals(item.get("selected")))
                .map(item -> ((BigDecimal) item.get("price")).multiply(BigDecimal.valueOf((Integer) item.get("quantity"))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return Map.of(
                "selectedCount", selectedCount,
                "totalPrice", totalPrice,
                "freightAmount", BigDecimal.ZERO,
                "payAmount", totalPrice
        );
    }

    private Map<String, Object> toCartItemMap(CartItem item) {
        Map<String, Object> product = productService.getSpuSnapshot(item.getSpuId());
        Map<String, Object> sku = productService.getSkuSnapshot(item.getSkuId());
        Map<String, Object> result = new java.util.LinkedHashMap<>();
        result.put("id", item.getId());
        result.put("productId", item.getSpuId());
        result.put("skuId", item.getSkuId());
        result.put("name", product.get("name"));
        result.put("sku", sku.get("skuText"));
        result.put("skuText", sku.get("skuText"));
        result.put("price", sku.get("price"));
        result.put("quantity", item.getQuantity());
        result.put("image", sku.get("image"));
        result.put("selected", item.getSelected() == 1);
        result.put("stock", sku.get("stock"));
        return result;
    }

    private CartItem requireCartItem(Long id) {
        CartItem cartItem = cartItemMapper.selectOne(
                Wrappers.<CartItem>lambdaQuery().eq(CartItem::getId, id).eq(CartItem::getUserId, currentUserId()).last("limit 1")
        );
        if (cartItem == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "购物项不存在");
        }
        return cartItem;
    }

    private Long currentUserId() {
        return SecurityUtils.getLoginUser().getUserId();
    }

    private Long longValue(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return 0L;
        }
        return Long.parseLong(String.valueOf(value));
    }

    private Integer intValue(Object value, Integer defaultValue) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return defaultValue;
        }
        return Integer.parseInt(String.valueOf(value));
    }

    private boolean booleanValue(Object value) {
        return value != null && ("true".equalsIgnoreCase(String.valueOf(value)) || "1".equals(String.valueOf(value)));
    }
}
