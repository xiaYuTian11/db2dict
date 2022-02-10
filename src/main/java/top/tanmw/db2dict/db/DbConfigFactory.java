package top.tanmw.db2dict.db;

import top.tanmw.db2dict.entity.DbEnum;

/**
 * @author TMW
 * @since 2022/2/10 11:06
 */
public class DbConfigFactory {

    public static DbConfig getDbConfig(DbEnum dbEnum) {
        switch (dbEnum) {
            case MYSQL:
                return new MysqlConfig();
            case ORACLE:
                return new OracleConfig();
            case PGSQL:
                return new PgSqlConfig();
            case KINGBASE:
                return new KingBaseConfig();
            case DM:
                return new DmConfig();
            default:
                throw new RuntimeException("暂不支持该类型数据库，请联系开发者：875618779@qq.com");
        }
    }

}
