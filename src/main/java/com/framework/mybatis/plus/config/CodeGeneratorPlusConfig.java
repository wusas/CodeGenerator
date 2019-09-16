/**
 * Copyright @2019/2/13 songxiaolong All Rights Reserved
 */
package com.framework.mybatis.plus.config;

import lombok.Data;

/**
 * @author xiaolong.song
 * @Package com.framework.mybatis.plus;
 * @Description: mybatisPlus自定义配置文件
 * @email loye.song@foxmail.com
 * @date 2019/2/13 15:59
 */
@Data
public class CodeGeneratorPlusConfig {

    private String tableName;//表名

    private String moduleName;//模块名

    private String url;

    private String driverClassName;

    private String username;

    private String password;

    private CdGeneratorConfiguration cdGeneratorConfiguration;
}
