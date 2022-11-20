package com.stock.core.export;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author caijinglong
 * @date 2022-11-19
 */
public interface Export {

  void export(JSONArray jsonArray, String filePath, String fileName);

  void export(JSONObject jsonObject, String filePath, String fileName);

  /**
   * 提取 model 字段名
   *
   * @param jsonObject
   * @return
   */
  default List<String> extractHeader(JSONObject jsonObject) {
    if (jsonObject == null) {
      return Collections.emptyList();
    }
    return new ArrayList<>(jsonObject.keySet());
  }


  /**
   * 提取 model 值
   *
   * @param keys
   * @param jsonArray
   * @return
   */
  default List<List<String>> extractValue(List<String> keys, JSONArray jsonArray) {
    if (jsonArray == null || jsonArray.isEmpty() || CollUtil.isEmpty(keys)) {
      return Collections.emptyList();
    }

    return jsonArray.stream()
            .map(item -> keys.stream()
                    .map(key -> {
                      Object valueObj = JSONUtil.parseObj(item).get(key);
                      return valueObj != null ? valueObj.toString() : "";
                    })
                    .collect(Collectors.toList()))
            .collect(Collectors.toList());
  }

  default String fileSuffix() {
    return "";
  }
}
