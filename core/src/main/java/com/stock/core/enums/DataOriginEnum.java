package com.stock.core.enums;

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

  ;

  private String name;
  private String hostname;

  DataOriginEnum(String name, String hostname) {
    this.name = name;
    this.hostname = hostname;
  }
}
