package com.framework.mybatis.plus.config;

/**
 * 代码代码生成器要配置的参数 cdgenerator 下的配置属性
 */
public enum  SystemConstants {

    CD_GENERATOR("cdgenerator"),//代码生成器配置parent key

    PARENT_PACKAGE("parent-package"),//生成代码父类包名

    AUTHOR("author"),//代码生成作者

    OUTPUT_DIR("output-dir"),//代码输出地址

    SHIP_VIEW("skip-view"),//是否跳过视图

    COPYRIGHT("copyright"),//代码版权

    TABLES("tables");//需要生成的表

    private String val;

    SystemConstants(String val){
        this.val=val;
    }

    public String getVal(){
        return val;
    }
}
