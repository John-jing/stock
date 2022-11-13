package com.stock.core.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 东方财富-强势股池
 * </p>
 *
 * @author caijinglong
 * @since 2022-11-313 23:55:52
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty("创建时间")
  @TableField("created_on")
  private LocalDateTime createdOn;

  @ApiModelProperty("更新时间")
  @TableField("updated_on")
  private LocalDateTime updatedOn;


}
