package com.stock.core.http.ths;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caijinglong
 * @date 2022-11-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TendencyItemModel implements Serializable {

  /*
时间,股价,e额度,均价,量
1500,31.86,23888600,36.929,749800
   */

  @ApiModelProperty("时间点 pattern:mmss 如0930")
  private Integer timing;

  @ApiModelProperty("股价")
  private Double price;

  @ApiModelProperty("交易均价")
  private Double avgPrice;

  @ApiModelProperty("总成交量")
  private Long tradingVolume;

  @ApiModelProperty("总成交额")
  private Long tradingAmount;

}
