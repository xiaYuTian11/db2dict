# db2dict

数据库导出数据字典，支持多种数据库,包含：mysql,pgsql,oracle,kingbase(人大金仓)，dm(达梦)

## 快速使用

1.指定maven仓库
```xml

<repositories>
    <repository>
        <id>maven-public</id>
        <name>maven-public</name>
        <url>https://s01.oss.sonatype.org/content/repositories/releases</url>
        <layout>default</layout>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>alimaven</id>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    </repository>
</repositories>
```

2.引入依赖
```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>db2dict</artifactId>
    <version>${newVersion}</version>
</dependency>
```


3.在resources文件下配置dbconfig.txt
```properties
## 注意mysql url 需要在后面添加 nullCatalogMeansCurrent=true
url=jdbc:postgresql://127.0.0.1:5432/demo
driver=org.postgresql.Driver
username=postgres
password=postgres
## mysql,oracle,pgsql,kingbase,dm
type=pgsql
## 包含指定前缀的表
includePrefix=sys
## 去除指定前缀表
excludePrefix=
## 导出路径,windwos 写两个 \\
exportPath=C:\\Users\\Administrator\\Desktop\\数据字典.doc
```

4.运行
```java
public class Main {
    public static void main(String[] args) throws Exception{
        Application.run("D:\\project\\tmw\\demo\\demo\\src\\main\\resources\\dbconfig.txt");
        // 第二种
        String path = Db2dictApplication.class.getClassLoader().getResource("dbconfig.txt").getPath();
        Db2dictApplication.run(path);
    }
}
```
