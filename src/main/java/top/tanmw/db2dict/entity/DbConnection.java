package top.tanmw.db2dict.entity;

import lombok.Data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * 连接实体类
 *
 * @author TMW
 * @since 2022/2/10 9:50
 */
@Data
public class DbConnection {
    private Connection connection;
    private DatabaseMetaData metaData;

}
