USE gobuy;

INSERT INTO sys_role (id, role_code, role_name, remark, status)
VALUES
    (1, 'ADMIN', '超级管理员', '拥有全部后台权限', 1),
    (2, 'OPS', '运营人员', '负责基础运营与商品维护', 1)
ON DUPLICATE KEY UPDATE
    role_name = VALUES(role_name),
    remark = VALUES(remark),
    status = VALUES(status);

INSERT INTO sys_user (id, username, password, real_name, phone, status)
VALUES
    (1, 'admin', 'admin123', '系统管理员', '13800000000', 1),
    (2, 'operator', 'operator123', '运营专员', '13800000001', 1)
ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    real_name = VALUES(real_name),
    phone = VALUES(phone),
    status = VALUES(status);

INSERT INTO sys_user_role (id, user_id, role_id)
VALUES
    (1, 1, 1),
    (2, 2, 2)
ON DUPLICATE KEY UPDATE
    user_id = VALUES(user_id),
    role_id = VALUES(role_id);

INSERT INTO sys_menu (id, parent_id, menu_name, permission_code, menu_type, route_path, component_name, icon_name, sort_no, status)
VALUES
    (1, 0, '控制台', 'dashboard:view', 1, '/admin', 'DashboardView', 'HomeFilled', 1, 1),
    (2, 0, '用户管理', 'sys:user:list', 2, '/admin/system/user', 'UserView', 'User', 2, 1),
    (3, 0, '角色管理', 'sys:role:list', 2, '/admin/system/role', 'RoleView', 'Avatar', 3, 1),
    (4, 0, '菜单管理', 'sys:menu:list', 2, '/admin/system/menu', 'MenuView', 'Menu', 4, 1),
    (5, 0, '字典管理', 'sys:dict:list', 2, '/admin/system/dict', 'DictView', 'Tickets', 5, 1),
    (6, 0, '分类管理', 'product:category:list', 2, '/admin/product/category', 'CategoryView', 'Collection', 6, 1),
    (7, 0, '模板管理', 'product:template:list', 2, '/admin/product/attribute', 'AttributeView', 'DataLine', 7, 1),
    (8, 0, '商品管理', 'product:spu:list', 2, '/admin/product/spusku', 'SpuSkuView', 'Goods', 8, 1)
ON DUPLICATE KEY UPDATE
    menu_name = VALUES(menu_name),
    permission_code = VALUES(permission_code),
    menu_type = VALUES(menu_type),
    route_path = VALUES(route_path),
    component_name = VALUES(component_name),
    icon_name = VALUES(icon_name),
    sort_no = VALUES(sort_no),
    status = VALUES(status);

INSERT INTO sys_role_menu (id, role_id, menu_id)
VALUES
    (1, 1, 1),
    (2, 1, 2),
    (3, 1, 3),
    (4, 1, 4),
    (5, 1, 5),
    (6, 1, 6),
    (7, 1, 7),
    (8, 1, 8),
    (9, 2, 1),
    (10, 2, 6),
    (11, 2, 7),
    (12, 2, 8)
ON DUPLICATE KEY UPDATE
    role_id = VALUES(role_id),
    menu_id = VALUES(menu_id);
