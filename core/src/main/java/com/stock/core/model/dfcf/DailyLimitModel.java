package com.stock.core.model.dfcf;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author caijinglong
 * @date 2022-11-06
 */
@Data
public class DailyLimitModel implements Serializable {

  @ApiModelProperty("股票编码")
  private String c;

  @ApiModelProperty("股票名称")
  private String n;

  @ApiModelProperty("股票名称")
  private Integer m;

  @ApiModelProperty("最新价 真实数值的1000倍")
  private Integer p;

  @ApiModelProperty("涨停价 真实数值的1000倍")
  private Integer ztp;

  @ApiModelProperty("涨跌幅 %")
  private Double zdp;

  @ApiModelProperty("成交额，单位：元")
  private Long amount;

  @ApiModelProperty("流通市值，单位：元")
  private Double ltsz;

  @ApiModelProperty("总市值，单位：元")
  private Double tshare;

  @ApiModelProperty("换手率 %")
  private Double hs;

  @ApiModelProperty("涨停连板数")
  private Integer lbc;

  @ApiModelProperty("首次封板时间 format:hhmmdd")
  private String fbt;

  @ApiModelProperty("最后封板时间")
  private String lbt;

  @ApiModelProperty("封板资金，单位：亿")
  private Long fund;

  @ApiModelProperty("涨停炸板次数")
  private Integer zbc;

  @ApiModelProperty("所属行业")
  private String hybk;

  @ApiModelProperty("涨停统计  2/2")
  private Map zttj;

  // 跌停专有属性
  @ApiModelProperty("动态市盈率")
  private Double pe;

  @ApiModelProperty("跌停板上成交额")
  private Integer fba;

  @ApiModelProperty("连续跌停天数")
  private Integer days;

  @ApiModelProperty("跌停开板次数")
  private Integer oc;

  // 强势股池专有属性
  @ApiModelProperty("？？")
  private String ztf;

  @ApiModelProperty("新高")
  private Integer nh;

  @ApiModelProperty("入选理由：" +
          "1：60日新高" +
          "2：近期多次涨停" +
          "3：60日新高且近期多次涨停"
  )
  private String cc;

  @ApiModelProperty("量比")
  private Double lb;

  @ApiModelProperty("涨速")
  private Double zs;


//  Map  {
//    @ApiModelProperty("更新时间")
//    private Integer days;
//    @ApiModelProperty("更新时间")
//    private Integer ct;
//  }


}
