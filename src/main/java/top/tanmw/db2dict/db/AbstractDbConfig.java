package top.tanmw.db2dict.db;

import lombok.extern.slf4j.Slf4j;
import top.tanmw.db2dict.entity.TableColumnInfo;
import top.tanmw.db2dict.entity.TableInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
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

    @Override
    public void init(Properties properties) {
        try {
            log.info("连接数据库...");
            // 使用InPutStream流读取properties文件
            final String driver = properties.getProperty(DRIVER);
            final String url = properties.getProperty(URL);
            final String username = properties.getProperty(USERNAME);
            final String password = properties.getProperty(PASSWORD);

            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            metaData = connection.getMetaData();
        } catch (Exception e) {
            log.error("连接数据库失败", e);
        }
    }

    /**
     * 获取表信息
     */
    @Override
    public List<TableInfo> getTableList() {
        return null;
    }

    /**
     * 获取表结构信息
     */
    @Override
    public List<TableColumnInfo> getTableColumnList() {
        return null;
    }

}
