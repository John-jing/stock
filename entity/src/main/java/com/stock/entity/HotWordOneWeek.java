package com.stock.entity;

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
@Builder(toBuilder = true)
//@Document(indexName = "hotWordOneWeek")
public class HotWordOneWeek {

  private Long id;
  private Integer times;
  private String word;
}
