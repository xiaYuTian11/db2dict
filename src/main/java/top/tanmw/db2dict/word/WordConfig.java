package top.tanmw.db2dict.word;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 * @author TMW
 * @since 2022/2/10 15:04
 */
public class WordConfig {
    // 表格第一行相关设置
    public static final String FIRST_ROW_COLOR = "C3BED4";// 表格第一行背景色
    public static final boolean IS_FIRST_ROW_COLOR = false;// 是否显示表格背景色
    public static final int FIRST_ROW_HEIGHT = 450;// 表格第一行高度
    public static final int FIRST_ROW_CEL_WIDTH = 2000;// 表格第一行中单元格的宽度
    public static final int FIRST_ROW_FONT_SIZE = 11;// 单元格中文字大小
    public static final String FIRST_ROW_FONT_FAMILY = "黑体";// 单元格中文字字体
    public static final boolean FIRST_ROW_FONT_BOLD = true;// 单元格中文字是否加粗
    public static final String FIRST_ROW_FONT_COLOR = "000000";// 单元格中文字颜色
    public static final ParagraphAlignment FIRST_ROW_ALIGNMENT = ParagraphAlignment.CENTER;// 设置表格文字对齐方式

    // 表格除了第一行，其他行相关设置
    public static final String ROW_COLOR = "EEEEFF";// （除了第一行外）表格内容行背景色
    public static final boolean IS_ROW_COLOR = true;// 是否显示表格背景色
    public static final int ROW_HEIGHT = 380;// 表格内容行高度
    public static final int ROW_CEL_WIDTH = 2000;// 表格内容行中单元格的宽度
    public static final int ROW_FONT_SIZE = 10;// 单元格中文字大小
    public static final String ROW_FONT_FAMILY = "楷体";// 单元格中文字字体
    public static final boolean ROW_FONT_BOLD = false;// 单元格中文字是否加粗
    public static final String ROW_FONT_COLOR = "FE4C40";// 单元格中文字颜色
    public static final ParagraphAlignment ROW_ALIGNMENT = ParagraphAlignment.CENTER;// 设置表格文字对齐方式

    // 表格标题相关设置
    public static final int TITLE_FONT_SIZE = 22;// 表格标题文字大小
    public static final String TITLE_FONT_FAMILY = "楷体";// 表格标题文字字体
    public static final boolean TITLE_FONT_BOLD = true;// 表格标题文字是否加粗
    public static final boolean TITLE_ADD_INDEX = true;// 表格标题是否自动加上序号
    public static final ParagraphAlignment TITLE_ALIGNMENT = ParagraphAlignment.LEFT;// 设置表格文字对齐方式
    public static final boolean IS_RETURN_ROW = false;// 是否换行

    // 表格主体相关设置
    public static final String TABLE_WIDTH = "10000";// 表格宽度

    public static String EXPORT_FILE_PATH = "/";// 导出文件的存放地址

}
