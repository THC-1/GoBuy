package com.example.backend.modules.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("product_spu")
public class ProductSpu {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long categoryId;

    private Long templateId;

    private String spuName;

    private String subtitle;

    private String brandName;

    private String coverImage;

    private String detailDesc;

    private BigDecimal originalPrice;

    private Integer salesCount;

    private BigDecimal rating;

    private Integer saleStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
