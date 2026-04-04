USE gobuy;

INSERT INTO mall_user (id, username, password, nickname, phone, email, gender, bio, status)
VALUES
    (1, 'buyer01', 'user123', '新鲜买家', '13900000000', 'buyer01@gobuy.test', 'secret', '热爱自然生活方式', 1),
    (2, 'buyer02', 'user123', '回头客', '13900000001', 'buyer02@gobuy.test', 'female', '喜欢优雅家居与生活方式好物', 1)
ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    nickname = VALUES(nickname),
    phone = VALUES(phone),
    email = VALUES(email),
    gender = VALUES(gender),
    bio = VALUES(bio),
    status = VALUES(status);

INSERT INTO product_category (id, parent_id, category_name, level_no, sort_no, status)
VALUES
    (1, 0, '数码', 1, 1, 1),
    (2, 1, '手机', 2, 1, 1),
    (3, 2, '游戏手机', 3, 1, 1),
    (4, 0, '家居', 1, 2, 1)
ON DUPLICATE KEY UPDATE
    parent_id = VALUES(parent_id),
    category_name = VALUES(category_name),
    level_no = VALUES(level_no),
    sort_no = VALUES(sort_no),
    status = VALUES(status);

INSERT INTO product_attribute_template (id, template_name, remark, status)
VALUES
    (1, '手机规格模板', '适用于手机商品', 1),
    (2, '家居规格模板', '适用于自然家居商品', 1)
ON DUPLICATE KEY UPDATE
    template_name = VALUES(template_name),
    remark = VALUES(remark),
    status = VALUES(status);

INSERT INTO product_attribute_template_item (id, template_id, item_type, item_name, item_values, sort_no, status)
VALUES
    (1, 1, 'SPEC', '颜色', '黑曜版,冰川版', 1, 1),
    (2, 1, 'SPEC', '版本', '12GB+256GB,16GB+512GB', 2, 1),
    (3, 2, 'SPEC', '颜色', '原木色', 1, 1)
ON DUPLICATE KEY UPDATE
    template_id = VALUES(template_id),
    item_type = VALUES(item_type),
    item_name = VALUES(item_name),
    item_values = VALUES(item_values),
    sort_no = VALUES(sort_no),
    status = VALUES(status);

INSERT INTO product_spu (id, category_id, template_id, spu_name, subtitle, brand_name, cover_image, detail_desc, original_price, sales_count, rating, sale_status)
VALUES
    (1, 3, 1, 'GoBuy 幻影游戏手机', '144Hz 高刷与大电池续航', 'GoBuy', 'https://example.com/assets/phone-main.png', '旗舰性能与大电池续航兼顾。', 4999.00, 120, 4.8, 1),
    (2, 4, 2, '莫兰迪收纳边柜', '简约自然风家居边柜', '自然家居', 'https://example.com/assets/cabinet-main.png', '采用自然木质纹理与收纳设计。', 999.00, 88, 4.7, 1)
ON DUPLICATE KEY UPDATE
    category_id = VALUES(category_id),
    template_id = VALUES(template_id),
    spu_name = VALUES(spu_name),
    subtitle = VALUES(subtitle),
    brand_name = VALUES(brand_name),
    cover_image = VALUES(cover_image),
    detail_desc = VALUES(detail_desc),
    original_price = VALUES(original_price),
    sales_count = VALUES(sales_count),
    rating = VALUES(rating),
    sale_status = VALUES(sale_status);

INSERT INTO product_sku (id, spu_id, sku_name, sku_code, specs_json, image_url, sale_price, stock, lock_stock, status)
VALUES
    (1, 1, '12GB+256GB 黑曜版', 'SKU-PHONE-001', '{"颜色":"黑曜版","版本":"12GB+256GB"}', 'https://example.com/assets/phone-main.png', 3999.00, 50, 0, 1),
    (2, 1, '16GB+512GB 冰川版', 'SKU-PHONE-002', '{"颜色":"冰川版","版本":"16GB+512GB"}', 'https://example.com/assets/phone-main.png', 4699.00, 36, 0, 1),
    (3, 2, '原木色 标准款', 'SKU-HOME-001', '{"颜色":"原木色"}', 'https://example.com/assets/cabinet-main.png', 899.00, 88, 0, 1)
ON DUPLICATE KEY UPDATE
    spu_id = VALUES(spu_id),
    sku_name = VALUES(sku_name),
    specs_json = VALUES(specs_json),
    image_url = VALUES(image_url),
    sale_price = VALUES(sale_price),
    stock = VALUES(stock),
    lock_stock = VALUES(lock_stock),
    status = VALUES(status);

INSERT INTO mall_user_address (id, user_id, receiver_name, receiver_phone, province, city, district, detail_address, is_default, status)
VALUES
    (1, 1, '张三', '13812345678', '浙江省', '杭州市', '西湖区', '文一西路 999 号', 1, 1),
    (2, 2, '王五', '13987654321', '上海市', '上海市', '浦东新区', '世纪大道 88 号', 1, 1)
ON DUPLICATE KEY UPDATE
    receiver_name = VALUES(receiver_name),
    receiver_phone = VALUES(receiver_phone),
    province = VALUES(province),
    city = VALUES(city),
    district = VALUES(district),
    detail_address = VALUES(detail_address),
    is_default = VALUES(is_default),
    status = VALUES(status);
