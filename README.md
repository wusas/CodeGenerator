# 代码生成器

## 使用方式

### 1. 引用jar

    <dependency>
          <groupId>com.framework.mybatis.plus</groupId>
          <artifactId>cdgenerator-spring-boot-starter</artifactId>
          <version>0.0.1-SNAPSHOT</version>
    </dependency>
   
### 2. 配置

    cdgenerator:
      #文件生成默认作者
      author: xiaolong.song
      #文件输出路径
      output-dir: /Users/songxiaolong/work/IdeaProjects
      #父级包名
      parent-package: com.fih
      #是否略过视图
      skip-view: true
      copyright: Fih
      #表名称配置，多个用逗号隔开，若全部则为All。注意不同数据库大小写敏感
      tables: AdConfig
    
## 3. 环境

    jdk : 1.8.0_201-b09
    springboot : 2.1.7.RELEASE
    
## 4. 运行

    public static void main(String[] args) {
        CodeGeneratorPlusMain.main(args);
    }