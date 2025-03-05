这是我的个人博客仓库
有问题联系我:2530320102@qq.com
## 技术栈
java:17
Spring Boot:Spring Security、jwt、Mybatis……
mysql:8.0.33
## 数据库
### users表
| 字段名            | 数据类型             | 约束/描述 |
|------------------|------------------|--------------------------------|
| `id`            | `INT`            | 主键，自增，唯一标识用户 |
| `username`      | `VARCHAR(50)`    | 不允许为空，唯一，存储用户名 |
| `email`         | `VARCHAR(100)`   | 不允许为空，唯一，存储用户邮箱 |
| `password`      | `VARCHAR(255)`   | 不允许为空，存储加密后的密码 |
| `avatar_url`    | `VARCHAR(255)`   | 可选，存储用户头像的 URL |
| `bio`           | `TEXT`           | 可选，存储用户简介 |
| `role`          | `ENUM('USER', 'ADMIN')` | 默认值为 `'USER'`，用于标识用户角色（普通用户或管理员） |
| `status`        | `ENUM('ACTIVE', 'INACTIVE', 'BANNED')` | 默认值为 `'ACTIVE'`，用于标识用户账户的状态（已激活、未激活、禁用） |
| `created_at`    | `TIMESTAMP`      | 默认值为 `CURRENT_TIMESTAMP`，存储用户注册时间，自动生成 |
| `updated_at`    | `TIMESTAMP`      | 默认值为 `CURRENT_TIMESTAMP`，每次更新记录时自动更新为当前时间 |
| `last_login`    | `TIMESTAMP`      | 可空，记录用户最后一次登录的时间 |
| `login_attempts` | `INT`           | 默认值为 `0`，用于记录登录失败次数，防止暴力破解 |
| `is_email_verified` | `BOOLEAN`   | 默认值为 `FALSE`，表示用户是否已验证邮箱 |
| `is_active`     | `BOOLEAN`       | 默认值为 `TRUE`，表示账户是否启用（禁用账户时为 `FALSE`） |

