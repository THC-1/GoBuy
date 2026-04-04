package com.example.backend.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.modules.system.entity.SysMenu;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("""
            select distinct m.permission_code
            from sys_menu m
            inner join sys_role_menu rm on rm.menu_id = m.id
            inner join sys_user_role ur on ur.role_id = rm.role_id
            where ur.user_id = #{userId}
              and m.status = 1
              and m.permission_code is not null
              and m.permission_code <> ''
            """)
    List<String> selectPermissionCodesByUserId(@Param("userId") Long userId);
}
