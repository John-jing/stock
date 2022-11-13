package com.stock.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 股票
 * </p>
 *
 * @author caijinglong
 * @since 2022-11-310 22:56:44
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("stock")
@ApiModel(value = "Stock对象", description = "股票")
public class Stock implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty("编码")
  @TableField("`code`")
  private String code;

  @ApiModelProperty("名称")
  @TableField("`name`")
  private String name;

  @ApiModelProperty("名称-拼音")
  @TableField("name_py")
  private String namePy;

  @ApiModelProperty("现价")
  @TableField("price")
  private Double price;

  @ApiModelProperty("类型")
  @TableField("category")
  private String category;

  @ApiModelProperty("详情地址")
  @TableField("detail_url")
  private String detailUrl;

  @ApiModelProperty("创建时间")
  @TableField("created_on")
  private LocalDateTime createdOn;

  @ApiModelProperty("更新时间")
  @TableField("updated_on")
  private LocalDateTime updatedOn;

  @TableField("destroy")
  private Boolean destroy;


}
