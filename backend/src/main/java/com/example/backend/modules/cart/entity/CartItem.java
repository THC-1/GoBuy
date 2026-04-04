package com.example.backend.modules.cart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("cart_item")
public class CartItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long spuId;

    private Long skuId;

    private Integer quantity;

    private Integer selected;

    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
