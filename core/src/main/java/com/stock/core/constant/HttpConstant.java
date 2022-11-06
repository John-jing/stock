package com.stock.core.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caijinglong
 * @date 2021-10-16
 */
public class HttpConstant {

  public static Map<String, List<String>> HEADER = new HashMap<>() {{
    put("User-Agent", Collections.singletonList("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36"));
  }};
}
