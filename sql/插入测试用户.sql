-- 插入测试用户 1
INSERT INTO `user` (
    `username`,
    `email`,
    `password`,
    `nickname`,
    `avatar_url`,
    `status`,
    `last_login_time`,
    `deleted`
) VALUES (
             'testuser01',
             'test01@example.com',
             -- 密码：123456（BCrypt加密）
             '$2a$10$y3iVrH4tQaGz0xLbKjHsUeFdSsAaQwSdXeRfTgBhYvUjIoLpNqQzC',
             '测试用户一号',
             'https://picsum.photos/200/200',
             1,
             NOW(),
             0
         );

-- 插入测试用户 2
INSERT INTO `user` (
    `username`,
    `email`,
    `password`,
    `nickname`,
    `avatar_url`,
    `status`,
    `last_login_time`,
    `deleted`
) VALUES (
             'lumina001',
             'lumina@example.com',
             -- 密码：123456（BCrypt加密）
             '$2a$10$y3iVrH4tQaGz0xLbKjHsUeFdSsAaQwSdXeRfTgBhYvUjIoLpNqQzC',
             'Lumina管理员',
             'https://picsum.photos/200/200',
             1,
             NOW(),
             0
         );