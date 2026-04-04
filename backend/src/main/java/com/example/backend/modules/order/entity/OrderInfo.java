package com.example.backend.modules.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("order_info")
public class OrderInfo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String orderNo;

    private String orderStatus;

    private String paymentMethod;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private String remark;

    private BigDecimal goodsAmount;

    private BigDecimal freightAmount;

    private BigDecimal discountAmount;

    private BigDecimal payAmount;

    private LocalDateTime payTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
