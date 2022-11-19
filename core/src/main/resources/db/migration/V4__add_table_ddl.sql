CREATE TABLE IF NOT EXISTS timing_tendency
(
    id             INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code           VARCHAR(20) NOT NULL COMMENT '股票编码',
    name           VARCHAR(20) NOT NULL COMMENT '股票名称',
    daily          DATE        NOT NULL COMMENT '日期',
    timing         INTEGER     NULL COMMENT '时间点',
    price          DOUBLE      NULL COMMENT '现价',
    avg_price      DOUBLE      NULL COMMENT '成交均价',
    trading_volume BIGINT      NULL COMMENT '总成交量-手数',
    trading_amount BIGINT      NULL COMMENT '总成交额',
    detail         JSON        NULL COMMENT 'json结构',
    created_on     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_on     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_code_daily (code, daily)
) COMMENT '分时趋势图';
