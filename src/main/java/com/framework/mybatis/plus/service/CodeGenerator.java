/**
 * Copyright @2019/2/12 songxiaolong All Rights Reserved
 */
package com.framework.mybatis.plus.service;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.framework.mybatis.plus.config.CodeGeneratorPlusConfig;
import com.framework.mybatis.plus.config.SystemConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author xiaolong.song
 * @Package com.framework.mybatis.plus
 * @Description: 代码生成器
 * @email loye.song@foxmail.com
 * @date 2019/2/12 11:19
 */
public class CodeGenerator {

    private static GeneratorUtil generatorUtil = new GeneratorUtil();

    private static CodeGeneratorPlusConfig config = generatorUtil.config;


    private static void executeCodeGenerator() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(config.getCdGeneratorConfiguration().getOutputDir()+"/"+config.getModuleName()+"/src/main/java");
        gc.setAuthor(config.getCdGeneratorConfiguration().getAuthor());
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
//        gc.setEntityName("%sEntity");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        mpg.setDataSource(generatorUtil.getDataSource());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(config.getModuleName());
        pc.setParent(config.getCdGeneratorConfiguration().getParentPackage());
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            //自定义属性注入:abc
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put(SystemConstants.COPYRIGHT.getVal(), config.getCdGeneratorConfiguration().getCopyright());
                map.put("parentPackage",config.getCdGeneratorConfiguration().getParentPackage());
                this.setMap(map);
            }
        };
        // 自定义输出配置
        cfg.setFileOutConfigList(generatorUtil.outputTemplatesFileConfig(pc.getModuleName()));

        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        templateConfig.setXml(null);
        templateConfig.setController(null);
        templateConfig.setMapper(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
//        strategy.setTablePrefix(config.getTableName());//表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true);
        strategy.setSuperServiceClass("com.framework.mybatis.plus.supser.cls.BaseIService");
        strategy.setSuperServiceImplClass("com.framework.mybatis.plus.supser.cls.BaseServiceImpl");
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(false);//驼峰转连字符
        strategy.setSkipView(config.getCdGeneratorConfiguration().getSkipView());//是否跳过视图
        strategy.setInclude(config.getCdGeneratorConfiguration().getTabs());
        mpg.setStrategy(strategy);
        // 切换为 freemarker 模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


    public static void execute(){
        Connection conn=null;
        try {
            generatorUtil.inputCodeGenerator();

            if("ALL".equals(config.getCdGeneratorConfiguration().getTables().toUpperCase())){
                conn=generatorUtil.getDataSource().getConn();
                Map<String,String> map=new HashMap<>();

                //创建一个Statement对象
                Statement stmt = conn.createStatement();
                //检索数据
                StringBuffer sql=new StringBuffer("SELECT * from ( ");
                sql.append("SELECT DISTINCT ");
                sql.append("SUBSTRING_INDEX(table_name,'_',1) AS table_prefix ");
                sql.append("FROM mysql.`innodb_table_stats` ");
                sql.append("WHERE database_name='"+conn.getCatalog()+"' ");
                sql.append(") t where t.table_prefix!='sys' ");
                ResultSet rs = stmt.executeQuery(sql.toString());
                while(rs.next()) {
                    String tablePrefix=rs.getString(1);
//                    String moduleName=config.getModuleName()+"-"+tablePrefix;
                    String moduleName=tablePrefix;

                    StringBuffer sqlstr=new StringBuffer("SELECT table_name ");
                    sqlstr.append("FROM mysql.`innodb_table_stats` ");
                    sqlstr.append("WHERE database_name='"+conn.getCatalog()+"' ");
                    sqlstr.append(" and table_name like '"+tablePrefix+"%' ");

                    StringBuffer tableName=new StringBuffer();

                    Statement statement=conn.createStatement();
                    ResultSet rset = statement.executeQuery(sqlstr.toString());
                    while(rset.next()) {
                        tableName.append(rset.getString(1)+",");
                    }

                    String tableNameStr=tableName.deleteCharAt(tableName.length()-1).toString();

                    map.put(moduleName,tableNameStr);
                }

                for(String key:map.keySet()){
                    config.setModuleName(key);
                    config.getCdGeneratorConfiguration().setTables(map.get(key));
                    executeCodeGenerator();
                }

            }else{
                executeCodeGenerator();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(null!=conn)  conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
