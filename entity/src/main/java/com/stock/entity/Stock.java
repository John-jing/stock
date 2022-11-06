package com.stock.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Stock {

  private long id;
  private String code;
  private String name;
  private String namePy;
  private double price;
  private String category;
  private String detailUrl;
  private java.sql.Timestamp createdOn;
  private java.sql.Timestamp updatedOn;

}
