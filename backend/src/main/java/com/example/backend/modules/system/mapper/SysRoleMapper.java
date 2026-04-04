package com.example.backend.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.modules.system.entity.SysRole;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("""
            select r.role_code
            from sys_role r
            inner join sys_user_role ur on ur.role_id = r.id
            where ur.user_id = #{userId} and r.status = 1
            """)
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);
}
