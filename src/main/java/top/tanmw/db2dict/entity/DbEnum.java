package top.tanmw.db2dict.entity;

import cn.hutool.core.util.StrUtil;

/**
 * 数据库枚举类
 *
 * @author TMW
 * @since 2022/2/10 11:07
 */
public enum DbEnum {
    MYSQL("mysql"), PGSQL("pgsql"), ORACLE("oracle"),
    KINGBASE("kingbase"), DM("dm"), UNKNOWN("unknown");
    private String name;

    DbEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static DbEnum getDbEnum(String name) {
        for (DbEnum dbEnum : DbEnum.values()) {
            if (StrUtil.equals(dbEnum.getName(), name)) {
                return dbEnum;
            }
        }
        return UNKNOWN;
    }
}
