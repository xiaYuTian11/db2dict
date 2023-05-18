# db2dict

数据库导出数据字典，支持多种数据库,包含：mysql,pgsql,oracle,kingbase(人大金仓)，dm(达梦)

## 快速使用

1.引入依赖
```xml

<dependency>
    <groupId>top.tanmw</groupId>
    <artifactId>db2dict</artifactId>
    <version>1.0.0</version>
</dependency>
```


2.在resources文件下配置dbconfig.txt
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

3.运行
```java
public class Main {
    public static void main(String[] args) throws Exception{
        Application.run("D:\\project\\tmw\\demo\\demo\\src\\main\\resources\\dbconfig.txt");
    }
}
```
