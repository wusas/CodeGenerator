package com.framework.mybatis.plus.exception;

public class GeneratorException extends Exception {

    public GeneratorException(){
        super("applaction.yml 配置异常，请检查配置...");
    }

    public GeneratorException(String attr){
        super("applaction.yml '"+attr+"' 配置异常，请检查配置...");
    }
}
