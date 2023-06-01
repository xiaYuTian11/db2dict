# db2dict

数据库导出数据字典，支持多种数据库,包含：mysql,pgsql,oracle,kingbase(人大金仓)，dm(达梦)

## 快速使用

1.指定maven仓库
```xml

<repositories>
    <repository>
        <id>maven-public</id>
        <name>maven-public</name>
        <url>https://repo.maven.apache.org/maven2/</url>
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
    <version>1.0.0</version>
</dependency>
```


3.在resources文件下配置dbconfig.txt
```properties
## 注意mysql url 需要在后面添加 nullCatalogMeansCurrent=true
url=jdbc:postgresql://192.168.0.245:5432/devdb
driver=org.postgresql.Driver
username=postgres
password=20191809
## mysql,oracle,pgsql,kingbase,dm
type=pgsql
## 包含指定前缀的表
includePrefix=sys
## 去除指定前缀表
excludePrefix=
## 导出路径,windwos 写两个 \\
exportPath=C:\\Users\\Administrator\\Desktop\\政策库数据字典.doc
```

4.运行
```java
public class Main {
    public static void main(String[] args) throws Exception{
        Application.run("D:\\project\\tmw\\demo\\demo\\src\\main\\resources\\dbconfig.txt");
    }
}
```
