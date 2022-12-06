package com.stock.core.http;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author caijinglong
 * @date 2022-11-27
 */
public abstract class TimingTendencyClient implements Client, InitializingBean {

  private final static List<TimingTendencyClient> TIMING_TENDENCY_CLIENTS = new ArrayList<>();

  public static TimingTendencyClient randomClient() {
    return RandomUtil.randomEle(TIMING_TENDENCY_CLIENTS);
  }

  public void afterPropertiesSet() {
    if (TIMING_TENDENCY_CLIENTS.isEmpty()) {
      Map<String, TimingTendencyClient> timingTendencyClientMap = ContextLoader
              .getCurrentWebApplicationContext()
              .getBeansOfType(TimingTendencyClient.class);
      timingTendencyClientMap.forEach((name, client) -> {
        if (!this.getClass().getSimpleName().equalsIgnoreCase(name)) {
          TIMING_TENDENCY_CLIENTS.add(client);
        }
      });
    }
  }

}
