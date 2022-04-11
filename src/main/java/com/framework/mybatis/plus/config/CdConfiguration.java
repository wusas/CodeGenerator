package com.framework.mybatis.plus.config;

import com.framework.mybatis.plus.exception.GeneratorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;

@Slf4j
public class CdConfiguration {

    private CdConfiguration(){}

    private static CdConfiguration cdConfiguration=new CdConfiguration();

    public static CdConfiguration getInstance(){
        return cdConfiguration;
    }


    public LinkedHashMap<String,LinkedHashMap> getConfiguration() throws Exception {
        Yaml yaml = new Yaml();
        LinkedHashMap<String,LinkedHashMap> map=yaml.loadAs(CdConfiguration.class.getResourceAsStream("/application.yml"),
                LinkedHashMap.class);

        LinkedHashMap<String,LinkedHashMap> linkedHashMap= map.get("spring");
        Assert.notEmpty(linkedHashMap,"'spring' Property needs to be configured");

        LinkedHashMap<String,String> linkedHashMap1=linkedHashMap.get("profiles");
        if(ObjectUtils.isEmpty(linkedHashMap1)) return map;

        String active=linkedHashMap1.get("active");
        Assert.hasLength(active,"'active' Property needs to be configured");

        map=yaml.loadAs(CdConfiguration.class.getResourceAsStream("/application-"+active+".yml"),
                LinkedHashMap.class);

        return map;
    }

    public CodeGeneratorPlusConfig getGeneratorPlusConfig() {
        try {
            LinkedHashMap<String,LinkedHashMap> linkedHashMap=getConfiguration();

            LinkedHashMap<String,LinkedHashMap> spring=linkedHashMap.get("spring");

            LinkedHashMap<String,String> datasource=spring.get("datasource");
            Assert.notEmpty(datasource,"'datasource' Property needs to be configured");

            CodeGeneratorPlusConfig config=new CodeGeneratorPlusConfig();

            config.setPassword(datasource.get("password"));
            config.setUrl(datasource.get("url"));
            config.setUsername(datasource.get("username"));
            config.setDriverClassName(datasource.get("driverClassName"));


            LinkedHashMap<String,Object> cdgenerator=linkedHashMap.get(SystemConstants.CD_GENERATOR.getVal());

            CdGeneratorConfiguration cdGeneratorConfiguration=new CdGeneratorConfiguration();
            Object parentPackage=cdgenerator.get(SystemConstants.PARENT_PACKAGE.getVal());
            Object controllerPackage=cdgenerator.get(SystemConstants.CONTROLLER_PACKAGE.getVal());
            Object serverPackage=cdgenerator.get(SystemConstants.SERVER_PACKAGE.getVal());
            Object mapperPackage=cdgenerator.get(SystemConstants.MAPPER_PACKAGE.getVal());
            Object entityPackage=cdgenerator.get(SystemConstants.ENTITY_PACKAGE.getVal());
            if(!ObjectUtils.isEmpty(parentPackage))
                cdGeneratorConfiguration.setParentPackage(String.valueOf(parentPackage));
            if(!ObjectUtils.isEmpty(controllerPackage))
                cdGeneratorConfiguration.setControllerPackage(String.valueOf(controllerPackage));
            if(!ObjectUtils.isEmpty(serverPackage))
                cdGeneratorConfiguration.setServerPackage(String.valueOf(serverPackage));
            if(!ObjectUtils.isEmpty(mapperPackage))
                cdGeneratorConfiguration.setMapperPackage(String.valueOf(mapperPackage));
            if(!ObjectUtils.isEmpty(entityPackage))
                cdGeneratorConfiguration.setEntityPackage(String.valueOf(entityPackage));
            Object author=cdgenerator.get(SystemConstants.AUTHOR.getVal());
            if(!ObjectUtils.isEmpty(author))
                cdGeneratorConfiguration.setAuthor(String.valueOf(author));

            Object outputDir=cdgenerator.get(SystemConstants.OUTPUT_DIR.getVal());
            Assert.notNull(outputDir,SystemConstants.OUTPUT_DIR.getVal()+" 需要配置...");
            cdGeneratorConfiguration.setOutputDir(String.valueOf(outputDir));

            Object skipView=cdgenerator.get(SystemConstants.SHIP_VIEW.getVal());
            if(!ObjectUtils.isEmpty(skipView))
                cdGeneratorConfiguration.setSkipView(Boolean.valueOf(String.valueOf(skipView)));

            Object copyright=cdgenerator.get(SystemConstants.COPYRIGHT.getVal());
            if(!ObjectUtils.isEmpty(copyright))
                cdGeneratorConfiguration.setCopyright(String.valueOf(copyright));

            Object tables=cdgenerator.get(SystemConstants.TABLES.getVal());
            Assert.notNull(tables,SystemConstants.TABLES.getVal()+" 需要配置...");
            cdGeneratorConfiguration.setTables(String.valueOf(tables));

            config.setCdGeneratorConfiguration(cdGeneratorConfiguration);

            return config;
        }catch (Exception e){
            e.printStackTrace();
            log.error(new GeneratorException().getMessage());
            return null;
        }
    }
}
