package top.tanmw.db2dict.entity;

import java.util.LinkedHashMap;

/**
 * @author TMW
 * @since 2022/2/10 9:57
 */
public class DbConstant {


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
     * 表结构信息
     */
    public static LinkedHashMap<String, String> TABLE_INFO_RELATION = new
            LinkedHashMap<String, String>() {
                {
                    this.put("tableName", "表名称");
                    this.put("tableComment", "表备注");
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

    public static String URL = "url";
    public static String DRIVER = "driver";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String TYPE = "type";
    public static String EXPORT_PATH = "exportPath";

}
