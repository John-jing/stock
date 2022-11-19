package com.stock.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stock.core.entity.base.BaseEntity;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 分时趋势图
 * </p>
 *
 * @author caijinglong
 * @since 2022-11-317 19:25:40
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("timing_tendency")
@ApiModel(value = "TimingTendency对象", description = "分时趋势图")
public class TimingTendency extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("股票编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty("股票名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty("日期")
    @TableField("daily")
    private LocalDate daily;

    @ApiModelProperty("时间点")
    @TableField("timing")
    private Integer timing;

    @ApiModelProperty("现价")
    @TableField("price")
    private Double price;

    @ApiModelProperty("成交均价")
    @TableField("avg_price")
    private Double avgPrice;

    @ApiModelProperty("总成交量-手数")
    @TableField("trading_volume")
    private Long tradingVolume;

    @ApiModelProperty("总成交额")
    @TableField("trading_amount")
    private Long tradingAmount;

    @ApiModelProperty("总成交额")
    @TableField("detail")
    private String detail;


}
