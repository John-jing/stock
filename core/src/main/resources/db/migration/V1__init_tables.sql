CREATE TABLE IF NOT EXISTS stock
(
    id         INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code       integer     not null comment '编码',
    name       varchar(20) not null comment '名称',
    price      double      not null comment '现价',
    type       varchar(20) not null comment '类型',
    created_on DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_on DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '股票';

