package com.stock.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 每日涨/跌停板
 * </p>
 *
 * @author caijinglong
 * @since 2022-11-310 22:56:44
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("daily_limit")
@ApiModel(value = "DailyLimit对象", description = "每日涨/跌停板")
public class DailyLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("股票编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty("股票名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("现价")
    @TableField("price")
    private Double price;

    @ApiModelProperty("价格涨跌幅度")
    @TableField("price_limit")
    private Double priceLimit;

    @ApiModelProperty("成交额")
    @TableField("amount")
    private Long amount;

    @ApiModelProperty("总市值")
    @TableField("mkt_cap")
    private Double mktCap;

    @ApiModelProperty("流通市值")
    @TableField("ff_cap")
    private Double ffCap;

    @ApiModelProperty("换手率")
    @TableField("turnover_rate")
    private Double turnoverRate;

    @ApiModelProperty("封板资金")
    @TableField("limit_fund")
    private Long limitFund;

    @ApiModelProperty("日期")
    @TableField("daily")
    private LocalDate daily;

    @ApiModelProperty("首次封板时间")
    @TableField("limit_time_first")
    private LocalTime limitTimeFirst;

    @ApiModelProperty("最后封板时间")
    @TableField("limit_time_last")
    private LocalTime limitTimeLast;

    @ApiModelProperty("炸板次数")
    @TableField("fail_times")
    private Integer failTimes;

    @ApiModelProperty("涨停统计")
    @TableField("limit_statistic")
    private String limitStatistic;

    @ApiModelProperty("连板天数")
    @TableField("limit_days")
    private Integer limitDays;

    @ApiModelProperty("所属行业")
    @TableField("industry")
    private String industry;

    @ApiModelProperty("是否涨停板")
    @TableField("is_raise")
    private Boolean isRaise;

    @ApiModelProperty("创建时间")
    @TableField("created_on")
    private LocalDateTime createdOn;

    @ApiModelProperty("更新时间")
    @TableField("updated_on")
    private LocalDateTime updatedOn;


}
