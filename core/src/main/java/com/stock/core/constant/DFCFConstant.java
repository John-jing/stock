package com.stock.core.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caijinglong
 * @date 2022-11-10
 */
public class DFCFConstant {

  /**
   * 强势股入选理由
   */
  public static final Map<String, String> CC_MAP = new HashMap<>() {{
    put("1", "60日新高");
    put("2", "近期多次涨停");
    put("3", "60日新高且近期多次涨停");
  }};

}
