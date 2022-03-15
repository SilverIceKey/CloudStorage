package com.silvericekey.cloudstorage.mybatisPlus;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.silvericekey.cloudstorage.common.Constants;
import com.silvericekey.cloudstorage.config.SilverIceKeyException;

import java.util.Collections;
import java.util.Scanner;

/**
 * 新的代码生成器
 * @author myq
 * @date 2022/1/17
 */
public class NewCodeGenerater {
    public static String url = "jdbc:mysql://home.silvericekey.fun:3306/cloudstorage?characterEncoding=UTF-8";
    public static String username = "cloudstorage";
    public static String password = "CloudStorage:2871";
    public static String driverClassName = "com.mysql.jdbc.Driver";
//    public static String driverClassName = "com.mysql.cj.jdbc.Driver";
    public static String basePath = Constants.SERVICE_PACKAGE + ".features";
    public static String module = "/"; // machine   applet  web

    private static boolean isPackageNeedPrefix = true;//包名是否需要表前缀 如表名：auth_user
    private static boolean isModuleNeedPrefix = true;//模块名是否需要表前缀
    private static boolean isClassNeedPrefix = false;//类名是否需要表前缀;false,SysUser
    private static boolean isNeedController = false;//是否要生成controller

    public static void main(String[] args) {
        //单个生成
        //singleTable(scanner("单个表名"));

        //多个生成，逗号隔开
        multiTable(scanner("多个表名，逗号隔开"));

        //auth_user,auth_role,auth_permission,auth_user_role,auth_role_permission

//        System.out.println("sys".split("_").length);
//        System.out.println(StringUtils.underlineToCamel("sys_integral_record"));

    }

    public static String scanner(String tip){
        Scanner scanner = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        builder.append("请输入 " + tip + "：");
        System.out.println(builder.toString());

        if (scanner.hasNext()) {
            String input = scanner.next();
            if (StrUtil.isNotEmpty(input)) {
                return input;
            }
        }
        throw new SilverIceKeyException("请输入正确的" + tip + "！");
    }

    public static void singleTable(String tableName) {
        generate(tableName);
        if(scanner("是否继续生成：y/n").equalsIgnoreCase("y")){
            singleTable(scanner("单个表名"));
        }
    }

    public static void multiTable(String tableName) {
        if (tableName.contains(",")) {
            String[] tables = tableName.split(",");
            for (int i = 0; i < tables.length; i++) {
                generate(tables[i]);
            }
        }else{
            generate(tableName);
        }
    }



    public static void generate(String tableName) {
        String moduleName = tableName.split("_")[0];
        String projectPath = System.getProperty("user.dir");//项目所在的路径
        String mapperXmlPath = projectPath + module + "/src/main/resources/mapper/" + moduleName;

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("myq") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + module +"/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(isPackageNeedPrefix ? (basePath + "." + StrUtil.toCamelCase(moduleName)) : basePath) // 设置父包名
                            .moduleName(StrUtil.toCamelCase(isModuleNeedPrefix ? (tableName.substring(moduleName.length() + 1)) : tableName)) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperXmlPath)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .entityBuilder().enableLombok() //lombok实体类
                            .enableColumnConstant().logicDeleteColumnName("logic_flag")
                            .enableColumnConstant().versionColumnName("version")
                            .idType(IdType.ASSIGN_ID)
                    ;
                    // 设置过滤表前缀
                    if (isClassNeedPrefix) {
                        builder.addTablePrefix((moduleName + "_"));
                    }
                })
                .templateConfig(builder -> {
                    builder.entity("templates/ftl/entity.java2")
                            .service("templates/ftl/service.java2")
                            .serviceImpl("templates/ftl/serviceImpl.java2")
                            .controller(null) //不生成controller
                    ;
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
