package top.tanmw.db2dict;

import lombok.extern.slf4j.Slf4j;
import top.tanmw.db2dict.db.DbConfig;
import top.tanmw.db2dict.db.DbConfigFactory;
import top.tanmw.db2dict.entity.DbEnum;
import top.tanmw.db2dict.entity.TableInfo;
import top.tanmw.db2dict.word.WordConfig;
import top.tanmw.db2dict.word.WordUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static top.tanmw.db2dict.db.DbConfigFactory.dbEnum;
import static top.tanmw.db2dict.entity.DbConstant.EXPORT_PATH;
import static top.tanmw.db2dict.entity.DbConstant.TYPE;

/**
 * 启动类
 *
 * @author TMW
 * @since 2022/2/10 9:25
 */
@Slf4j
public class Application {

    public static void main(String[] args) throws Exception {
        Properties prop = getProp();
        if (Objects.isNull(prop)) {
            return;
        }
        DbConfig dbConfig = DbConfigFactory.getDbConfig();
        dbConfig.init(prop);
        final List<TableInfo> tableList = dbConfig.getTableList();
        WordUtil wordUtil = new WordUtil();
        wordUtil.writeTableToWord(tableList);
    }

    public static void run(String url) throws Exception {
        Properties prop = getProperties(url);
        initProp(prop);
        DbConfig dbConfig = DbConfigFactory.getDbConfig();
        dbConfig.init(prop);
        List<TableInfo> tableList = dbConfig.getTableList();
        WordUtil wordUtil = new WordUtil();
        wordUtil.writeTableToWord(tableList);
    }

    public static Properties getProperties(String url) throws Exception {
        Properties properties = new Properties();
        File file = new File(url);
        InputStream in = Files.newInputStream(file.toPath());
        properties.load(in);
        return properties;
    }

    public static Properties getProp() {
        Properties properties = new Properties();
        try {
            log.info("...读取配置文件...");
            // 使用InPutStream流读取properties文件
            final String configPath = Application.class.getClassLoader().getResource("dbconfig.txt").getPath();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configPath));
            properties.load(bufferedReader);
            initProp(properties);
            return properties;
        } catch (Exception e) {
            log.error("读取配置文件失败", e);
            return null;
        }
    }

    public static void initProp(Properties properties) {
        dbEnum = DbEnum.getDbEnum(properties.getProperty(TYPE));
        WordConfig.EXPORT_FILE_PATH = properties.getProperty(EXPORT_PATH);
    }

}
