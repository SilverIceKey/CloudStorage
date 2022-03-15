package com.silvericekey.cloudstorage.mybatisPlus;//package com.myq.core.framework.mybatisPlus;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
//import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//import com.myq.core.framework.common.consts.BaseConst;
//import lombok.AllArgsConstructor;
//
//import java.util.*;
//
///**
// * mybatisPlus-plus 代码生成器 工具类
// *      支持 Velocity（默认）、Freemarker、Beetl，用户可以选择自己熟悉的模板引擎
// *      3.0.3 之后，需要手动添加相关依赖。
// *      参考官网配置：
// *          https://baomidou.gitee.io/mybatis-plus-doc/#/generate-code
// *
// */
//@AllArgsConstructor
//public class CodeGenerator {
//
//    public static String url = "jdbc:mysql://122.51.144.87:3306/db_club?characterEncoding=UTF-8";
//    public static String username = "root";
//    public static String password = "myqMYQ_137920";
//    public static String driverClassName = "com.mysql.jdbc.Driver";
////    public static String driverClassName = "com.mysql.cj.jdbc.Driver";
//    public static String basePath = BaseConst.SERVICE_PACKAGE + ".business";
//    public static String module = "/club-applet"; // machine   applet
//
//    private static boolean isPackageNeedPrefix = true;//包名是否需要表前缀 如表名：auth_user
//    private static boolean isModuleNeedPrefix = true;//模块名是否需要表前缀
//    private static boolean isClassNeedPrefix = false;//类名是否需要表前缀;false,SysUser
//    private static boolean isNeedController = false;//是否要生成controller
//
//    public static void main(String[] args) {
//        //单个生成
//        //singleTable(scanner("单个表名"));
//
//        //多个生成，逗号隔开
//        multiTable(scanner("多个表名，逗号隔开"));
//
//        //auth_user,auth_role,auth_permission,auth_user_role,auth_role_permission
//
////        System.out.println("sys".split("_").length);
////        System.out.println(StringUtils.underlineToCamel("sys_integral_record"));
//
//    }
//
//    public static String scanner(String tip){
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder builder = new StringBuilder();
//        builder.append("请输入 " + tip + "：");
//        System.out.println(builder.toString());
//
//        if (scanner.hasNext()) {
//            String input = scanner.next();
//            if (StringUtils.isNotEmpty(input)) {
//                return input;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }
//
//    public static void singleTable(String tableName) {
//        generate(tableName);
//        if(scanner("是否继续生成：y/n").equalsIgnoreCase("y")){
//            singleTable(scanner("单个表名"));
//        }
//    }
//
//    public static void multiTable(String tableName) {
//        if (tableName.contains(",")) {
//            String[] tables = tableName.split(",");
//            for (int i = 0; i < tables.length; i++) {
//                generate(tables[i]);
//            }
//        }else{
//            generate(tableName);
//        }
//    }
//
//
//    private static void generate(String tableName){
//
//        if(StringUtils.isEmpty(tableName) || tableName.split("_").length <= 1){
//            System.out.println("表名格式错误！");
//            return;
//        }
//        String moduleName = tableName.split("_")[0];
//        String packageName = tableName;//不区分表前缀
//
//
//
//            //
//            //代码生成器
//            AutoGenerator generator = new AutoGenerator();
//
//            //1.全局配置
//            GlobalConfig config = new GlobalConfig();
//            String projectPath = System.getProperty("user.dir");//项目所在的路径
//            config.setOutputDir(projectPath + module +"/src/main/java"); // 输出路径
//            config.setAuthor("myq"); //作者
//            config.setFileOverride(false); // 是否覆盖文件
//            config.setActiveRecord(false); //是否需要ActiveRecord模式
//            config.setEnableCache(false); //是否开启二级缓存
//            config.setSwagger2(true);
//            config.setBaseResultMap(false); // 是否生成表结果集
//            config.setBaseColumnList(false); // 是否生成字段列表
//            config.setKotlin(false); // 是否生成kotlin代码
//            config.setOpen(false); //是否打开输出目录
//            config.setServiceName("%sService"); // 自定义service名称，mp原生会自动加入IXxxService
//            generator.setGlobalConfig(config);
//
//
//            //2.数据源配置
//            DataSourceConfig dsConfig = new DataSourceConfig();
//            dsConfig.setDbType(DbType.MYSQL);//数据库类型
//            dsConfig.setDriverName(driverClassName);
//            dsConfig.setUsername(username);
//            dsConfig.setPassword(password);
//            dsConfig.setUrl(url);
//            dsConfig.setTypeConvert(new MySqlTypeConvert() {
//                /**
//                 * 字段类型转换
//                 *
//                 * @param globalConfig
//                 * @param fieldType
//                 * @return
//                 */
//                @Override
//                public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
//                    if (fieldType.equals("datetime")) {
//                        return DbColumnType.DATE;
//                    }
//                    if (fieldType.equals("date")) {
//                        return DbColumnType.DATE;
//                    }
//                    if (fieldType.equals("enum")) {
//                        return DbColumnType.STRING;
//                    }
//                    if (fieldType.equals("longtext")) {
//                        return DbColumnType.STRING;
//                    }
//                    return new MySqlTypeConvert().processTypeConvert(globalConfig, fieldType);
//                }
//            }); //自定义数据库表字段类型转换【可选】
//            generator.setDataSource(dsConfig);
//
//
//            //3.包名配置
//            PackageConfig packageConfig = new PackageConfig();
//            packageConfig.setParent(basePath); //不包含表前缀
//            if (isPackageNeedPrefix) {
//                packageConfig.setParent(basePath + "." + StringUtils.underlineToCamel(moduleName));
//            }
//            if (isModuleNeedPrefix) {
//                packageName = tableName.substring(moduleName.length() + 1);
//            }
//            packageConfig.setModuleName(StringUtils.underlineToCamel(packageName)); //功能名
//            generator.setPackageInfo(packageConfig);
//
//
//            //4.策略配置
//            StrategyConfig stConfig = new StrategyConfig();
//            stConfig.setNaming(NamingStrategy.underline_to_camel); //表名生成策略 下划线转驼峰
//            stConfig.setColumnNaming(NamingStrategy.underline_to_camel); //字段名生成策略 下划线转驼峰
//            stConfig.setEntityLombokModel(true); // lombok 模式 生成实体类
//            stConfig.setRestControllerStyle(true); //RestController 样式的controller
//
////        stConfig.setSuperControllerClass("com.dl.demo.common.controller.BaseController"); //controller父类
////        stConfig.setSuperEntityClass("com.baomidou.ant.common.BaseEntity"); //entity父类
//
//            //写于父类总的公众字段
////        stConfig.setControllerMappingHyphenStyle(true);
//            stConfig.setInclude(tableName); //需要生成的表
////        stConfig.setExclude(new String[]{"sys_user"}); // 排除需要生成的表
//
//            stConfig.setLogicDeleteFieldName("logic_flag");//逻辑删除
//            stConfig.setVersionFieldName("version");//加锁
//            if (isClassNeedPrefix) {
//                stConfig.setTablePrefix(moduleName + "_");//包含表前缀
//            }
//
//            generator.setStrategy(stConfig);
//
//
//            //4.自定义模板配置
//            TemplateConfig templateConfig = new TemplateConfig();
//            templateConfig.setXml(null);//去除xml的文件
//            //指定自定义的 模板路径
//            templateConfig.setEntity("templates/ftl/entity.java2"); // 模板路径 entity
//            templateConfig.setService("templates/ftl/service.java3");  // 模板路径 service
//            templateConfig.setServiceImpl("templates/ftl/serviceImpl.java3"); // 模板路径 serviceImpl
//        templateConfig.setController(null);
//        if (isNeedController) {
//            CodeGenerator2.generate(tableName);
//            templateConfig.setController("/templates/ftl/controller.java3");// 模板路径 controller}
//        }
//
//
//            generator.setTemplate(templateConfig);
//
//            // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
//            InjectionConfig cfg = new InjectionConfig() {
//                @Override
//                public void initMap() {
//                    Map<String, Object> map = new HashMap<String, Object>();
//                    map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mybatisPlus");
//                    this.setMap(map);
//                }
//            };
//            // 自定义输出配置
//            List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
//            focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
//                @Override
//                public String outputFile(TableInfo tableInfo) {
//                    return projectPath + module + "/src/main/resources/mapper/"
//                            + (isPackageNeedPrefix
//                            ? packageConfig.getParent().split("\\.")[packageConfig.getParent().split("\\.").length - 2] + "/"
//                            : "")
//                            + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//                }
//            });
//        cfg.setFileOutConfigList(focList);
//            generator.setCfg(cfg);
//
//
//            //5.执行代码生成器
//            generator.setTemplateEngine(new FreemarkerTemplateEngine()); //选择 freemarker 引擎，默认 Veloctiy
//            generator.execute();
//
//
//        }
//
//
//
//
//
//
//
//
//
//}
