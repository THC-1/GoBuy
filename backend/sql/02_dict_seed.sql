USE gobuy;

INSERT INTO sys_dict_type (id, dict_name, dict_type, status, remark)
VALUES
    (1, '用户性别', 'sys_user_gender', 1, '用户性别字典'),
    (2, '菜单状态', 'sys_show_hide', 1, '菜单显示隐藏字典'),
    (3, '系统开关', 'sys_normal_disable', 1, '系统开关字典'),
    (4, '商品上下架', 'product_sale_status', 1, '商品状态字典'),
    (5, '订单状态', 'order_status', 1, '订单状态字典')
ON DUPLICATE KEY UPDATE
    dict_name = VALUES(dict_name),
    status = VALUES(status),
    remark = VALUES(remark);

INSERT INTO sys_dict_item (id, dict_type, label_name, value_code, sort_no, status, remark)
    VALUES
        (1, 'sys_user_gender', '男', 'male', 1, 1, NULL),
        (2, 'sys_user_gender', '女', 'female', 2, 1, NULL),
        (3, 'sys_user_gender', '保密', 'secret', 3, 1, NULL),
        (4, 'sys_show_hide', '显示', '1', 1, 1, NULL),
        (5, 'sys_show_hide', '隐藏', '0', 2, 1, NULL),
        (6, 'sys_normal_disable', '正常', '1', 1, 1, NULL),
        (7, 'sys_normal_disable', '停用', '0', 2, 1, NULL),
        (8, 'product_sale_status', '上架', '1', 1, 1, NULL),
        (9, 'product_sale_status', '下架', '0', 2, 1, NULL),
        (10, 'order_status', '待支付', 'PENDING_PAYMENT', 1, 1, NULL),
        (11, 'order_status', '待发货', 'PAID', 2, 1, NULL),
        (12, 'order_status', '已完成', 'COMPLETED', 3, 1, NULL)
        AS new_data
ON DUPLICATE KEY UPDATE
                     label_name = new_data.label_name,
                     value_code = new_data.value_code,
                     sort_no = new_data.sort_no,
                     status = new_data.status,
                     remark = new_data.remark;
