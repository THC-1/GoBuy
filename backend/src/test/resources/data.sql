INSERT INTO sys_role (id, role_code, role_name, remark, status) VALUES
    (1, 'ADMIN', '超级管理员', '拥有全部后台权限', 1),
    (2, 'EDITOR', '运营人员', '负责商品与内容维护', 1);

INSERT INTO sys_user (id, username, password, real_name, phone, status) VALUES
    (1, 'admin', 'admin123', '系统管理员', '13800000000', 1),
    (2, 'operator', 'operator123', '商城运营', '13800000001', 1);

INSERT INTO sys_user_role (id, user_id, role_id) VALUES
    (1, 1, 1),
    (2, 2, 2);

INSERT INTO sys_menu (id, parent_id, menu_name, permission_code, menu_type, route_path, component_name, icon_name, sort_no, status) VALUES
    (1, 0, '控制台', 'dashboard:view', 1, '/admin', 'DashboardView', 'HomeFilled', 1, 1),
    (2, 0, '用户管理', 'sys:user:list', 2, '/admin/system/user', 'UserView', 'User', 2, 1),
    (3, 0, '角色管理', 'sys:role:list', 2, '/admin/system/role', 'RoleView', 'Avatar', 3, 1),
    (4, 0, '菜单管理', 'sys:menu:list', 2, '/admin/system/menu', 'MenuView', 'Menu', 4, 1),
    (5, 0, '字典管理', 'sys:dict:list', 2, '/admin/system/dict', 'DictView', 'Tickets', 5, 1),
    (6, 0, '分类管理', 'product:category:list', 2, '/admin/product/category', 'CategoryView', 'Collection', 6, 1),
    (7, 0, '模板管理', 'product:template:list', 2, '/admin/product/attribute', 'AttributeView', 'DataLine', 7, 1),
    (8, 0, '商品管理', 'product:spu:list', 2, '/admin/product/spusku', 'SpuSkuView', 'Goods', 8, 1);

INSERT INTO sys_role_menu (id, role_id, menu_id) VALUES
    (1, 1, 1),
    (2, 1, 2),
    (3, 1, 3),
    (4, 1, 4),
    (5, 1, 5),
    (6, 1, 6),
    (7, 1, 7),
    (8, 1, 8),
    (9, 2, 6),
    (10, 2, 7),
    (11, 2, 8);

INSERT INTO sys_dict_type (id, dict_name, dict_type, status, remark) VALUES
    (1, '用户性别', 'sys_user_gender', 1, '会员性别字典'),
    (2, '订单状态', 'order_status', 1, '订单状态字典');

INSERT INTO sys_dict_item (id, dict_type, label_name, value_code, sort_no, status, remark) VALUES
    (1, 'sys_user_gender', '男', 'male', 1, 1, NULL),
    (2, 'sys_user_gender', '女', 'female', 2, 1, NULL),
    (3, 'sys_user_gender', '保密', 'secret', 3, 1, NULL),
    (4, 'order_status', '待支付', 'PENDING_PAYMENT', 1, 1, NULL),
    (5, 'order_status', '待发货', 'PAID', 2, 1, NULL),
    (6, 'order_status', '已完成', 'COMPLETED', 3, 1, NULL);

INSERT INTO mall_user (id, username, password, nickname, phone, email, gender, bio, status) VALUES
    (1, 'buyer01', 'user123', '新鲜买家', '13900000000', 'buyer01@gobuy.test', 'secret', '热爱自然生活方式', 1);

INSERT INTO mall_user_address (id, user_id, receiver_name, receiver_phone, province, city, district, detail_address, is_default, status) VALUES
    (1, 1, '张三', '13812345678', '浙江省', '杭州市', '西湖区', '文一西路 999 号', 1, 1),
    (2, 1, '李四', '13987654321', '上海市', '上海市', '浦东新区', '世纪大道 1 号', 0, 1);

INSERT INTO product_category (id, parent_id, category_name, level_no, sort_no, status) VALUES
    (1, 0, '手机数码', 1, 1, 1),
    (11, 1, '智能手机', 2, 1, 1),
    (111, 11, '苹果', 3, 1, 1),
    (112, 11, '华为', 3, 2, 1),
    (2, 0, '家居生活', 1, 2, 1);

INSERT INTO product_attribute_template (id, template_name, remark, status) VALUES
    (1, '手机规格模板', '适用于手机商品规格', 1),
    (2, '家居模板', '适用于家居用品', 1);

INSERT INTO product_attribute_template_item (id, template_id, item_type, item_name, item_values, sort_no, status) VALUES
    (1, 1, 'ATTR', '机身材质', '钛金属,玻璃', 1, 1),
    (2, 1, 'SPEC', '颜色', '原色钛金属,白色钛金属,雅川青', 2, 1),
    (3, 1, 'SPEC', '版本', '256GB,512GB,12GB+512GB', 3, 1),
    (4, 2, 'ATTR', '材质', '原木,棉麻', 1, 1),
    (5, 2, 'SPEC', '颜色', '原木色,浅咖色,薄荷绿', 2, 1);

INSERT INTO product_spu (id, category_id, template_id, spu_name, subtitle, brand_name, cover_image, detail_desc, original_price, sales_count, rating, sale_status) VALUES
    (1001, 111, 1, 'Apple iPhone 15 Pro Max 钛金属 5G手机', 'A17 Pro 芯片，旗舰影像表现', '苹果', 'https://img14.360buyimg.com/n0/jfs/t1/214196/23/40432/79981/653f58a3F512ff317/637eb852a466a96c.jpg', '旗舰性能与自然质感设计兼具。', 11999.00, 50, 4.9, 1),
    (1002, 112, 1, 'HUAWEI Mate 60 Pro 卫星通话 麒麟芯片', '昆仑玻璃，卫星通信', '华为', 'https://img14.360buyimg.com/n0/jfs/t1/211997/15/38475/88636/651b72e7F3d321151/52e92c208479bbec.jpg', '支持卫星通话与高端影像体验。', 7999.00, 200, 4.8, 1),
    (1003, 2, 2, '手工编织收纳篮', '自然编织，居家收纳', '自然系列', 'https://images.unsplash.com/photo-1584346133934-a3afd2a33c4c?w=400&h=400&fit=crop', '精选天然材质，适合客厅与卧室收纳。', 159.00, 88, 4.7, 1);

INSERT INTO product_sku (id, spu_id, sku_name, sku_code, specs_json, image_url, sale_price, stock, lock_stock, status) VALUES
    (2001, 1001, 'iPhone 15 Pro Max 原色钛金属 256GB', 'SKU-1001-1', '{"颜色":"原色钛金属","版本":"256GB"}', 'https://img14.360buyimg.com/n0/jfs/t1/214196/23/40432/79981/653f58a3F512ff317/637eb852a466a96c.jpg', 9999.00, 120, 0, 1),
    (2002, 1001, 'iPhone 15 Pro Max 白色钛金属 512GB', 'SKU-1001-2', '{"颜色":"白色钛金属","版本":"512GB"}', 'https://img14.360buyimg.com/n0/jfs/t1/214196/23/40432/79981/653f58a3F512ff317/637eb852a466a96c.jpg', 11999.00, 80, 0, 1),
    (2003, 1002, 'Mate 60 Pro 雅川青 12GB+512GB', 'SKU-1002-1', '{"颜色":"雅川青","版本":"12GB+512GB"}', 'https://img14.360buyimg.com/n0/jfs/t1/211997/15/38475/88636/651b72e7F3d321151/52e92c208479bbec.jpg', 6999.00, 30, 0, 1),
    (2004, 1003, '手工编织收纳篮 原木色', 'SKU-1003-1', '{"颜色":"原木色"}', 'https://images.unsplash.com/photo-1584346133934-a3afd2a33c4c?w=400&h=400&fit=crop', 129.00, 200, 0, 1);

INSERT INTO product_review (id, spu_id, user_id, username, avatar_url, rating, content, images_json) VALUES
    (1, 1001, 1, '张**', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Felix', 5, '非常舒服，系统流畅，手感也很好。', '[]'),
    (2, 1001, 1, '李**', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Aneka', 4, '做工不错，价格稍高。', '["https://img14.360buyimg.com/n0/jfs/t1/214196/23/40432/79981/653f58a3F512ff317/637eb852a466a96c.jpg"]'),
    (3, 1003, 1, '王**', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Oliver', 5, '收纳效果很好，和家里风格很搭。', '[]');

INSERT INTO mall_user_favorite (id, user_id, spu_id) VALUES
    (1, 1, 1001),
    (2, 1, 1003);

INSERT INTO cart_item (id, user_id, spu_id, sku_id, quantity, selected, status) VALUES
    (1, 1, 1001, 2001, 1, 1, 1),
    (2, 1, 1003, 2004, 2, 1, 1);
