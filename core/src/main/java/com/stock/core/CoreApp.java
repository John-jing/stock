package com.stock.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yuexichun
 * @date 2020/9/14
 */
@SpringBootApplication(scanBasePackages = {
   "com.stock.core"
})
//@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class CoreApp {

  public static void main(String[] args) {
    ApplicationContext applicationContext = SpringApplication.run(CoreApp.class, args);
//        SpringContextUtil.setApplicationContext(applicationContext);
  }

}

