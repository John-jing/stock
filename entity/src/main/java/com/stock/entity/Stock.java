package com.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

  private long id;
  private long code;
  private String name;
  private double price;
  private String type;
  private String detailUrl;
  private java.sql.Timestamp createdOn;
  private java.sql.Timestamp updatedOn;

}
