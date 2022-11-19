package com.stock.core.http.constants;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author caijinglong
 * @date 2021-10-16
 */
@NoArgsConstructor
@Getter
public enum DataOriginEnum {
  /**
   * 东方财富
   */
  DFCF("东方财富", "http://push2ex.eastmoney.com"),
  THS("同花顺个股", "https://d.10jqka.com.cn"),

  ;

  private String name;
  private String hostname;

  DataOriginEnum(String name, String hostname) {
    this.name = name;
    this.hostname = hostname;
  }
}
