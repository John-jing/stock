package com.stock.core.http.sse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author caijinglong
 * @date 2022-12-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgtResponse implements Serializable {

  private SgtMetadataResponse metadata;
  private List<SgtItemResponse> data;
  private String error;

  @Data
  @NoArgsConstructor
  public static class SgtItemResponse implements Serializable {

    private String zqdm;
    private String zqjc;

  }


  @Data
  @NoArgsConstructor
  public static class SgtMetadataResponse {
    private Integer pagecount;
  }
}
