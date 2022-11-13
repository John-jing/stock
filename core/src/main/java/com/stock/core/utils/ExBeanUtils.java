package com.stock.core.utils;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.Assert;

/**
 * @author caijinglong
 * @date 2022-11-06
 */
public class ExBeanUtils extends BeanUtils {

  public static <T> T copyProperties(Object source, Class<T> clazz) throws BeansException {
    Assert.notNull(clazz, "clazz not be null");
    if (source == null) {
      return null;
    }

    T t = ReflectUtil.newInstance(clazz);
    copyProperties(source, t);
    return t;
  }
}
