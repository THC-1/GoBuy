-- Lumina 数码商城 - 用户表
-- 用途: 存储C端用户基本信息
-- 执行前提: 需先创建 gobuy 数据库 (CREATE DATABASE IF NOT EXISTS gobuy DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci)

CREATE TABLE IF NOT EXISTS `user` (
    `id`              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`        VARCHAR(50)     NOT NULL                COMMENT '用户名',
    `email`           VARCHAR(100)    NOT NULL                COMMENT '邮箱',
    `password`        VARCHAR(255)    NOT NULL                COMMENT '密码(BCrypt加密)',
    `nickname`        VARCHAR(50)     DEFAULT NULL            COMMENT '昵称',
    `avatar_url`      VARCHAR(500)    DEFAULT NULL            COMMENT '头像URL',
    `status`          TINYINT         NOT NULL DEFAULT 1      COMMENT '状态: 0-禁用 1-正常',
    `last_login_time` DATETIME        DEFAULT NULL            COMMENT '最后登录时间',
    `created_at`      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`         TINYINT         NOT NULL DEFAULT 0      COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
