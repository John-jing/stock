package com.stock.core.export;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.stock.core.entity.DailyLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author caijinglong
 * @date 2022-11-19
 */
@Slf4j
@Component
public class TxtExport implements Export {

  public static void main(String[] args) throws IOException {
    List<DailyLimit> dailyRaisingLimits = new ArrayList<>();
    DailyLimit dailyLimit = new DailyLimit()
            .setDaily(LocalDate.now())
            .setName("name")
            .setCode("code")
            .setPrice(2.1);
    dailyRaisingLimits.add(dailyLimit);
//    new TxtExport().export(JSONUtil.parseArray(dailyRaisingLimits),
//            "dailyRaisingLimit", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));

    String syncShFile = System.getProperty("user.dir") + "\\sync\\sync.sh";
    String commandStr = FileUtil.readUtf8String(syncShFile);
    System.out.println(commandStr);
    System.out.println(syncShFile);
    Process process = Runtime.getRuntime().exec("git add .");
    InputStreamReader ips = new InputStreamReader(process.getInputStream());
    BufferedReader br = new BufferedReader(ips);
    String line;
    while ((line = br.readLine()) != null) {
      System.out.println(line);
    }
    process = Runtime.getRuntime().exec("git status");
    ips = new InputStreamReader(process.getInputStream());
    br = new BufferedReader(ips);
    while ((line = br.readLine()) != null) {
      System.out.println(line);
    }

  }

  @Override
  public void export(JSONArray jsonArray, String filePath, String fileName) {
    if (jsonArray == null || jsonArray.isEmpty()) {
      return;
    }
    try {

      String fileNameWithPath = StrUtil.join("\\", PATH, filePath, fileName + fileSuffix());
      log.info("export start... fileNameWithPath:{}, jsonArray.size:{}", fileNameWithPath, jsonArray.size());
      File file = FileUtil.file(fileNameWithPath);
      log.info("export file absolutePath:{}. ", file.getAbsolutePath());

      // write header
      List<String> headerKeys = extractHeader(JSONUtil.parseObj(jsonArray.get(0)));
      FileUtil.writeUtf8Lines(Collections.singletonList(headerLine(headerKeys)), file);

      // write body
      List<List<String>> valueLineList = extractValue(headerKeys, jsonArray);
      FileUtil.appendUtf8Lines(valueLineList, file);
    } catch (Exception e) {
      log.error("txt export error.", e);
    }
  }

  @Override
  public void export(JSONObject jsonObject, String filePath, String fileName) {
    try {
      export(new JSONArray(jsonObject), filePath, fileName);
    } catch (Exception e) {
      log.error("txt export error.", e);
    }
  }

  private String headerLine(List<String> headerKeys) {
    return StrUtil.join(",", headerKeys);
  }

  @Override
  public String fileSuffix() {
    return ".txt";
  }
}
