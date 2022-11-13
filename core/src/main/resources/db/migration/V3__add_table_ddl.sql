CREATE TABLE IF NOT EXISTS daily_limit
(
    id               INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code             VARCHAR(20)  NOT NULL COMMENT '股票编码',
    name             VARCHAR(20)  NOT NULL COMMENT '股票名称',
    price            DOUBLE       NOT NULL COMMENT '现价',
    price_limit      DOUBLE       NOT NULL COMMENT '价格涨跌幅度',
    amount           BIGINT       NOT NULL COMMENT '成交额',
    mkt_cap          DOUBLE       NOT NULL COMMENT '总市值',
    ff_cap           DOUBLE       NOT NULL COMMENT '流通市值',
    turnover_rate    DOUBLE       NOT NULL COMMENT '换手率',
    limit_fund       BIGINT       NOT NULL COMMENT '封板资金',
    daily            DATE         NOT NULL COMMENT '日期',
    limit_time_first TIME         NOT NULL COMMENT '首次封板时间',
    limit_time_last  TIME         NOT NULL COMMENT '最后封板时间',
    fail_times       INTEGER      NOT NULL DEFAULT 0 COMMENT '炸板次数',
    limit_statistic  VARCHAR(50)  NULL COMMENT '涨停统计',
    limit_days       INTEGER      NOT NULL DEFAULT 1 COMMENT '连板天数',
    industry         VARCHAR(500) NOT NULL COMMENT '所属行业',
    is_raise         tinyint(1)   NOT NULL COMMENT '是否涨停板',
    created_on       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_on       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_code_daily (code, daily)
) COMMENT '每日涨/跌停板';

CREATE TABLE IF NOT EXISTS dfcf_qs_pool
(
    id               INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    daily            DATE         NOT NULL COMMENT '日期',
    code             VARCHAR(20)  NOT NULL COMMENT '股票编码',
    name             VARCHAR(20)  NOT NULL COMMENT '股票名称',
    price_limit      DOUBLE       NOT NULL COMMENT '价格涨跌幅度',
    price            DOUBLE       NOT NULL COMMENT '最新价',
    price_high_limit DOUBLE       NOT NULL COMMENT '涨停价',
    amount           BIGINT       NOT NULL COMMENT '成交额',
    ff_cap           DOUBLE       NOT NULL COMMENT '流通市值',
    mkt_cap          DOUBLE       NOT NULL COMMENT '总市值',
    turnover_rate    DOUBLE       NOT NULL COMMENT '换手率',
    is_new_high      TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '是否新高',
    qrr              DOUBLE       NOT NULL COMMENT '量比 =（现成交总手数/现累计开市时间（分））/前5天平均每分钟成交量',
    limit_statistic  VARCHAR(50)  NULL COMMENT '涨停统计',
    remark           VARCHAR(500) NULL COMMENT '入选理由',
    industry         VARCHAR(500) NOT NULL COMMENT '所属行业',
    created_on       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_on       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_code_daily (code, daily)
) COMMENT '东方财富-强势股池';

