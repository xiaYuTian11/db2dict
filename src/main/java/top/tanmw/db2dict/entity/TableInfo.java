package top.tanmw.db2dict.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 表信息
 *
 * @author TMW
 * @since 2022/2/10 10:11
 */
@Data
@Builder
public class TableInfo {
    private String tableName;
    private String tableComment;
    private String title;
    private List<List<String>> fieldList;
}
