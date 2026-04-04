package com.example.backend.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.modules.user.entity.MallUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MallUserMapper extends BaseMapper<MallUser> {
}
