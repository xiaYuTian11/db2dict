package top.tanmw.db2dict.db;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import top.tanmw.db2dict.entity.DbConstant;
import top.tanmw.db2dict.entity.TableInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static top.tanmw.db2dict.entity.DbConstant.*;

/**
 * 配置抽象类
 *
 * @author TMW
 * @since 2022/2/10 10:10
 */
@Slf4j
public abstract class AbstractDbConfig implements DbConfig {
    public Connection connection = null;
    public DatabaseMetaData metaData = null;
    public String username = "";
    public String[] excludePrefixList = new String[]{};

    @Override
    public void init(Properties properties) {
        try {
            log.info("...连接数据库...");
            // 使用InPutStream流读取properties文件
            final String driver = properties.getProperty(DRIVER);
            final String url = properties.getProperty(URL);
            username = properties.getProperty(USERNAME);
            final String password = properties.getProperty(PASSWORD);

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            metaData = connection.getMetaData();

            String excludePrefix = properties.getProperty(DbConstant.EXCLUDE_PREFIX);
            if (StrUtil.isNotBlank(excludePrefix)) {
                excludePrefixList = excludePrefix.split(",");
            }
        } catch (Exception e) {
            log.error("连接数据库失败", e);
        }
    }

    /**
     * 获取表信息
     */
    @Override
    public List<TableInfo> getTableList() throws Exception {
        log.info("...开始读取表信息...");
        // 表数据集
        ResultSet tableResultSet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
        List<TableInfo> tableInfoList = new ArrayList<>(64);
        while (tableResultSet.next()) {
            // 表信息
            String tableName = tableResultSet.getString("TABLE_NAME");
            String remarkes = tableResultSet.getString("REMARKS");
            remarkes = StrUtil.isNotBlank(remarkes) ? remarkes : tableName;
            List<List<String>> fieldList = new ArrayList<>(64);
            if (StrUtil.isNotBlank(tableName) && !StrUtil.startWithAny(tableName, excludePrefixList)) {
                log.info("...读取 {} 表结构...", tableName);
                // 表结构
                ResultSet resultSet = metaData.getColumns(null, "%", tableName, "%");
                while (resultSet.next()) {
                    List<String> columnList = new ArrayList<>();
                    for (String fieldName : TABLE_RELATION.keySet()) {
                        final String column = resultSet.getString(fieldName);
                        if(StrUtil.equalsIgnoreCase(fieldName,"REMARKS")&&StrUtil.isBlank(column)){
                            columnList.add(columnList.get(0));
                        }else{
                            columnList.add(column);
                        }

                    }
                    fieldList.add(columnList);
                }
                final TableInfo tableInfo = TableInfo.builder().tableName(tableName).tableComment(remarkes)
                        .title(tableName + "(" + remarkes + ")")
                        .fieldList(fieldList).build();
                tableInfoList.add(tableInfo);
            }
        }
        return tableInfoList;
    }

}
