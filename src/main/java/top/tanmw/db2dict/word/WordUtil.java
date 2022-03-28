package top.tanmw.db2dict.word;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import top.tanmw.db2dict.entity.TableInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

import static top.tanmw.db2dict.entity.DbConstant.TABLE_INFO_RELATION;
import static top.tanmw.db2dict.entity.DbConstant.TABLE_RELATION;
import static top.tanmw.db2dict.word.WordConfig.*;

/**
 * @author TMW
 * @since 2022/2/10 15:05
 */
@Slf4j
public class WordUtil {
    private static XWPFDocument DOCUMENT = new XWPFDocument();

    public void writeTableToWord(List<TableInfo> tableList) throws Exception {
        createTableListNormal(tableList);
        for (TableInfo tableInfo : tableList) {
            if (TITLE_ADD_INDEX) {
                int index = tableList.indexOf(tableInfo) + 1;
                tableInfo.setTitle(index + ". " + tableInfo.getTitle());
            }
            createSimpleTableNormal(tableInfo);
        }
        saveDOCUMENT(EXPORT_FILE_PATH);
        log.info("文件写入成功.");
        log.info("成功将文件保存在 >>>" + EXPORT_FILE_PATH);
    }

    /**
     * 往word插入一张表格
     *
     * @param tableInfo
     * @throws Exception
     */
    private void createSimpleTableNormal(TableInfo tableInfo)
            throws Exception {
        // 添加一个文档
        addNewPage(BreakType.TEXT_WRAPPING);

        // 设置标题
        setTableTitle(tableInfo.getTitle());
        final List<List<String>> fieldList = tableInfo.getFieldList();
        // 创建表格
        XWPFTable table = createTable(fieldList.size(), TABLE_RELATION.size());
        // 设置表格中行列内容
        setRowText(table, tableInfo);
        // 往表格中插入第一列标题内容
        setFirstRowText(table);
    }

    /**
     * 往word插入一张表格
     *
     * @param tableInfoList
     * @throws Exception
     */
    private void createTableListNormal(List<TableInfo> tableInfoList)
            throws Exception {
        // 添加一个文档
        addNewPage(BreakType.TEXT_WRAPPING);

        // 设置标题
        setTableTitle("表清单");
        // final List<List<String>> fieldList = tableInfoList.getFieldList();
        // 创建表格
        XWPFTable table = createTable(tableInfoList.size(), TABLE_INFO_RELATION.size());
        // 设置表格中行列内容
        Integer count = 0;
        for (TableInfo tableInfo : tableInfoList) {
            setRowTableText(table, tableInfo, count);
            count++;
        }
        // 往表格中插入第一列标题内容
        setTableFirstRowText(table);
    }

    /**
     * 创建表格
     *
     * @param rowNum
     * @param celNum
     * @return
     */
    private XWPFTable createTable(int rowNum, int celNum) {
        XWPFTable table = DOCUMENT.createTable(rowNum, celNum);
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl
                .getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr
                .addNewTblW();
        // CTJc cTJc = tblPr.addNewJc();
        final CTJcTable cTJc = tblPr.addNewJc();
        // cTJc.setVal(STJc.Enum.forString("center"));
        cTJc.setVal(STJcTable.Enum.forString("center"));
        tblWidth.setW(new BigInteger(TABLE_WIDTH));
        tblWidth.setType(STTblWidth.DXA);
        return table;
    }

    /**
     * 设置表格标题
     *
     * @param tableTitle
     */
    private void setTableTitle(String tableTitle) {
        XWPFParagraph p2 = DOCUMENT.createParagraph();

        addCustomHeadingStyle(DOCUMENT, "标题 2", 2);

        p2.setAlignment(TITLE_ALIGNMENT);
        XWPFRun r2 = p2.createRun();
        p2.setStyle("标题 2");
        // r2.setTextPosition(5);
        r2.setText(tableTitle);
        if (TITLE_FONT_BOLD) {
            r2.setBold(TITLE_FONT_BOLD);
        }
        r2.setFontFamily(TITLE_FONT_FAMILY);
        r2.setFontSize(TITLE_FONT_SIZE);
        if (IS_RETURN_ROW) {
            r2.addCarriageReturn();// 是否换行
        }
    }

    /**
     * 设置表格第一行内容
     *
     * @param table
     */
    private void setFirstRowText(XWPFTable table) {
        XWPFTableRow firstRow = null;
        XWPFTableCell firstCell = null;
        firstRow = table.insertNewTableRow(0);
        firstRow.setHeight(FIRST_ROW_HEIGHT);
        // 表关系列
        for (String fieldValue : TABLE_RELATION.values()) {
            firstCell = firstRow.addNewTableCell();
            createVSpanCell(firstCell, fieldValue, FIRST_ROW_COLOR,
                    FIRST_ROW_CEL_WIDTH, STMerge.RESTART);
        }
    }

    /**
     * 设置表格第一行内容
     *
     * @param table
     */
    private void setTableFirstRowText(XWPFTable table) {
        XWPFTableRow firstRow = null;
        XWPFTableCell firstCell = null;
        firstRow = table.insertNewTableRow(0);
        firstRow.setHeight(FIRST_ROW_HEIGHT);
        // 表关系列
        for (String fieldValue : TABLE_INFO_RELATION.values()) {
            firstCell = firstRow.addNewTableCell();
            createVSpanCell(firstCell, fieldValue, FIRST_ROW_COLOR,
                    FIRST_ROW_CEL_WIDTH, STMerge.RESTART);
        }
    }

    /**
     * 设置每行的内容
     *
     * @param table
     * @param tableInfo
     */
    private void setRowText(XWPFTable table, TableInfo tableInfo) {
        XWPFTableRow firstRow = null;
        XWPFTableCell firstCell = null;

        List<List<String>> fieldList = tableInfo.getFieldList();
        List<String> fieldValues = null;
        for (int i = 0, fieldListSize = fieldList.size(); i < fieldListSize; i++) {
            firstRow = table.getRow(i);
            firstRow.setHeight(ROW_HEIGHT);
            fieldValues = fieldList.get(i);
            for (int j = 0, fieldValuesSize = fieldValues.size(); j < fieldValuesSize; j++) {
                firstCell = firstRow.getCell(j);
                setCellText(firstCell, fieldValues.get(j), ROW_COLOR, ROW_CEL_WIDTH);
            }
        }

    }

    /**
     * 设置每行的内容
     *
     * @param table
     * @param tableInfo
     */
    private void setRowTableText(XWPFTable table, TableInfo tableInfo, Integer index) {
        XWPFTableRow firstRow = null;
        XWPFTableCell firstCell = null;

        firstRow = table.getRow(index);
        if (firstRow == null) {
            firstRow = table.createRow();
        }
        firstRow.setHeight(ROW_HEIGHT);
        firstCell = firstRow.getCell(0);
        setCellText(firstCell, tableInfo.getTableName(), ROW_COLOR, ROW_CEL_WIDTH);

        firstRow = table.getRow(index);
        if (firstRow == null) {
            firstRow = table.createRow();
        }
        firstRow.setHeight(ROW_HEIGHT);

        firstCell = firstRow.getCell(1);
        setCellText(firstCell, tableInfo.getTableComment(), ROW_COLOR, ROW_CEL_WIDTH);
    }

    /**
     * 添加单个列的内容
     *
     * @param cell
     * @param value
     * @param bgcolor
     * @param width
     */
    private void setCellText(XWPFTableCell cell, String value, String bgcolor,
                             int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));

        if (IS_ROW_COLOR)// 设置颜色
            cell.setColor(bgcolor);

        /*
         * ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
         * cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
         * cell.setText(value);
         */

        XWPFParagraph p = getXWPFParagraph(ROW_ALIGNMENT, ROW_FONT_BOLD,
                ROW_FONT_FAMILY, ROW_FONT_SIZE, ROW_FONT_COLOR, value);
        cell.setParagraph(p);
    }

    /**
     * 往第一行插入一列
     *
     * @param cell
     * @param value
     * @param bgcolor
     * @param width
     * @param stMerge
     */
    private void createVSpanCell(XWPFTableCell cell, String value,
                                 String bgcolor, int width, STMerge.Enum stMerge) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        if (IS_FIRST_ROW_COLOR)// 设置颜色
            cell.setColor(bgcolor);
        /*
         * cellPr.addNewVMerge().setVal(stMerge);
         * cellPr.addNewVAlign().setVal(STVerticalJc.CENTER);
         * cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
         * cttc.getPList().get(0).addNewR().addNewT().setStringValue(value);
         */

        XWPFParagraph p = getXWPFParagraph(FIRST_ROW_ALIGNMENT,
                FIRST_ROW_FONT_BOLD, FIRST_ROW_FONT_FAMILY,
                FIRST_ROW_FONT_SIZE, FIRST_ROW_FONT_COLOR, value);
        cell.setParagraph(p);
    }

    private XWPFParagraph getXWPFParagraph(ParagraphAlignment alignment,
                                           boolean isBold, String fontFamily, int fontSize, String fontColor,
                                           String celValue) {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r2 = p.createRun();
        p.setAlignment(alignment);
        if (isBold)
            r2.setBold(isBold);
        r2.setFontFamily(fontFamily);
        r2.setFontSize(fontSize);
        r2.setText(celValue);
        r2.setColor(fontColor);
        return p;
    }

    /**
     * 添加新的一个文档
     *
     * @param breakType
     */
    private void addNewPage(BreakType breakType) {
        XWPFParagraph xp = DOCUMENT.createParagraph();
        xp.createRun().addBreak(breakType);
    }

    /**
     * 输出文件
     *
     * @param savePath
     * @throws Exception
     */
    public void saveDOCUMENT(String savePath) throws Exception {
        final File file = new File(savePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(savePath);
        DOCUMENT.write(fos);
        fos.close();
    }

    /**
     * 增加自定义标题样式。这里用的是stackoverflow的源码
     *
     * @param docxDocument 目标文档
     * @param strStyleId   样式名称
     * @param headingLevel 样式级别
     */
    private static void addCustomHeadingStyle(XWPFDocument docxDocument,
                                              String strStyleId, int headingLevel) {

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);

        // style shows up in the formats bar
        ctStyle.setQFormat(onoffnull);

        // style defines a heading of the given level
        // CTPPr ppr = CTPPr.Factory.newInstance();
        // ppr.setOutlineLvl(indentNumber);
        final CTPPrGeneral ppr = CTPPrGeneral.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        // is a null op if already defined
        XWPFStyles styles = docxDocument.createStyles();

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);
    }
}
