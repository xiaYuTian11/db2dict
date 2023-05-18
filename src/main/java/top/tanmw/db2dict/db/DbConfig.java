package top.tanmw.db2dict.db;

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
    List<TableInfo> getTableList() throws Exception;

}
