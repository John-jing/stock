package com.stock.core.model.dfcf;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author caijinglong
 * @date 2022-11-06
 */
@Data
public class PageModel implements Serializable {

  private Integer rc;
  private Integer rt;
  private Long svr;
  private Integer lt;
  private Integer full;
  private DataList data;

  @Data
  public static class DataList implements Serializable {
    private Integer tc;
    private List<DailyLimitModel> pool;

  }

}
