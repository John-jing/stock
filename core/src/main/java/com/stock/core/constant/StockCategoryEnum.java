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
  BAIDU_OPEN_DATA("百度-百度股市通", "https://opendata.baidu.com"),
  BAIDU_JIU_FANG_ZHI_TOU_OPEN_DATA("百度-九方智投", "https://sp0.baidu.com"),
  BAIDU_GU_SHI_TONG_DATA("百度股市通", "https://gushitong.baidu.com"),
  BAIDU_9FZT("百度-九方智投", "https://sp0.baidu.com/5LMDcjW6BwF3otqbppnN2DJv/finance.pae.baidu.com"),
  SZSE("深圳证券交易所", "http://www.szse.cn"),
  SSE("上海证券交易所", "http://query.sse.com.cn"),

  ;

  private String name;
  private String hostname;

  DataOriginEnum(String name, String hostname) {
    this.name = name;
    this.hostname = hostname;
  }
}
