CREATE TABLE IF NOT EXISTS stock
(
    id         INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code       INTEGER      NOT NULL comment '编码',
    name       varchar(20)  NOT NULL comment '名称',
    price      double       NOT NULL comment '现价',
    type       varchar(20)  NOT NULL comment '类型',
    detail_url varchar(512) NOT NULL comment '详情地址',
    created_on DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_on DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_code (code),
    INDEX idx_price (price)
) COMMENT '股票';

