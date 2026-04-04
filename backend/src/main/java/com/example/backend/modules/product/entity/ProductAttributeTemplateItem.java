package com.example.backend.modules.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("product_attribute_template_item")
public class ProductAttributeTemplateItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long templateId;

    private String itemType;

    private String itemName;

    private String itemValues;

    private Integer sortNo;

    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
