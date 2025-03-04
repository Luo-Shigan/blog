CREATE DATABASE IF NOT EXISTS `blog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `blog`;
CREATE TABLE `users` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,       -- 用户ID，主键，自增
  `username` VARCHAR(50) NOT NULL UNIQUE,     -- 用户名，必须唯一
  `email` VARCHAR(100) NOT NULL UNIQUE,       -- 邮箱，必须唯一
  `password` VARCHAR(255) NOT NULL,           -- 密码（经过加密存储）
  `avatar_url` VARCHAR(255) DEFAULT NULL,     -- 用户头像URL（可选）
  `bio` TEXT DEFAULT NULL,                    -- 用户简介（可选）
  `role` ENUM('USER', 'ADMIN') DEFAULT 'USER', -- 用户角色（USER：普通用户，ADMIN：管理员）
  `status` ENUM('ACTIVE', 'INACTIVE', 'BANNED') DEFAULT 'ACTIVE', -- 用户状态（ACTIVE：激活，INACTIVE：未激活，BANNED：禁用）
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 用户注册时间，自动生成
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 用户信息更新时间
  `last_login` TIMESTAMP DEFAULT NULL,        -- 最后登录时间
  `login_attempts` INT DEFAULT 0,             -- 登录失败次数（防止暴力破解）
  `is_email_verified` BOOLEAN DEFAULT FALSE,  -- 邮箱是否已验证
  `is_active` BOOLEAN DEFAULT TRUE            -- 账户是否启用（用于禁用账户）
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文章标题',
  `cover` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文章封面',
  `summary` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文章摘要',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标志位：0：未删除 1：已删除',
  `read_num` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '被阅读次数',
  `weight` int(6) UNSIGNED NOT NULL DEFAULT 0 COMMENT '文章权重，用于是否置顶（0: 未置顶；>0: 参与置顶，权重值越高越靠前）',
  `type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '文章类型 - 1：普通文章，2：收录于知识库',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章表' ROW_FORMAT = DYNAMIC;