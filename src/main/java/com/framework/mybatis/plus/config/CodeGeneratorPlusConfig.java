/**
 * Copyright @2019/2/13 songxiaolong All Rights Reserved
 */
package com.framework.mybatis.plus.config;

import lombok.Data;

/**
 * @author wu.sha
 * @Package com.framework.mybatis.plus;
 * @Description: mybatisPlus自定义配置文件
 * @email wusha@meibangtech.com
 */
@Data
public class CodeGeneratorPlusConfig {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 模块名
     */
    private String moduleName;

    private String url;

    private String driverClassName;

    private String username;

    private String password;

    private CdGeneratorConfiguration cdGeneratorConfiguration;
}
