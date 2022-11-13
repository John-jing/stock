package com.stock.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author John
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Document(indexName = "hotWordThreeDay")
public class HotWordThreeDay {

  private Long id;
  private Integer times;
  private String word;
}
