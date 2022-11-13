package com.stock.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.stock.core.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * <p>
 * 东方财富-强势股池
 * </p>
 *
 * @author caijinglong
 * @since 2022-11-313 23:58:37
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("dfcf_qs_pool")
@ApiModel(value = "DfcfQsPool对象", description = "东方财富-强势股池")
public class DfcfQsPool extends BaseEntity {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("日期")
  @TableField("daily")
  private LocalDate daily;

  @ApiModelProperty("股票编码")
  @TableField("`code`")
  private String code;

  @ApiModelProperty("股票名称")
  @TableField("`name`")
  private String name;

  @ApiModelProperty("价格涨跌幅度")
  @TableField("price_limit")
  private Double priceLimit;

  @ApiModelProperty("最新价")
  @TableField("price")
  private Double price;

  @ApiModelProperty("涨停价")
  @TableField("price_high_limit")
  private Double priceHighLimit;

  @ApiModelProperty("成交额")
  @TableField("amount")
  private Long amount;

  @ApiModelProperty("流通市值")
  @TableField("ff_cap")
  private Double ffCap;

  @ApiModelProperty("总市值")
  @TableField("mkt_cap")
  private Double mktCap;

  @ApiModelProperty("换手率")
  @TableField("turnover_rate")
  private Double turnoverRate;

  @ApiModelProperty("是否新高")
  @TableField("is_new_high")
  private Boolean isNewHigh;

  @ApiModelProperty("量比 =（现成交总手数/现累计开市时间（分））/前5天平均每分钟成交量")
  @TableField("qrr")
  private Double qrr;

  @ApiModelProperty("涨停统计")
  @TableField("limit_statistic")
  private String limitStatistic;

  @ApiModelProperty("入选理由")
  @TableField("remark")
  private String remark;

  @ApiModelProperty("所属行业")
  @TableField("industry")
  private String industry;


}
