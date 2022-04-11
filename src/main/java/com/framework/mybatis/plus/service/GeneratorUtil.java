package com.framework.mybatis.plus.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.framework.mybatis.plus.config.CdConfiguration;
import com.framework.mybatis.plus.config.CodeGeneratorPlusConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneratorUtil {

    public CodeGeneratorPlusConfig config = CdConfiguration.getInstance().getGeneratorPlusConfig();

    enum TEMP {

        /**
         * 模板枚举
         */
        xml("/templates/mapper.xml.ftl"),

        mapper("/templates/mapper.java.ftl"),

        controller("/templates/controller.java.ftl"),

        service("/templates/service.java.ftl"),

        impl("/templates/serviceImpl.java.ftl");

        private String value;

        TEMP(String value) {
            this.value = value;
        }

        public String getVal() {
            return value;
        }
    }

    /**
     * 获取输入路径和文件
     *
     * @param name       输出文件类型：mapper、controller、service、serviceImpl、mapperXml
     * @param moduleName
     * @param tableInfo
     * @return
     */
    private String getOutputFileString(String name, String prjName, TableInfo tableInfo) {
        String outputFileString = null;
        switch (name) {
            case "mapper":
                outputFileString =  StringUtils.isEmpty(config.getCdGeneratorConfiguration().getMapperPackage()) ?  config.getCdGeneratorConfiguration().getOutputDir() :
                config.getCdGeneratorConfiguration().getMapperPackage() + "/" + prjName + "/src/main/java";
                outputFileString += "/mapper/" + tableInfo.getEntityName() + ConstVal.MAPPER + StringPool.DOT_JAVA;
                break;
            case "controller":
                outputFileString =  StringUtils.isEmpty(config.getCdGeneratorConfiguration().getControllerPackage()) ?  config.getCdGeneratorConfiguration().getOutputDir() :
                        config.getCdGeneratorConfiguration().getControllerPackage() + "/" + prjName + "/src/main/java";
                outputFileString += "/controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
                break;
            case "service":
                outputFileString =  StringUtils.isEmpty(config.getCdGeneratorConfiguration().getServerPackage()) ?  config.getCdGeneratorConfiguration().getOutputDir() :
                        config.getCdGeneratorConfiguration().getServerPackage() + "/" + prjName + "/src/main/java";
                outputFileString += "/service/I" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
                break;
            case "serviceImpl":
                outputFileString =  StringUtils.isEmpty(config.getCdGeneratorConfiguration().getServerPackage()) ?  config.getCdGeneratorConfiguration().getOutputDir() :
                        config.getCdGeneratorConfiguration().getServerPackage() + "/" + prjName + "/src/main/java";
                outputFileString += "/service/impl/" + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
                break;
            default:
                break;
        }

        return outputFileString;
    }

    /**
     * 输出模板配置
     *
     * @return
     */
    public List<FileOutConfig> outputTemplatesFileConfig(String prjName) {
        List<FileOutConfig> focList = new ArrayList<>();

        for (GeneratorUtil.TEMP temp : GeneratorUtil.TEMP.values()) {
            focList.add(new FileOutConfig(temp.getVal()) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    String type = temp.getVal().substring(temp.getVal().indexOf("."), temp.getVal().lastIndexOf("."));
                    String name = temp.getVal().substring(temp.getVal().lastIndexOf("/") + 1, temp.getVal().indexOf("."));
                    String outputFileString = StringUtils.isEmpty(config.getCdGeneratorConfiguration().getMapperPackage()) ?
                            config.getCdGeneratorConfiguration().getOutputDir() : config.getCdGeneratorConfiguration().getMapperPackage();
                    switch (type) {
                        case StringPool.DOT_JAVA:
                            outputFileString = getOutputFileString(name, prjName, tableInfo);
                            break;
                        case StringPool.DOT_XML:
                            outputFileString = outputFileString + "/" + prjName + config.getModuleName() + "/src/main/resources/"
                                    + config.getCdGeneratorConfiguration().getParentPackagePath()
                                    + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                        default:
                            break;
                    }
                    return outputFileString;
                }
            });
        }

        return focList;
    }


    /**
     * 数据源配置
     *
     * @return
     */
    public DataSourceConfig getDataSource() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(config.getUrl());
        // dsc.setSchemaName("public");
        dsc.setDriverName(config.getDriverClassName());
        dsc.setUsername(config.getUsername());
        dsc.setPassword(config.getPassword());
        dsc.setDbType(getDbType(config.getDriverClassName()));

        return dsc;
    }


    /**
     * 判断数据库类型
     *
     * @return 类型枚举值
     */
    public DbType getDbType(String driverName) {
        DbType dbType = null;
        if (driverName.contains("mysql")) {
            dbType = DbType.MYSQL;
        } else if (driverName.contains("oracle")) {
            dbType = DbType.ORACLE;
        } else if (driverName.contains("postgresql")) {
            dbType = DbType.POSTGRE_SQL;
        } else if (driverName.contains("sqlserver")) {
            dbType = DbType.SQL_SERVER;
        } else {
            throw new MybatisPlusException("Unknown type of database!");
        }
        return dbType;
    }


    /**
     * 代码生成前的输入项
     */
    public void inputCodeGenerator(String prjName) {
        if (StringUtils.isEmpty(prjName)) {
            config.setModuleName(scanner("模块名（groupId）"));
        } else {
            config.setModuleName(prjName);
        }
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
