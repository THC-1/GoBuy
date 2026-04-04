package com.example.backend.modules.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("product_sku")
public class ProductSku {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long spuId;

    private String skuName;

    private String skuCode;

    private String specsJson;

    private String imageUrl;

    private BigDecimal salePrice;

    private Integer stock;

    private Integer lockStock;

    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
