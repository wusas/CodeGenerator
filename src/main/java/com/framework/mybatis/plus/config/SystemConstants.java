package com.framework.mybatis.plus.config;

/**
 * 代码代码生成器要配置的参数 cdgenerator 下的配置属性
 */
public enum  SystemConstants {
    /**
     * 代码生成器配置parent key
     */
    CD_GENERATOR("cdgenerator"),
    /**
     * 生成代码父类包名
     */
    PARENT_PACKAGE("parent-package"),

    CONTROLLER_PACKAGE("controller-package"),

    SERVER_PACKAGE("server-package"),

    MAPPER_PACKAGE("mapper-package"),

    ENTITY_PACKAGE("entity-package"),
    /**
     * 代码生成作者
     */
    AUTHOR("author"),
    /**
     * 代码输出地址
     */
    OUTPUT_DIR("output-dir"),
    /**
     * 是否跳过视图
     */
    SHIP_VIEW("skip-view"),
    /**
     * 代码版权
     */
    COPYRIGHT("copyright"),
    /**
     * 需要生成的表
     */
    TABLES("tables");

    private String val;

    SystemConstants(String val){
        this.val=val;
    }

    public String getVal(){
        return val;
    }
}
