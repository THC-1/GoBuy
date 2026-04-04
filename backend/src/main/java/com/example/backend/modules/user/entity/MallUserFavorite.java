package com.example.backend.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("mall_user_favorite")
public class MallUserFavorite {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long spuId;

    private LocalDateTime createdAt;
}
