CREATE TABLE data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,          -- 自增主键
    `data_key` VARCHAR(255) NOT NULL UNIQUE,            -- 唯一键
    `data_value` TEXT NOT NULL,                         -- 值
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 更新时间
);