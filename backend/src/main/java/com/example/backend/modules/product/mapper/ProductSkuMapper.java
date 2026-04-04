package com.example.backend.modules.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.modules.product.entity.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    @Update("""
            update product_sku
            set stock = stock - #{quantity},
                lock_stock = lock_stock + #{quantity}
            where id = #{skuId}
              and status = 1
              and stock >= #{quantity}
            """)
    int deductStock(@Param("skuId") Long skuId, @Param("quantity") Integer quantity);
}
