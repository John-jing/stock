package com.stock.core;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.stock.core.entity.base.BaseEntity;

import java.util.Collections;
import java.util.Scanner;

/**
 * @author caijinglong
 * @date 2022-11-06
 */
public class CodeGen {

  private static final String DB_URL = "jdbc:mysql://localhost:3306/astock?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";
  private static final String DB_USERNAME = "root";
  private static final String DB_PASSWORD = "root";
  private static final String BASE_PATH = "core\\src\\main\\java";
  private static final String BASE_MAPPER_PATH = "core\\src\\main\\resources\\mapper";
  private static final String BASE_PACKAGE = "com.stock.core";

  public static void main(String[] args) {
    String tableNameStr = scanner("请输入表名(多个表英文逗号分隔)：");
    String[] tableNames = StrUtil.split(tableNameStr, ",");

    DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig
            .Builder(DB_URL, DB_USERNAME, DB_PASSWORD)
            .dbQuery(new MySqlQuery())
            .typeConvert(new MySqlTypeConvert())
            .keyWordsHandler(new MySqlKeyWordsHandler());

    FastAutoGenerator.create(dataSourceConfig)
            .globalConfig(builder -> {
              builder.author("caijinglong") // 设置作者
                      .commentDate("YYYY-MM-DD HH:mm:ss")//注释日期
                      .enableSwagger() // 开启 swagger 模式
                      .fileOverride() // 覆盖已生成文件
                      .outputDir(BASE_PATH) // 指定输出目录
                      .disableOpenDir();
            })
            .packageConfig(builder -> {
              builder.parent(BASE_PACKAGE) // 设置父包名
//                      .moduleName("system") // 设置父包模块名
                      .pathInfo(Collections.singletonMap(OutputFile.mapperXml, BASE_MAPPER_PATH)); // 设置mapperXml生成路径
            })
            .strategyConfig(builder -> {
              builder.addInclude(tableNames) // 设置需要生成的表名
//                      .addTablePrefix("t_", "c_") // 设置过滤表前缀
              ;
              builder.entityBuilder()
                      .superClass(BaseEntity.class)
                      .addSuperEntityColumns("id", "created_on", "updated_on")
                      .enableLombok()
                      .enableTableFieldAnnotation();//开启生成实体时生成字段注解
            })
            .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            .execute();
  }

  public static String scanner(String key) {
    Scanner scanner = new Scanner(System.in);
    System.out.println(key);
    return scanner.next();
  }
}
