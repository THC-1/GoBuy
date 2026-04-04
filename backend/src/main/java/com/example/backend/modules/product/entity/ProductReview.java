package com.example.backend.modules.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("product_review")
public class ProductReview {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long spuId;

    private Long userId;

    private String username;

    private String avatarUrl;

    private Integer rating;

    private String content;

    private String imagesJson;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
