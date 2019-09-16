package com.framework.mybatis.plus.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "cdgenerator")
@Data
public class CdGeneratorConfiguration {

    private String parentPackage = "com";

    private String author = "xiaolong.song";

    private String outputDir = System.getProperty("user.dir");

    private Boolean skipView = false;//是否略过视图

    private String tables;//需要生成的表，多个用逗号隔开。

    private String copyright="";//代码生成版权

    public String getParentPackagePath(){
        if(StringUtils.hasLength(parentPackage)){
            String path=parentPackage;
            path=path.replaceAll("\\.", "/");
            return path;
        }
        return "";
    }

    public String[] getTabs(){
        Assert.hasLength(tables,"需要配置tables参数...");

        if("All".equals(tables.toUpperCase())){
            return null;
        }else if(tables.contains(",")){
            return tables.split(",");
        }else {
            return new String[]{tables};
        }
    }
}
