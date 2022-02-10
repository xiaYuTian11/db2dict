package top.tanmw.db2dict.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.tanmw.db2dict.entity.TableColumnInfo;
import top.tanmw.db2dict.entity.TableInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

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
     * 初始化
     */
    public void init(Properties properties);

    /**
     * 获取表信息
     *
     * @return
     */
    List<TableInfo> getTableList();

    /**
     * 获取表结构信息
     *
     * @return
     */
    List<TableColumnInfo> getTableColumnList();

}
