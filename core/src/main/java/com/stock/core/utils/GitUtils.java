package com.stock.core.utils;

import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

/**
 * @author caijinglong
 * @date 2022-11-20
 */
public class GitUtils {

  public static void push(Optional<String> commitMsgOptional) {
    try {
      String[] commands = {
              "git add .",
              "git commit -m " + StrUtil.wrap(commitMsgOptional.orElse("sync"), "'"),
              "git pull",
              "git push"
      };
      Runtime  runtime = Runtime.getRuntime();
      for (String command : commands) {
        Process process = runtime.exec(command);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
          System.out.println(line);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
