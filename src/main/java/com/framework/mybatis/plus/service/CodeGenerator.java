/**
 * Copyright @2019/2/12 songxiaolong All Rights Reserved
 */
package com.framework.mybatis.plus.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.framework.mybatis.plus.config.CdConfiguration;
import com.framework.mybatis.plus.config.CodeGeneratorPlusConfig;
import com.framework.mybatis.plus.config.SystemConstants;

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


    public static void executeCodeGenerator() {
        generatorUtil.inputCodeGenerator();
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
        gc.setEntityName("%sEntity");
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
}
