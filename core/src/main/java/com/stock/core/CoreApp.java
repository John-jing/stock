package com.stock.core;

import com.stock.core.schedule.StockJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @date 2020/9/14
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {
        "com.stock.core",
        "com.stock.es"
})
@EnableElasticsearchRepositories(basePackages = "com.stock.es.repository")
//@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class CoreApp {

  public static void main(String[] args) {
    ApplicationContext applicationContext = SpringApplication.run(CoreApp.class, args);

//        SpringContextUtil.setApplicationContext(applicationContext);
    log.info("========================startup success!================================");

    StockJob stockJob = applicationContext.getBean(StockJob.class);
    stockJob.syncStockList();

  }

}

