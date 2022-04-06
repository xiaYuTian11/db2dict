package top.tanmw.db2dict.db;

import top.tanmw.db2dict.entity.TableInfo;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 达梦
 *
 * @author TMW
 * @since 2022/2/10 10:04
 */
public class DmConfig extends AbstractDbConfig {

    @Override
    public List<TableInfo> getTableList() throws Exception {
        final List<TableInfo> tableList = super.getTableList();
        List<String> tableNameList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("select NAME from sysobjects where \"SUBTYPE$\"='UTAB' AND SCHID=(SELECT ID FROM sysobjects WHERE NAME='" + username + "' AND TYPE$='SCH');")) {
                while (rs.next()) {
                    // 注意：索引从1开始
                    String tableName = rs.getString(1);
                    tableNameList.add(tableName);
                }
            }
        }
        return tableList.stream().filter(tableInfo -> tableNameList.contains(tableInfo.getTableName())).collect(Collectors.toList());
    }
}
