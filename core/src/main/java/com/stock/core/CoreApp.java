package com.stock.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author yuexichun
 * @date 2020/9/14
 */
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
  }

}

