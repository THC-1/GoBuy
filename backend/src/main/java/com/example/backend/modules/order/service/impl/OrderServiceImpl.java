package com.example.backend.modules.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.backend.common.api.ResultCode;
import com.example.backend.common.exception.BusinessException;
import com.example.backend.common.util.SecurityUtils;
import com.example.backend.modules.cart.entity.CartItem;
import com.example.backend.modules.cart.mapper.CartItemMapper;
import com.example.backend.modules.order.entity.OrderInfo;
import com.example.backend.modules.order.entity.OrderItem;
import com.example.backend.modules.order.mapper.OrderInfoMapper;
import com.example.backend.modules.order.mapper.OrderItemMapper;
import com.example.backend.modules.order.service.OrderService;
import com.example.backend.modules.product.mapper.ProductSkuMapper;
import com.example.backend.modules.product.service.ProductService;
import com.example.backend.modules.user.entity.MallUserAddress;
import com.example.backend.modules.user.mapper.MallUserAddressMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String STATUS_PENDING_PAYMENT = "PENDING_PAYMENT";
    private static final String STATUS_PAID = "PAID";
    private static final String STATUS_COMPLETED = "COMPLETED";

    private final OrderInfoMapper orderInfoMapper;
    private final OrderItemMapper orderItemMapper;
    private final MallUserAddressMapper mallUserAddressMapper;
    private final CartItemMapper cartItemMapper;
    private final ProductService productService;
    private final ProductSkuMapper productSkuMapper;

    public OrderServiceImpl(
            OrderInfoMapper orderInfoMapper,
            OrderItemMapper orderItemMapper,
            MallUserAddressMapper mallUserAddressMapper,
            CartItemMapper cartItemMapper,
            ProductService productService,
            ProductSkuMapper productSkuMapper
    ) {
        this.orderInfoMapper = orderInfoMapper;
        this.orderItemMapper = orderItemMapper;
        this.mallUserAddressMapper = mallUserAddressMapper;
        this.cartItemMapper = cartItemMapper;
        this.productService = productService;
        this.productSkuMapper = productSkuMapper;
    }

    @Override
    public Map<String, Object> confirmOrder() {
        List<Map<String, Object>> addresses = listAddressMaps();
        List<Map<String, Object>> items = loadCheckoutItems(null);
        BigDecimal goodsAmount = totalAmount(items);
        return Map.of(
                "addresses", addresses,
                "defaultAddressId", addresses.stream().filter(item -> Boolean.TRUE.equals(item.get("isDefault"))).findFirst().map(item -> item.get("id")).orElse(null),
                "items", items,
                "goodsAmount", goodsAmount,
                "freightAmount", BigDecimal.ZERO,
                "discountAmount", BigDecimal.ZERO,
                "payAmount", goodsAmount
        );
    }

    @Override
    @Transactional
    public Map<String, Object> createOrder(Map<String, Object> request) {
        MallUserAddress address = requireAddress(longValue(request.get("addressId")));
        List<Map<String, Object>> items = loadCheckoutItems(request.get("items"));
        if (items.isEmpty()) {
            throw new BusinessException(ResultCode.BUSINESS_ERROR, "没有可提交的商品");
        }
        for (Map<String, Object> item : items) {
            int updated = productSkuMapper.deductStock(longValue(item.get("skuId")), (Integer) item.get("quantity"));
            if (updated == 0) {
                throw new BusinessException(ResultCode.BUSINESS_ERROR, item.get("name") + "库存不足");
            }
        }
        BigDecimal goodsAmount = totalAmount(items);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(currentUserId());
        orderInfo.setOrderNo(generateOrderNo());
        orderInfo.setOrderStatus(STATUS_PENDING_PAYMENT);
        orderInfo.setPaymentMethod(stringValue(request.get("paymentMethod")));
        orderInfo.setReceiverName(address.getReceiverName());
        orderInfo.setReceiverPhone(address.getReceiverPhone());
        orderInfo.setReceiverAddress(address.getProvince() + " " + address.getCity() + " " + address.getDistrict() + " " + address.getDetailAddress());
        orderInfo.setRemark(stringValue(request.get("remark")));
        orderInfo.setGoodsAmount(goodsAmount);
        orderInfo.setFreightAmount(BigDecimal.ZERO);
        orderInfo.setDiscountAmount(BigDecimal.ZERO);
        orderInfo.setPayAmount(goodsAmount);
        orderInfo.setCreatedAt(LocalDateTime.now());
        orderInfo.setUpdatedAt(LocalDateTime.now());
        orderInfoMapper.insert(orderInfo);

        for (Map<String, Object> item : items) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderInfo.getId());
            orderItem.setSpuId(longValue(item.get("productId")));
            orderItem.setSkuId(longValue(item.get("skuId")));
            orderItem.setProductName(String.valueOf(item.get("name")));
            orderItem.setSkuText(String.valueOf(item.get("sku")));
            orderItem.setProductImage(String.valueOf(item.get("image")));
            orderItem.setSalePrice((BigDecimal) item.get("price"));
            orderItem.setQuantity((Integer) item.get("quantity"));
            orderItem.setTotalAmount(((BigDecimal) item.get("price")).multiply(BigDecimal.valueOf((Integer) item.get("quantity"))));
            orderItem.setCreatedAt(LocalDateTime.now());
            orderItem.setUpdatedAt(LocalDateTime.now());
            orderItemMapper.insert(orderItem);
        }

        clearSelectedCartItems();
        return Map.of(
                "orderId", orderInfo.getId(),
                "orderNo", orderInfo.getOrderNo(),
                "status", orderInfo.getOrderStatus(),
                "payAmount", orderInfo.getPayAmount()
        );
    }

    @Override
    public List<Map<String, Object>> listMyOrders() {
        return orderInfoMapper.selectList(
                Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getUserId, currentUserId()).orderByDesc(OrderInfo::getId)
        ).stream().map(order -> Map.<String, Object>of(
                "orderId", order.getId(),
                "orderNo", order.getOrderNo(),
                "status", order.getOrderStatus(),
                "payAmount", order.getPayAmount(),
                "createdAt", order.getCreatedAt(),
                "items", listOrderItems(order.getId())
        )).toList();
    }

    @Override
    public Map<String, Object> getOrderDetail(Long id) {
        OrderInfo order = requireOrder(id);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("orderId", order.getId());
        result.put("orderNo", order.getOrderNo());
        result.put("status", order.getOrderStatus());
        result.put("payAmount", order.getPayAmount());
        result.put("paymentMethod", stringValue(order.getPaymentMethod()));
        result.put("receiverName", order.getReceiverName());
        result.put("receiverPhone", order.getReceiverPhone());
        result.put("receiverAddress", order.getReceiverAddress());
        result.put("remark", stringValue(order.getRemark()));
        result.put("items", listOrderItems(order.getId()));
        result.put("createdAt", order.getCreatedAt());
        result.put("payTime", order.getPayTime());
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> pay(Long id, Map<String, Object> request) {
        OrderInfo order = requireOrder(id);
        order.setPaymentMethod(StringUtils.hasText(stringValue(request.get("paymentMethod"))) ? stringValue(request.get("paymentMethod")) : order.getPaymentMethod());
        order.setOrderStatus(STATUS_PAID);
        order.setPayTime(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderInfoMapper.updateById(order);
        return Map.of(
                "orderId", order.getId(),
                "status", order.getOrderStatus(),
                "paidTime", order.getPayTime()
        );
    }

    private List<Map<String, Object>> listAddressMaps() {
        return mallUserAddressMapper.selectList(
                Wrappers.<MallUserAddress>lambdaQuery().eq(MallUserAddress::getUserId, currentUserId()).eq(MallUserAddress::getStatus, 1)
                        .orderByDesc(MallUserAddress::getIsDefault).orderByDesc(MallUserAddress::getId)
        ).stream().map(address -> Map.<String, Object>of(
                "id", address.getId(),
                "name", address.getReceiverName(),
                "phone", address.getReceiverPhone(),
                "province", address.getProvince(),
                "city", address.getCity(),
                "district", address.getDistrict(),
                "detail", address.getDetailAddress(),
                "isDefault", address.getIsDefault() == 1
        )).toList();
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> loadCheckoutItems(Object itemSource) {
        if (itemSource instanceof Collection<?> collection && !collection.isEmpty()) {
            List<Map<String, Object>> items = new ArrayList<>();
            for (Object entry : collection) {
                if (!(entry instanceof Map<?, ?> raw)) {
                    continue;
                }
                Map<String, Object> requestItem = (Map<String, Object>) raw;
                Map<String, Object> sku = productService.getSkuSnapshot(longValue(requestItem.get("skuId")));
                Map<String, Object> product = productService.getSpuSnapshot(longValue(sku.get("spuId")));
                items.add(Map.of(
                        "productId", longValue(product.get("id")),
                        "skuId", longValue(sku.get("id")),
                        "name", product.get("name"),
                        "sku", sku.get("skuText"),
                        "price", sku.get("price"),
                        "quantity", intValue(requestItem.get("quantity"), 1),
                        "image", sku.get("image")
                ));
            }
            return items;
        }
        return cartItemMapper.selectList(
                Wrappers.<CartItem>lambdaQuery()
                        .eq(CartItem::getUserId, currentUserId())
                        .eq(CartItem::getStatus, 1)
                        .eq(CartItem::getSelected, 1)
                        .orderByDesc(CartItem::getId)
        ).stream().map(item -> {
            Map<String, Object> product = productService.getSpuSnapshot(item.getSpuId());
            Map<String, Object> sku = productService.getSkuSnapshot(item.getSkuId());
            return Map.<String, Object>of(
                    "productId", item.getSpuId(),
                    "skuId", item.getSkuId(),
                    "name", product.get("name"),
                    "sku", sku.get("skuText"),
                    "price", sku.get("price"),
                    "quantity", item.getQuantity(),
                    "image", sku.get("image")
            );
        }).toList();
    }

    private BigDecimal totalAmount(List<Map<String, Object>> items) {
        return items.stream()
                .map(item -> ((BigDecimal) item.get("price")).multiply(BigDecimal.valueOf((Integer) item.get("quantity"))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void clearSelectedCartItems() {
        cartItemMapper.delete(
                Wrappers.<CartItem>lambdaQuery()
                        .eq(CartItem::getUserId, currentUserId())
                        .eq(CartItem::getSelected, 1)
        );
    }

    private List<Map<String, Object>> listOrderItems(Long orderId) {
        return orderItemMapper.selectList(
                Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderId, orderId).orderByAsc(OrderItem::getId)
        ).stream().map(item -> Map.<String, Object>of(
                "productId", item.getSpuId(),
                "name", item.getProductName(),
                "image", item.getProductImage(),
                "skuText", stringValue(item.getSkuText()),
                "price", item.getSalePrice(),
                "quantity", item.getQuantity()
        )).toList();
    }

    private MallUserAddress requireAddress(Long id) {
        MallUserAddress address = mallUserAddressMapper.selectOne(
                Wrappers.<MallUserAddress>lambdaQuery().eq(MallUserAddress::getId, id).eq(MallUserAddress::getUserId, currentUserId()).last("limit 1")
        );
        if (address == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "收货地址不存在");
        }
        return address;
    }

    private OrderInfo requireOrder(Long id) {
        OrderInfo order = orderInfoMapper.selectOne(
                Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getId, id).eq(OrderInfo::getUserId, currentUserId()).last("limit 1")
        );
        if (order == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "订单不存在");
        }
        return order;
    }

    private String generateOrderNo() {
        return "GB" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + System.nanoTime() % 100000;
    }

    private Long currentUserId() {
        return SecurityUtils.getLoginUser().getUserId();
    }

    private String stringValue(Object value) {
        return value == null ? "" : String.valueOf(value).trim();
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
}
