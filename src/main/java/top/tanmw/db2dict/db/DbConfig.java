package top.tanmw.db2dict.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.tanmw.db2dict.entity.DbConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import java.util.LinkedHashMap;
import java.util.Properties;

import static top.tanmw.db2dict.entity.DbConstant.DRIVER;
import static top.tanmw.db2dict.entity.DbConstant.URL;

/**
 * 数据库接口
 *
 * @author TMW
 * @since 2022/2/10 9:29
 */
public interface DbConfig {
    public static final Logger LOGGER = LoggerFactory.getLogger(DbConfig.class);
    /**
     * 表结构信息
     */
    public static LinkedHashMap<String, String> TABLE_RELATION = new
            LinkedHashMap<String, String>() {
                {
                    this.put("COLUMN_NAME", "字段名称");
                    this.put("TYPE_NAME", "字段类型");
                    this.put("COLUMN_SIZE", "字段长度");
                    this.put("COLUMN_DEF", "默认值");
                    this.put("REMARKS", "备注信息");
                }
            };
    /**
     * 表索引信息
     */
    public static LinkedHashMap<String, String> TABLE_INDEX = new
            LinkedHashMap<String, String>() {
                {
                    this.put("INDEX_NAME", "索引名称");
                    this.put("non_unique", "是否唯一");
                }
            };
    /**
     * 表索引信息
     */
    public static LinkedHashMap<String, String> TABLE_LIST = new
            LinkedHashMap<String, String>() {
                {
                    this.put("TABLE_NAME", "表名称");
                    this.put("TABLE_COMMENT", "表注释");
                }
            };

    /**
     * 获取连接
     *
     * @return
     */
    default DbConnection getDbConfig() {
        DbConnection dbConnection = new DbConnection();
        Properties properties = new Properties();
        try {
            // 使用InPutStream流读取properties文件
            final String configPath = DbConfig.class.getClassLoader().getResource("/db.config").toString();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configPath));
            properties.load(bufferedReader);
            Class.forName(properties.getProperty(DRIVER));
            dbConnection.setConnection(DriverManager.getConnection(URL));
            dbConnection.setMetaData(dbConnection.getConnection().getMetaData());
        } catch (Exception e) {
            LOGGER.error("读取配置文件失败");
            return null;
        }
        return dbConnection;
    }

}
