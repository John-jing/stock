CREATE TABLE IF NOT EXISTS hot_word_one_day
(
    id    INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    word  varchar(20) NOT NULL comment '热点词',
    times INTEGER     NOT NULL comment '次数',
    UNIQUE KEY uk_word (word)
) COMMENT '热点词';

CREATE TABLE IF NOT EXISTS hot_word_two_day
(
    id    INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    word  varchar(20) NOT NULL comment '热点词',
    times INTEGER     NOT NULL comment '次数',
    UNIQUE KEY uk_word (word)
) COMMENT '热点词';

CREATE TABLE IF NOT EXISTS hot_word_three_day
(
    id    INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    word  varchar(20) NOT NULL comment '热点词',
    times INTEGER     NOT NULL comment '次数',
    UNIQUE KEY uk_word (word)
) COMMENT '热点词';

CREATE TABLE IF NOT EXISTS hot_word_one_week
(
    id    INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    word  varchar(20) NOT NULL comment '热点词',
    times INTEGER     NOT NULL comment '次数',
    UNIQUE KEY uk_word (word)
) COMMENT '热点词';

CREATE TABLE IF NOT EXISTS hot_word_one_month
(
    id    INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    word  varchar(20) NOT NULL comment '热点词',
    times INTEGER     NOT NULL comment '次数',
    UNIQUE KEY uk_word (word)
) COMMENT '热点词';