package com.example.backend.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend.common.api.PageResponse;
import com.example.backend.common.api.ResultCode;
import com.example.backend.common.exception.BusinessException;
import com.example.backend.modules.order.mapper.OrderInfoMapper;
import com.example.backend.modules.product.entity.ProductSpu;
import com.example.backend.modules.product.mapper.ProductSpuMapper;
import com.example.backend.modules.system.entity.SysDictItem;
import com.example.backend.modules.system.entity.SysDictType;
import com.example.backend.modules.system.entity.SysMenu;
import com.example.backend.modules.system.entity.SysRole;
import com.example.backend.modules.system.entity.SysRoleMenu;
import com.example.backend.modules.system.entity.SysUser;
import com.example.backend.modules.system.entity.SysUserRole;
import com.example.backend.modules.system.mapper.SysDictItemMapper;
import com.example.backend.modules.system.mapper.SysDictTypeMapper;
import com.example.backend.modules.system.mapper.SysMenuMapper;
import com.example.backend.modules.system.mapper.SysRoleMapper;
import com.example.backend.modules.system.mapper.SysRoleMenuMapper;
import com.example.backend.modules.system.mapper.SysUserMapper;
import com.example.backend.modules.system.mapper.SysUserRoleMapper;
import com.example.backend.modules.system.service.AdminSystemService;
import com.example.backend.modules.user.entity.MallUser;
import com.example.backend.modules.user.mapper.MallUserMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AdminSystemServiceImpl implements AdminSystemService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysMenuMapper sysMenuMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysDictTypeMapper sysDictTypeMapper;
    private final SysDictItemMapper sysDictItemMapper;
    private final MallUserMapper mallUserMapper;
    private final ProductSpuMapper productSpuMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminSystemServiceImpl(
            SysUserMapper sysUserMapper,
            SysRoleMapper sysRoleMapper,
            SysUserRoleMapper sysUserRoleMapper,
            SysMenuMapper sysMenuMapper,
            SysRoleMenuMapper sysRoleMenuMapper,
            SysDictTypeMapper sysDictTypeMapper,
            SysDictItemMapper sysDictItemMapper,
            MallUserMapper mallUserMapper,
            ProductSpuMapper productSpuMapper,
            OrderInfoMapper orderInfoMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysMenuMapper = sysMenuMapper;
        this.sysRoleMenuMapper = sysRoleMenuMapper;
        this.sysDictTypeMapper = sysDictTypeMapper;
        this.sysDictItemMapper = sysDictItemMapper;
        this.mallUserMapper = mallUserMapper;
        this.productSpuMapper = productSpuMapper;
        this.orderInfoMapper = orderInfoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, Object> getDashboardOverview() {
        return Map.of(
                "totalSales", orderInfoMapper.selectCount(null),
                "todayOrderCount", orderInfoMapper.selectCount(
                        Wrappers.<com.example.backend.modules.order.entity.OrderInfo>lambdaQuery()
                                .ge(com.example.backend.modules.order.entity.OrderInfo::getCreatedAt, LocalDateTime.now().toLocalDate().atStartOfDay())
                ),
                "newUserCount", mallUserMapper.selectCount(
                        Wrappers.<MallUser>lambdaQuery()
                                .ge(MallUser::getCreatedAt, LocalDateTime.now().toLocalDate().atStartOfDay())
                ),
                "pendingAfterSaleCount", productSpuMapper.selectCount(
                        Wrappers.<ProductSpu>lambdaQuery().eq(ProductSpu::getSaleStatus, 0)
                )
        );
    }

    @Override
    public PageResponse<Map<String, Object>> getUserPage(long pageNum, long pageSize, String username, Integer status) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                .like(StringUtils.hasText(username), SysUser::getUsername, username)
                .eq(status != null, SysUser::getStatus, status)
                .orderByDesc(SysUser::getId);
        Page<SysUser> page = sysUserMapper.selectPage(Page.of(pageNum, pageSize), queryWrapper);
        List<Map<String, Object>> records = page.getRecords().stream().map(user -> {
            List<String> roleCodes = sysRoleMapper.selectRoleCodesByUserId(user.getId());
            String roleName = roleCodes.isEmpty() ? "" : roleCodes.get(0);
            return Map.<String, Object>of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "nickname", user.getRealName(),
                    "role", roleName,
                    "status", user.getStatus(),
                    "createTime", user.getCreatedAt()
            );
        }).toList();
        return new PageResponse<>(page.getTotal(), page.getCurrent(), page.getSize(), records);
    }

    @Override
    @Transactional
    public void createUser(Map<String, Object> request) {
        String username = requiredString(request, "username");
        if (sysUserMapper.selectCount(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username)) > 0) {
            throw new BusinessException("用户名已存在");
        }
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(requiredString(request, "password")));
        user.setRealName(requiredString(request, "nickname"));
        user.setPhone(stringValue(request.get("phone")));
        user.setStatus(intValue(request.get("status"), 1));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        sysUserMapper.insert(user);
        bindUserRole(user.getId(), request);
    }

    @Override
    @Transactional
    public void updateUser(Long id, Map<String, Object> request) {
        SysUser user = requireUser(id);
        user.setRealName(requiredString(request, "nickname"));
        user.setPhone(stringValue(request.get("phone")));
        user.setStatus(intValue(request.get("status"), user.getStatus()));
        if (StringUtils.hasText(stringValue(request.get("password")))) {
            user.setPassword(passwordEncoder.encode(stringValue(request.get("password"))));
        }
        user.setUpdatedAt(LocalDateTime.now());
        sysUserMapper.updateById(user);
        bindUserRole(user.getId(), request);
    }

    @Override
    public void updateUserStatus(Long id, Integer status) {
        SysUser user = requireUser(id);
        user.setStatus(status);
        user.setUpdatedAt(LocalDateTime.now());
        sysUserMapper.updateById(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, id));
        sysUserMapper.deleteById(id);
    }

    @Override
    public PageResponse<Map<String, Object>> getRolePage(long pageNum, long pageSize, String roleName, Integer status) {
        Page<SysRole> page = sysRoleMapper.selectPage(
                Page.of(pageNum, pageSize),
                Wrappers.<SysRole>lambdaQuery()
                        .like(StringUtils.hasText(roleName), SysRole::getRoleName, roleName)
                        .eq(status != null, SysRole::getStatus, status)
                        .orderByAsc(SysRole::getId)
        );
        List<Map<String, Object>> records = page.getRecords().stream().map(role -> Map.<String, Object>of(
                "id", role.getId(),
                "roleName", role.getRoleName(),
                "roleKey", role.getRoleCode(),
                "status", role.getStatus(),
                "remark", stringValue(role.getRemark()),
                "createTime", role.getCreatedAt()
        )).toList();
        return new PageResponse<>(page.getTotal(), page.getCurrent(), page.getSize(), records);
    }

    @Override
    public List<Map<String, Object>> getRoleOptions() {
        return sysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery().eq(SysRole::getStatus, 1).orderByAsc(SysRole::getId))
                .stream()
                .map(role -> Map.<String, Object>of("id", role.getId(), "roleName", role.getRoleName(), "roleCode", role.getRoleCode()))
                .toList();
    }

    @Override
    public void createRole(Map<String, Object> request) {
        SysRole role = new SysRole();
        role.setRoleName(requiredString(request, "roleName"));
        role.setRoleCode(requiredString(request, "roleKey"));
        role.setRemark(stringValue(request.get("remark")));
        role.setStatus(intValue(request.get("status"), 1));
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        sysRoleMapper.insert(role);
    }

    @Override
    public void updateRole(Long id, Map<String, Object> request) {
        SysRole role = requireRole(id);
        role.setRoleName(requiredString(request, "roleName"));
        role.setRoleCode(requiredString(request, "roleKey"));
        role.setRemark(stringValue(request.get("remark")));
        role.setStatus(intValue(request.get("status"), role.getStatus()));
        role.setUpdatedAt(LocalDateTime.now());
        sysRoleMapper.updateById(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, id));
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, id));
        sysRoleMapper.deleteById(id);
    }

    @Override
    public List<Map<String, Object>> getMenuTree(String menuName, Integer status) {
        List<SysMenu> menus = sysMenuMapper.selectList(
                Wrappers.<SysMenu>lambdaQuery()
                        .like(StringUtils.hasText(menuName), SysMenu::getMenuName, menuName)
                        .eq(status != null, SysMenu::getStatus, status)
                        .orderByAsc(SysMenu::getSortNo)
                        .orderByAsc(SysMenu::getId)
        );
        return buildMenuNodes(menus, 0L);
    }

    @Override
    public void createMenu(Map<String, Object> request) {
        SysMenu menu = buildMenuEntity(new SysMenu(), request);
        menu.setCreatedAt(LocalDateTime.now());
        menu.setUpdatedAt(LocalDateTime.now());
        sysMenuMapper.insert(menu);
    }

    @Override
    public void updateMenu(Long id, Map<String, Object> request) {
        SysMenu menu = sysMenuMapper.selectById(id);
        if (menu == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "菜单不存在");
        }
        buildMenuEntity(menu, request);
        menu.setUpdatedAt(LocalDateTime.now());
        sysMenuMapper.updateById(menu);
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getMenuId, id));
        sysMenuMapper.delete(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getId, id).or().eq(SysMenu::getParentId, id));
    }

    @Override
    public PageResponse<Map<String, Object>> getDictTypePage(long pageNum, long pageSize, String dictName, String dictType, Integer status) {
        Page<SysDictType> page = sysDictTypeMapper.selectPage(
                Page.of(pageNum, pageSize),
                Wrappers.<SysDictType>lambdaQuery()
                        .like(StringUtils.hasText(dictName), SysDictType::getDictName, dictName)
                        .like(StringUtils.hasText(dictType), SysDictType::getDictType, dictType)
                        .eq(status != null, SysDictType::getStatus, status)
                        .orderByAsc(SysDictType::getId)
        );
        List<Map<String, Object>> records = page.getRecords().stream().map(item -> Map.<String, Object>of(
                "id", item.getId(),
                "dictName", item.getDictName(),
                "dictType", item.getDictType(),
                "status", item.getStatus(),
                "remark", stringValue(item.getRemark()),
                "createTime", item.getCreatedAt()
        )).toList();
        return new PageResponse<>(page.getTotal(), page.getCurrent(), page.getSize(), records);
    }

    @Override
    public List<Map<String, Object>> getDictItems(String dictType) {
        return sysDictItemMapper.selectList(
                Wrappers.<SysDictItem>lambdaQuery()
                        .eq(SysDictItem::getDictType, dictType)
                        .orderByAsc(SysDictItem::getSortNo)
                        .orderByAsc(SysDictItem::getId)
        ).stream().map(item -> Map.<String, Object>of(
                "id", item.getId(),
                "dictType", item.getDictType(),
                "labelName", item.getLabelName(),
                "valueCode", item.getValueCode(),
                "sortNo", item.getSortNo(),
                "status", item.getStatus(),
                "remark", stringValue(item.getRemark())
        )).toList();
    }

    @Override
    public void createDictType(Map<String, Object> request) {
        SysDictType dictType = new SysDictType();
        dictType.setDictName(requiredString(request, "dictName"));
        dictType.setDictType(requiredString(request, "dictType"));
        dictType.setRemark(stringValue(request.get("remark")));
        dictType.setStatus(intValue(request.get("status"), 1));
        dictType.setCreatedAt(LocalDateTime.now());
        dictType.setUpdatedAt(LocalDateTime.now());
        sysDictTypeMapper.insert(dictType);
    }

    @Override
    public void updateDictType(Long id, Map<String, Object> request) {
        SysDictType dictType = sysDictTypeMapper.selectById(id);
        if (dictType == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "字典类型不存在");
        }
        dictType.setDictName(requiredString(request, "dictName"));
        dictType.setDictType(requiredString(request, "dictType"));
        dictType.setRemark(stringValue(request.get("remark")));
        dictType.setStatus(intValue(request.get("status"), dictType.getStatus()));
        dictType.setUpdatedAt(LocalDateTime.now());
        sysDictTypeMapper.updateById(dictType);
    }

    @Override
    @Transactional
    public void deleteDictType(Long id) {
        SysDictType dictType = sysDictTypeMapper.selectById(id);
        if (dictType == null) {
            return;
        }
        sysDictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictType, dictType.getDictType()));
        sysDictTypeMapper.deleteById(id);
    }

    @Override
    public void createDictItem(Map<String, Object> request) {
        SysDictItem item = new SysDictItem();
        item.setDictType(requiredString(request, "dictType"));
        item.setLabelName(requiredString(request, "labelName"));
        item.setValueCode(requiredString(request, "valueCode"));
        item.setSortNo(intValue(request.get("sortNo"), 0));
        item.setStatus(intValue(request.get("status"), 1));
        item.setRemark(stringValue(request.get("remark")));
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        sysDictItemMapper.insert(item);
    }

    @Override
    public void updateDictItem(Long id, Map<String, Object> request) {
        SysDictItem item = sysDictItemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "字典项不存在");
        }
        item.setDictType(requiredString(request, "dictType"));
        item.setLabelName(requiredString(request, "labelName"));
        item.setValueCode(requiredString(request, "valueCode"));
        item.setSortNo(intValue(request.get("sortNo"), item.getSortNo()));
        item.setStatus(intValue(request.get("status"), item.getStatus()));
        item.setRemark(stringValue(request.get("remark")));
        item.setUpdatedAt(LocalDateTime.now());
        sysDictItemMapper.updateById(item);
    }

    @Override
    public void deleteDictItem(Long id) {
        sysDictItemMapper.deleteById(id);
    }

    private SysUser requireUser(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "用户不存在");
        }
        return user;
    }

    private SysRole requireRole(Long id) {
        SysRole role = sysRoleMapper.selectById(id);
        if (role == null) {
            throw new BusinessException(ResultCode.NOT_FOUND, "角色不存在");
        }
        return role;
    }

    private void bindUserRole(Long userId, Map<String, Object> request) {
        Long roleId = null;
        if (request.get("roleId") != null) {
            roleId = longValue(request.get("roleId"));
        } else if (StringUtils.hasText(stringValue(request.get("roleCode")))) {
            SysRole role = sysRoleMapper.selectOne(Wrappers.<SysRole>lambdaQuery()
                    .eq(SysRole::getRoleCode, stringValue(request.get("roleCode"))).last("limit 1"));
            roleId = role == null ? null : role.getId();
        } else if (StringUtils.hasText(stringValue(request.get("role")))) {
            SysRole role = sysRoleMapper.selectOne(Wrappers.<SysRole>lambdaQuery()
                    .and(wrapper -> wrapper.eq(SysRole::getRoleName, stringValue(request.get("role")))
                            .or()
                            .eq(SysRole::getRoleCode, stringValue(request.get("role"))))
                    .last("limit 1"));
            roleId = role == null ? null : role.getId();
        }
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
        if (roleId != null) {
            SysUserRole relation = new SysUserRole();
            relation.setUserId(userId);
            relation.setRoleId(roleId);
            sysUserRoleMapper.insert(relation);
        }
    }

    private SysMenu buildMenuEntity(SysMenu menu, Map<String, Object> request) {
        menu.setParentId(longValue(request.getOrDefault("parentId", 0)));
        menu.setMenuName(requiredString(request, "menuName"));
        menu.setPermissionCode(stringValue(request.get("perms")));
        menu.setMenuType(resolveMenuType(stringValue(request.get("menuType"))));
        menu.setRoutePath(stringValue(request.get("path")));
        menu.setComponentName(stringValue(request.get("component")));
        menu.setIconName(stringValue(request.get("icon")));
        menu.setSortNo(intValue(request.get("orderNum"), 0));
        menu.setStatus(intValue(request.get("status"), 1));
        return menu;
    }

    private Integer resolveMenuType(String menuType) {
        if (!StringUtils.hasText(menuType)) {
            return 1;
        }
        return switch (menuType) {
            case "C" -> 2;
            case "F" -> 3;
            default -> 1;
        };
    }

    private List<Map<String, Object>> buildMenuNodes(List<SysMenu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> Objects.equals(menu.getParentId(), parentId))
                .map(menu -> {
                    Map<String, Object> node = new LinkedHashMap<>();
                    node.put("id", menu.getId());
                    node.put("parentId", menu.getParentId());
                    node.put("menuName", menu.getMenuName());
                    node.put("icon", menu.getIconName());
                    node.put("orderNum", menu.getSortNo());
                    node.put("perms", menu.getPermissionCode());
                    node.put("component", menu.getComponentName());
                    node.put("path", menu.getRoutePath());
                    node.put("menuType", switch (menu.getMenuType()) {
                        case 2 -> "C";
                        case 3 -> "F";
                        default -> "M";
                    });
                    node.put("status", menu.getStatus());
                    node.put("createTime", menu.getCreatedAt());
                    List<Map<String, Object>> children = buildMenuNodes(menus, menu.getId());
                    if (!children.isEmpty()) {
                        node.put("children", children);
                    }
                    return node;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private String requiredString(Map<String, Object> request, String key) {
        String value = stringValue(request.get(key));
        if (!StringUtils.hasText(value)) {
            throw new BusinessException(ResultCode.BAD_REQUEST, key + "不能为空");
        }
        return value;
    }

    private String stringValue(Object value) {
        return value == null ? null : String.valueOf(value).trim();
    }

    private Integer intValue(Object value, Integer defaultValue) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return defaultValue;
        }
        return Integer.parseInt(String.valueOf(value));
    }

    private Long longValue(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return 0L;
        }
        return Long.parseLong(String.valueOf(value));
    }
}
