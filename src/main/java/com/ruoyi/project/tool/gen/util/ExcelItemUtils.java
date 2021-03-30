package com.ruoyi.project.tool.gen.util;

import com.ruoyi.project.project.domain.EmpSalaryinfo;
import com.ruoyi.project.project.domain.Monthname;
import com.ruoyi.project.project.domain.Percost;
import com.ruoyi.project.project.domain.Perincome;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

public class ExcelItemUtils {
    /**
     * 导出项目人员支出
     * @param list
     * @return
     */
    public  Workbook PercostDataExcel(List<Percost> list){
        // 1.创建工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();

        // 一级标题
        CellRangeAddress callRangeAddress01 = new CellRangeAddress(0, 0, 0, 40);// 起始行,结束行,起始列,结束列
        // 二级标题
        CellRangeAddress callRangeAddress11 = new CellRangeAddress(1, 2, 0, 0);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress12 = new CellRangeAddress(1, 1, 1, 3);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress13 = new CellRangeAddress(1, 1, 4, 6);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress14 = new CellRangeAddress(1, 1, 7, 9);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress15 = new CellRangeAddress(1, 1, 10, 12);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress16 = new CellRangeAddress(1, 1, 13, 15);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress17 = new CellRangeAddress(1, 1, 16, 18);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress18 = new CellRangeAddress(1, 1, 19, 21);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress19 = new CellRangeAddress(1, 1, 22, 24);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress110 = new CellRangeAddress(1, 1, 25, 27);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress111 = new CellRangeAddress(1, 1, 28, 30);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress112 = new CellRangeAddress(1, 1, 31, 33);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress113 = new CellRangeAddress(1, 1, 34, 36);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress114 = new CellRangeAddress(1, 2, 37, 37);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress115 = new CellRangeAddress(1, 2, 38, 38);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress116 = new CellRangeAddress(1, 2, 39, 39);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress117 = new CellRangeAddress(1, 2, 40, 40);// 起始行,结束行,起始列,结束列
        // 三级标题
        CellRangeAddress callRangeAddress21 = new CellRangeAddress(2, 2, 1, 1);
        CellRangeAddress callRangeAddress22 = new CellRangeAddress(2, 2, 2, 2);
        CellRangeAddress callRangeAddress23 = new CellRangeAddress(2, 2, 3, 3);
        CellRangeAddress callRangeAddress24 = new CellRangeAddress(2, 2, 4, 4);
        CellRangeAddress callRangeAddress25 = new CellRangeAddress(2, 2, 5, 5);
        CellRangeAddress callRangeAddress26 = new CellRangeAddress(2, 2, 6, 6);
        CellRangeAddress callRangeAddress27 = new CellRangeAddress(2, 2, 7, 7);
        CellRangeAddress callRangeAddress28 = new CellRangeAddress(2, 2, 8, 8);
        CellRangeAddress callRangeAddress29 = new CellRangeAddress(2, 2, 9, 9);
        CellRangeAddress callRangeAddress210 = new CellRangeAddress(2, 2, 10, 10);
        CellRangeAddress callRangeAddress211 = new CellRangeAddress(2, 2, 11, 11);
        CellRangeAddress callRangeAddress212 = new CellRangeAddress(2, 2, 12, 12);
        CellRangeAddress callRangeAddress213 = new CellRangeAddress(2, 2, 13, 13);
        CellRangeAddress callRangeAddress214 = new CellRangeAddress(2, 2, 14, 14);
        CellRangeAddress callRangeAddress215 = new CellRangeAddress(2, 2, 15, 15);
        CellRangeAddress callRangeAddress216 = new CellRangeAddress(2, 2, 16, 16);
        CellRangeAddress callRangeAddress217 = new CellRangeAddress(2, 2, 17, 17);
        CellRangeAddress callRangeAddress218 = new CellRangeAddress(2, 2, 18, 18);
        CellRangeAddress callRangeAddress219 = new CellRangeAddress(2, 2, 19, 19);
        CellRangeAddress callRangeAddress220 = new CellRangeAddress(2, 2, 20, 20);
        CellRangeAddress callRangeAddress221 = new CellRangeAddress(2, 2, 21, 21);
        CellRangeAddress callRangeAddress222 = new CellRangeAddress(2, 2, 22, 22);
        CellRangeAddress callRangeAddress223 = new CellRangeAddress(2, 2, 23, 23);
        CellRangeAddress callRangeAddress224 = new CellRangeAddress(2, 2, 24, 24);
        CellRangeAddress callRangeAddress225 = new CellRangeAddress(2, 2, 25, 25);
        CellRangeAddress callRangeAddress226 = new CellRangeAddress(2, 2, 26, 26);
        CellRangeAddress callRangeAddress227 = new CellRangeAddress(2, 2, 27, 27);
        CellRangeAddress callRangeAddress228 = new CellRangeAddress(2, 2, 28, 28);
        CellRangeAddress callRangeAddress229 = new CellRangeAddress(2, 2, 29, 29);
        CellRangeAddress callRangeAddress230 = new CellRangeAddress(2, 2, 30, 30);
        CellRangeAddress callRangeAddress231 = new CellRangeAddress(2, 2, 31, 31);
        CellRangeAddress callRangeAddress232 = new CellRangeAddress(2, 2, 32, 32);
        CellRangeAddress callRangeAddress233 = new CellRangeAddress(2, 2, 33, 33);
        CellRangeAddress callRangeAddress234 = new CellRangeAddress(2, 2, 34, 34);
        CellRangeAddress callRangeAddress235 = new CellRangeAddress(2, 2, 35, 35);
        CellRangeAddress callRangeAddress236 = new CellRangeAddress(2, 2, 36, 36);

        // 标题样式
        CellStyle telStyle = workbook.createCellStyle();
        telStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        telStyle.setBorderRight(BorderStyle.THIN);
//        telStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        telStyle.setBorderLeft(BorderStyle.THIN);
//        telStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        telStyle.setBorderTop(BorderStyle.THIN);
//        telStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        telStyle.setBorderBottom(BorderStyle.THIN);
//        telStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        telStyle.setAlignment(HorizontalAlignment.CENTER);
        telStyle.setWrapText(true);

        telStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        telStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 表格头样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setWrapText(true);

        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        //内容样式
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        //设置字体格式
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)14);
        font.setBold(true);
        //将字体格式设置到HSSFCellStyle上
        telStyle.setFont(font);
        //设置表头字体
        Font font1 = workbook.createFont();
        font1.setFontName("宋体");
        font1.setFontHeightInPoints((short)12);
        font1.setBold(true);
        cellStyle.setFont(font1);
        //设置内容字体
        Font font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)9);
        font2.setBold(true);
        cellStyle1.setFont(font2);
        cellStyle1.setBorderRight(BorderStyle.THIN);
//        telStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle1.setBorderLeft(BorderStyle.THIN);
//        telStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle1.setBorderTop(BorderStyle.THIN);
//        telStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle1.setBorderBottom(BorderStyle.THIN);

        // 2.创建工作表
        Sheet sheet = workbook.createSheet("项目人员年度成本报表");

        // 2.1加载合并单元格对象
        sheet.addMergedRegion(callRangeAddress01);
        sheet.addMergedRegion(callRangeAddress11);
        sheet.addMergedRegion(callRangeAddress12);
        sheet.addMergedRegion(callRangeAddress13);
        sheet.addMergedRegion(callRangeAddress14);
        sheet.addMergedRegion(callRangeAddress15);
        sheet.addMergedRegion(callRangeAddress16);
        sheet.addMergedRegion(callRangeAddress17);
        sheet.addMergedRegion(callRangeAddress18);
        sheet.addMergedRegion(callRangeAddress19);
        sheet.addMergedRegion(callRangeAddress110);
        sheet.addMergedRegion(callRangeAddress111);
        sheet.addMergedRegion(callRangeAddress112);
        sheet.addMergedRegion(callRangeAddress113);

        sheet.addMergedRegion(callRangeAddress114);
        sheet.addMergedRegion(callRangeAddress115);
        sheet.addMergedRegion(callRangeAddress116);
        sheet.addMergedRegion(callRangeAddress117);

        // 设置默认列宽
        sheet.setDefaultColumnWidth(15);

        // 一级标题
        Row row1 = sheet.createRow(0);
        Cell cell1 = row1.createCell(0);
        cell1.setCellStyle(telStyle);
        // 加载单元格样式
        String year = GetDateUtils.getSysYear();
        cell1.setCellValue(year+"年项目人员成本报表");
        for (int j = 1; j <= 39; j++) {
            cell1 = row1.createCell(j);
            cell1.setCellStyle(telStyle); //style为带边框的样式 上面有定义
            }
        //二级标题
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        Cell cell22 = row2.createCell(1);
        Cell cell23 = row2.createCell(4);
        Cell cell24 = row2.createCell(7);
        Cell cell25 = row2.createCell(10);
        Cell cell26 = row2.createCell(13);
        Cell cell27 = row2.createCell(16);
        Cell cell28 = row2.createCell(19);
        Cell cell29 = row2.createCell(22);
        Cell cell210 = row2.createCell(25);
        Cell cell211 = row2.createCell(28);
        Cell cell212 = row2.createCell(31);
        Cell cell213 = row2.createCell(34);
        Cell cell214 = row2.createCell(37);
        Cell cell215 = row2.createCell(38);
        Cell cell216 = row2.createCell(39);
        Cell cell217 = row2.createCell(40);


        // 加载单元格样式
        cell21.setCellStyle(cellStyle);
        cell21.setCellValue("姓名");
        cell22.setCellStyle(cellStyle);
        cell22.setCellValue("一月");
        cell23.setCellStyle(cellStyle);
        cell23.setCellValue("二月");
        cell24.setCellStyle(cellStyle);
        cell24.setCellValue("三月");
        cell25.setCellStyle(cellStyle);
        cell25.setCellValue("四月");
        cell26.setCellStyle(cellStyle);
        cell26.setCellValue("五月");
        cell27.setCellStyle(cellStyle);
        cell27.setCellValue("六月");
        cell28.setCellStyle(cellStyle);
        cell28.setCellValue("七月");
        cell29.setCellStyle(cellStyle);
        cell29.setCellValue("八月");
        cell210.setCellStyle(cellStyle);
        cell210.setCellValue("九月");
        cell211.setCellStyle(cellStyle);
        cell211.setCellValue("十月");
        cell212.setCellStyle(cellStyle);
        cell212.setCellValue("十一月");
        cell213.setCellStyle(cellStyle);
        cell213.setCellValue("十二月");
        cell214.setCellStyle(cellStyle);
        cell214.setCellValue("开始日期");
        cell215.setCellStyle(cellStyle);
        cell215.setCellValue("结束日期");
        cell216.setCellStyle(cellStyle);
        cell216.setCellValue("毛利费用率");
        cell217.setCellStyle(cellStyle);
        cell217.setCellValue("成本合计(/元)");

        //三级标题
        Row row3 = sheet.createRow(2);
        Cell cell30 =  row3.createCell(0);
        Cell cell31 =  row3.createCell(1);
        Cell cell32 = row3.createCell(2);
        Cell cell33 = row3.createCell(3);
        Cell cell34 = row3.createCell(4);
        Cell cell35 = row3.createCell(5);
        Cell cell36 = row3.createCell(6);
        Cell cell37 = row3.createCell(7);
        Cell cell38 = row3.createCell(8);
        Cell cell39 = row3.createCell(9);
        Cell cell310 = row3.createCell(10);
        Cell cell311 = row3.createCell(11);
        Cell cell312 = row3.createCell(12);
        Cell cell313 = row3.createCell(13);
        Cell cell314 = row3.createCell(14);
        Cell cell315 = row3.createCell(15);
        Cell cell316 = row3.createCell(16);
        Cell cell317 = row3.createCell(17);
        Cell cell318 = row3.createCell(18);
        Cell cell319 = row3.createCell(19);
        Cell cell320 = row3.createCell(20);
        Cell cell321 =  row3.createCell(21);
        Cell cell322 = row3.createCell(22);
        Cell cell323 = row3.createCell(23);
        Cell cell324 = row3.createCell(24);
        Cell cell325 = row3.createCell(25);
        Cell cell326 = row3.createCell(26);
        Cell cell327 = row3.createCell(27);
        Cell cell328 = row3.createCell(28);
        Cell cell329 = row3.createCell(29);
        Cell cell330 = row3.createCell(30);
        Cell cell331 =  row3.createCell(31);
        Cell cell332 = row3.createCell(32);
        Cell cell333 = row3.createCell(33);
        Cell cell334 = row3.createCell(34);
        Cell cell335 = row3.createCell(35);
        Cell cell336 = row3.createCell(36);
        Cell cell337 = row3.createCell(37);
        Cell cell338 = row3.createCell(38);
        Cell cell339 = row3.createCell(39);
        Cell cell340 = row3.createCell(40);
        //加载单元格样式
        cell30.setCellStyle(cellStyle1);
        cell30.setCellValue("");
        cell31.setCellStyle(cellStyle);
        cell31.setCellValue("税前工资(/元)");
        cell32.setCellStyle(cellStyle);
        cell32.setCellValue("五险一金小计(/元)");
        cell33.setCellStyle(cellStyle);
        cell33.setCellValue("每月成本合计(/元)");
        cell34.setCellStyle(cellStyle);
        cell34.setCellValue("税前工资(/元)");
        cell35.setCellStyle(cellStyle);
        cell35.setCellValue("五险一金小计(/元)");
        cell36.setCellStyle(cellStyle);
        cell36.setCellValue("每月成本合计(/元)");
        cell37.setCellStyle(cellStyle);
        cell37.setCellValue("税前工资(/元)");
        cell38.setCellStyle(cellStyle);
        cell38.setCellValue("五险一金小计(/元)");
        cell39.setCellStyle(cellStyle);
        cell39.setCellValue("每月成本合计(/元)");
        cell310.setCellStyle(cellStyle);
        cell310.setCellValue("税前工资(/元)");
        cell311.setCellStyle(cellStyle);
        cell311.setCellValue("五险一金小计(/元)");
        cell312.setCellStyle(cellStyle);
        cell312.setCellValue("每月成本合计(/元)");
        cell313.setCellStyle(cellStyle);
        cell313.setCellValue("税前工资(/元)");
        cell314.setCellStyle(cellStyle);
        cell314.setCellValue("五险一金小计(/元)");
        cell315.setCellStyle(cellStyle);
        cell315.setCellValue("每月成本合计(/元)");
        cell316.setCellStyle(cellStyle);
        cell316.setCellValue("税前工资(/元)");
        cell317.setCellStyle(cellStyle);
        cell317.setCellValue("五险一金小计(/元)");
        cell318.setCellStyle(cellStyle);
        cell318.setCellValue("每月成本合计(/元)");
        cell319.setCellStyle(cellStyle);
        cell319.setCellValue("税前工资(/元)");
        cell320.setCellStyle(cellStyle);
        cell320.setCellValue("五险一金小计(/元)");
        cell321.setCellStyle(cellStyle);
        cell321.setCellValue("每月成本合计(/元)");
        cell322.setCellStyle(cellStyle);
        cell322.setCellValue("税前工资(/元)");
        cell323.setCellStyle(cellStyle);
        cell323.setCellValue("五险一金小计(/元)");
        cell324.setCellStyle(cellStyle);
        cell324.setCellValue("每月成本合计(/元)");
        cell325.setCellStyle(cellStyle);
        cell325.setCellValue("税前工资(/元)");
        cell326.setCellStyle(cellStyle);
        cell326.setCellValue("五险一金小计(/元)");
        cell327.setCellStyle(cellStyle);
        cell327.setCellValue("每月成本合计(/元)");
        cell328.setCellStyle(cellStyle);
        cell328.setCellValue("税前工资(/元)");
        cell329.setCellStyle(cellStyle);
        cell329.setCellValue("五险一金小计(/元)");
        cell330.setCellStyle(cellStyle);
        cell330.setCellValue("每月成本合计(/元)");
        cell331.setCellStyle(cellStyle);
        cell331.setCellValue("税前工资(/元)");
        cell332.setCellStyle(cellStyle);
        cell332.setCellValue("五险一金小计(/元)");
        cell333.setCellStyle(cellStyle);
        cell333.setCellValue("每月成本合计(/元)");
        cell334.setCellStyle(cellStyle);
        cell334.setCellValue("税前工资(/元)");
        cell335.setCellStyle(cellStyle);
        cell335.setCellValue("五险一金小计(/元)");
        cell336.setCellStyle(cellStyle);
        cell336.setCellValue("每月成本合计(/元)");
        cell337.setCellStyle(cellStyle);
        cell337.setCellValue("");
        cell338.setCellStyle(cellStyle);
        cell338.setCellValue("");
        cell339.setCellStyle(cellStyle);
        cell339.setCellValue("");
        cell340.setCellStyle(cellStyle);
        cell340.setCellValue("");

        // 3.2创建列标题;并且设置列标题
     /*   Row rowtel = sheet.createRow(3);
        String[] titles = { "", "笔数", "金额","笔数", "金额","笔数", "金额","笔数", "金额","笔数", "金额","笔数", "金额","笔数", "金额","笔数", "金额","笔数", "金额","笔数", "金额"};// ""为占位字符串
        for (int i = 0; i < titles.length; i++) {
            Cell cell2 = rowtel.createCell(i);
            // 加载单元格样式
            cell2.setCellStyle(telStyle);
            cell2.setCellValue(titles[i]);
        }*/

        // 4.操作单元格;将用户列表写入excel
        if (list != null) {
            Percost percost = null;
            Monthname monthname = new Monthname();
            EmpSalaryinfo empSalaryinfo1 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo2 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo3 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo4 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo5 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo6 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo7 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo8 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo9 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo10 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo11 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo12 = new EmpSalaryinfo();
            for (int j = 0; j < list.size(); j++) {
                percost = list.get(j);
                // 创建数据行,前面有两行,头标题行和列标题行
                Row rowContent1 = sheet.createRow(j + 3);
                Cell cellContent1 = rowContent1.createCell(0);
                cellContent1.setCellStyle(cellStyle1);
                cellContent1.setCellValue((String) percost.getEmpName());

                monthname = percost.getMonthname();
                empSalaryinfo1 = monthname.getJanuary();
                empSalaryinfo2 = monthname.getFebruary();
                empSalaryinfo3 = monthname.getMarch();
                empSalaryinfo4 = monthname.getApril();
                empSalaryinfo5 = monthname.getMay();
                empSalaryinfo6 = monthname.getJune();
                empSalaryinfo7 = monthname.getJuly();
                empSalaryinfo8 = monthname.getAugust();
                empSalaryinfo9 = monthname.getSeptember();
                empSalaryinfo10 = monthname.getOctober();
                empSalaryinfo11 = monthname.getNovember();
                empSalaryinfo12 = monthname.getDecember();

                //==============一月==============================
                Cell cellContent2 = rowContent1.createCell(1);
                cellContent2.setCellStyle(cellStyle1);
                cellContent2.setCellValue(empSalaryinfo1.getEmpSalary());

                Cell cellContent3 = rowContent1.createCell(2);
                cellContent3.setCellStyle(cellStyle1);
                cellContent3.setCellValue(empSalaryinfo1.getEmpInsuranceFund());

                Cell cellContent4 = rowContent1.createCell(3);
                cellContent4.setCellStyle(cellStyle1);
                cellContent4.setCellValue(empSalaryinfo1.getEmpMonthSalary());
                //==============二月==============================
                Cell cellContent5 = rowContent1.createCell(4);
                cellContent5.setCellStyle(cellStyle1);
                cellContent5.setCellValue(empSalaryinfo2.getEmpSalary());

                Cell cellContent6 = rowContent1.createCell(5);
                cellContent6.setCellStyle(cellStyle1);
                cellContent6.setCellValue(empSalaryinfo2.getEmpInsuranceFund());

                Cell cellContent7 = rowContent1.createCell(6);
                cellContent7.setCellStyle(cellStyle1);
                cellContent7.setCellValue(empSalaryinfo2.getEmpMonthSalary());
                //==============三月==============================
                Cell cellContent8 = rowContent1.createCell(7);
                cellContent8.setCellStyle(cellStyle1);
                cellContent8.setCellValue(empSalaryinfo3.getEmpSalary());

                Cell cellContent9 = rowContent1.createCell(8);
                cellContent9.setCellStyle(cellStyle1);
                cellContent9.setCellValue(empSalaryinfo3.getEmpInsuranceFund());

                Cell cellContent10 = rowContent1.createCell(9);
                cellContent10.setCellStyle(cellStyle1);
                cellContent10.setCellValue(empSalaryinfo3.getEmpMonthSalary());
                //==============四月==============================
                Cell cellContent11 = rowContent1.createCell(10);
                cellContent11.setCellStyle(cellStyle1);
                cellContent11.setCellValue(empSalaryinfo4.getEmpSalary());

                Cell cellContent12 = rowContent1.createCell(11);
                cellContent12.setCellStyle(cellStyle1);
                cellContent12.setCellValue(empSalaryinfo4.getEmpInsuranceFund());

                Cell cellContent13 = rowContent1.createCell(12);
                cellContent13.setCellStyle(cellStyle1);
                cellContent13.setCellValue(empSalaryinfo4.getEmpMonthSalary());
                //==============五月==============================
                Cell cellContent14 = rowContent1.createCell(13);
                cellContent14.setCellStyle(cellStyle1);
                cellContent14.setCellValue(empSalaryinfo5.getEmpSalary());

                Cell cellContent15 = rowContent1.createCell(14);
                cellContent15.setCellStyle(cellStyle1);
                cellContent15.setCellValue(empSalaryinfo5.getEmpInsuranceFund());

                Cell cellContent16 = rowContent1.createCell(15);
                cellContent16.setCellStyle(cellStyle1);
                cellContent16.setCellValue(empSalaryinfo5.getEmpMonthSalary());
                //==============六月==============================
                Cell cellContent17 = rowContent1.createCell(16);
                cellContent17.setCellStyle(cellStyle1);
                cellContent17.setCellValue(empSalaryinfo6.getEmpSalary());

                Cell cellContent18 = rowContent1.createCell(17);
                cellContent18.setCellStyle(cellStyle1);
                cellContent18.setCellValue(empSalaryinfo6.getEmpInsuranceFund());

                Cell cellContent19 = rowContent1.createCell(18);
                cellContent19.setCellStyle(cellStyle1);
                cellContent19.setCellValue(empSalaryinfo6.getEmpMonthSalary());
                //==============七月==============================
                Cell cellContent20 = rowContent1.createCell(19);
                cellContent20.setCellStyle(cellStyle1);
                cellContent20.setCellValue(empSalaryinfo7.getEmpSalary());

                Cell cellContent21 = rowContent1.createCell(20);
                cellContent21.setCellStyle(cellStyle1);
                cellContent21.setCellValue(empSalaryinfo7.getEmpInsuranceFund());

                Cell cellContent22 = rowContent1.createCell(21);
                cellContent22.setCellStyle(cellStyle1);
                cellContent22.setCellValue(empSalaryinfo7.getEmpMonthSalary());
                //==============八月==============================
                Cell cellContent23 = rowContent1.createCell(22);
                cellContent23.setCellStyle(cellStyle1);
                cellContent23.setCellValue(empSalaryinfo8.getEmpSalary());

                Cell cellContent24 = rowContent1.createCell(23);
                cellContent24.setCellStyle(cellStyle1);
                cellContent24.setCellValue(empSalaryinfo8.getEmpInsuranceFund());

                Cell cellContent25 = rowContent1.createCell(24);
                cellContent25.setCellStyle(cellStyle1);
                cellContent25.setCellValue(empSalaryinfo8.getEmpMonthSalary());
                //==============九月==============================
                Cell cellContent26 = rowContent1.createCell(25);
                cellContent26.setCellStyle(cellStyle1);
                cellContent26.setCellValue(empSalaryinfo9.getEmpSalary());

                Cell cellContent27 = rowContent1.createCell(26);
                cellContent27.setCellStyle(cellStyle1);
                cellContent27.setCellValue(empSalaryinfo9.getEmpInsuranceFund());

                Cell cellContent28 = rowContent1.createCell(27);
                cellContent28.setCellStyle(cellStyle1);
                cellContent28.setCellValue(empSalaryinfo9.getEmpMonthSalary());
                //==============十月==============================
                Cell cellContent29 = rowContent1.createCell(28);
                cellContent29.setCellStyle(cellStyle1);
                cellContent29.setCellValue(empSalaryinfo10.getEmpSalary());

                Cell cellContent30 = rowContent1.createCell(29);
                cellContent30.setCellStyle(cellStyle1);
                cellContent30.setCellValue(empSalaryinfo10.getEmpInsuranceFund());

                Cell cellContent31 = rowContent1.createCell(30);
                cellContent31.setCellStyle(cellStyle1);
                cellContent31.setCellValue(empSalaryinfo10.getEmpMonthSalary());
                //==============十一月==============================
                Cell cellContent32 = rowContent1.createCell(31);
                cellContent32.setCellStyle(cellStyle1);
                cellContent32.setCellValue(empSalaryinfo11.getEmpSalary());

                Cell cellContent33 = rowContent1.createCell(32);
                cellContent33.setCellStyle(cellStyle1);
                cellContent33.setCellValue(empSalaryinfo11.getEmpInsuranceFund());

                Cell cellContent34 = rowContent1.createCell(33);
                cellContent34.setCellStyle(cellStyle1);
                cellContent34.setCellValue(empSalaryinfo11.getEmpMonthSalary());
                //==============十二月==============================
                Cell cellContent35 = rowContent1.createCell(34);
                cellContent35.setCellStyle(cellStyle1);
                cellContent35.setCellValue(empSalaryinfo12.getEmpSalary());

                Cell cellContent36 = rowContent1.createCell(35);
                cellContent36.setCellStyle(cellStyle1);
                cellContent36.setCellValue(empSalaryinfo12.getEmpInsuranceFund());

                Cell cellContent37 = rowContent1.createCell(36);
                cellContent37.setCellStyle(cellStyle1);
                cellContent37.setCellValue(empSalaryinfo12.getEmpMonthSalary());

                Cell cellContent38 = rowContent1.createCell(37);
                cellContent38.setCellStyle(cellStyle1);
                cellContent38.setCellValue(percost.getEmpStartDate());

                Cell cellContent39 = rowContent1.createCell(38);
                cellContent39.setCellStyle(cellStyle1);
                cellContent39.setCellValue(percost.getEmpEndDate());

                Cell cellContent40 = rowContent1.createCell(39);
                cellContent40.setCellStyle(cellStyle1);
                cellContent40.setCellValue(percost.getEmpProfitrate());

                Cell cellContent41 = rowContent1.createCell(40);
                cellContent41.setCellStyle(cellStyle1);
                cellContent41.setCellValue(percost.getEmpTotalSalary());

            }
        }
        return workbook;
    }

    /**
     * 导出外包人员收入
     * @param list
     * @return
     */
    public  Workbook PerincomeDataExcel(List<Perincome> list){

        // 1.创建工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();

        // 一级标题
        CellRangeAddress callRangeAddress01 = new CellRangeAddress(0, 0, 0, 28);// 起始行,结束行,起始列,结束列
        // 二级标题
        CellRangeAddress callRangeAddress11 = new CellRangeAddress(1, 2, 0, 0);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress12 = new CellRangeAddress(1, 1, 1, 2);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress13 = new CellRangeAddress(1, 1, 3, 4);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress14 = new CellRangeAddress(1, 1, 5, 6);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress15 = new CellRangeAddress(1, 1, 7, 8);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress16 = new CellRangeAddress(1, 1, 9, 10);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress17 = new CellRangeAddress(1, 1, 11, 12);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress18 = new CellRangeAddress(1, 1, 13, 14);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress19 = new CellRangeAddress(1, 1, 15, 16);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress110 = new CellRangeAddress(1, 1, 17, 18);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress111 = new CellRangeAddress(1, 1, 19, 20);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress112 = new CellRangeAddress(1, 1, 21, 22);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress113 = new CellRangeAddress(1, 1, 23, 24);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress114 = new CellRangeAddress(1, 2, 25, 25);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress115 = new CellRangeAddress(1, 2, 26, 26);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress116 = new CellRangeAddress(1, 2, 27, 27);// 起始行,结束行,起始列,结束列
        CellRangeAddress callRangeAddress117 = new CellRangeAddress(1, 2, 28, 28);// 起始行,结束行,起始列,结束列
        // 三级标题
        CellRangeAddress callRangeAddress21 = new CellRangeAddress(2, 2, 1, 1);
        CellRangeAddress callRangeAddress22 = new CellRangeAddress(2, 2, 2, 2);
        CellRangeAddress callRangeAddress23 = new CellRangeAddress(2, 2, 3, 3);
        CellRangeAddress callRangeAddress24 = new CellRangeAddress(2, 2, 4, 4);
        CellRangeAddress callRangeAddress25 = new CellRangeAddress(2, 2, 5, 5);
        CellRangeAddress callRangeAddress26 = new CellRangeAddress(2, 2, 6, 6);
        CellRangeAddress callRangeAddress27 = new CellRangeAddress(2, 2, 7, 7);
        CellRangeAddress callRangeAddress28 = new CellRangeAddress(2, 2, 8, 8);
        CellRangeAddress callRangeAddress29 = new CellRangeAddress(2, 2, 9, 9);
        CellRangeAddress callRangeAddress210 = new CellRangeAddress(2, 2, 10, 10);
        CellRangeAddress callRangeAddress211 = new CellRangeAddress(2, 2, 11, 11);
        CellRangeAddress callRangeAddress212 = new CellRangeAddress(2, 2, 12, 12);
        CellRangeAddress callRangeAddress213 = new CellRangeAddress(2, 2, 13, 13);
        CellRangeAddress callRangeAddress214 = new CellRangeAddress(2, 2, 14, 14);
        CellRangeAddress callRangeAddress215 = new CellRangeAddress(2, 2, 15, 15);
        CellRangeAddress callRangeAddress216 = new CellRangeAddress(2, 2, 16, 16);
        CellRangeAddress callRangeAddress217 = new CellRangeAddress(2, 2, 17, 17);
        CellRangeAddress callRangeAddress218 = new CellRangeAddress(2, 2, 18, 18);
        CellRangeAddress callRangeAddress219 = new CellRangeAddress(2, 2, 19, 19);
        CellRangeAddress callRangeAddress220 = new CellRangeAddress(2, 2, 20, 20);
        CellRangeAddress callRangeAddress221 = new CellRangeAddress(2, 2, 21, 21);
        CellRangeAddress callRangeAddress222 = new CellRangeAddress(2, 2, 22, 22);
        CellRangeAddress callRangeAddress223 = new CellRangeAddress(2, 2, 23, 23);
        CellRangeAddress callRangeAddress224 = new CellRangeAddress(2, 2, 24, 24);

        // 标题样式
        CellStyle telStyle = workbook.createCellStyle();
        telStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        telStyle.setBorderRight(BorderStyle.THIN);
//        telStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        telStyle.setBorderLeft(BorderStyle.THIN);
//        telStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        telStyle.setBorderTop(BorderStyle.THIN);
//        telStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        telStyle.setBorderBottom(BorderStyle.THIN);
//        telStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        telStyle.setAlignment(HorizontalAlignment.CENTER);
        telStyle.setWrapText(true);

        telStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        telStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 表格头样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setWrapText(true);

        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        //内容样式
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        //设置字体格式
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)14);
        font.setBold(true);
        //将字体格式设置到HSSFCellStyle上
        telStyle.setFont(font);
        //设置表头字体
        Font font1 = workbook.createFont();
        font1.setFontName("宋体");
        font1.setFontHeightInPoints((short)12);
        font1.setBold(true);
        cellStyle.setFont(font1);
        //设置内容字体
        Font font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)9);
        font2.setBold(true);
        cellStyle1.setFont(font2);
        cellStyle1.setBorderRight(BorderStyle.THIN);
//        telStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle1.setBorderLeft(BorderStyle.THIN);
//        telStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle1.setBorderTop(BorderStyle.THIN);
//        telStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        cellStyle1.setBorderBottom(BorderStyle.THIN);

        // 2.创建工作表
        Sheet sheet = workbook.createSheet("外包人员年度收入报表");

        // 2.1加载合并单元格对象
        sheet.addMergedRegion(callRangeAddress01);
        sheet.addMergedRegion(callRangeAddress11);
        sheet.addMergedRegion(callRangeAddress12);
        sheet.addMergedRegion(callRangeAddress13);
        sheet.addMergedRegion(callRangeAddress14);
        sheet.addMergedRegion(callRangeAddress15);
        sheet.addMergedRegion(callRangeAddress16);
        sheet.addMergedRegion(callRangeAddress17);
        sheet.addMergedRegion(callRangeAddress18);
        sheet.addMergedRegion(callRangeAddress19);
        sheet.addMergedRegion(callRangeAddress110);
        sheet.addMergedRegion(callRangeAddress111);
        sheet.addMergedRegion(callRangeAddress112);
        sheet.addMergedRegion(callRangeAddress113);

        sheet.addMergedRegion(callRangeAddress114);
        sheet.addMergedRegion(callRangeAddress115);
        sheet.addMergedRegion(callRangeAddress116);
        sheet.addMergedRegion(callRangeAddress117);

        // 设置默认列宽
        sheet.setDefaultColumnWidth(15);

        // 一级标题
        Row row1 = sheet.createRow(0);
        Cell cell1 = row1.createCell(0);
        cell1.setCellStyle(telStyle);
        // 加载单元格样式
        String year = GetDateUtils.getSysYear();
        cell1.setCellValue(year+"年项目人员成本报表");
        for (int j = 1; j <= 27; j++) {
            cell1 = row1.createCell(j);
            cell1.setCellStyle(telStyle); //style为带边框的样式 上面有定义
        }
        //二级标题
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        Cell cell22 = row2.createCell(1);
        Cell cell23 = row2.createCell(3);
        Cell cell24 = row2.createCell(5);
        Cell cell25 = row2.createCell(7);
        Cell cell26 = row2.createCell(9);
        Cell cell27 = row2.createCell(11);
        Cell cell28 = row2.createCell(13);
        Cell cell29 = row2.createCell(15);
        Cell cell210 = row2.createCell(17);
        Cell cell211 = row2.createCell(19);
        Cell cell212 = row2.createCell(21);
        Cell cell213 = row2.createCell(23);
        Cell cell214 = row2.createCell(25);
        Cell cell215 = row2.createCell(26);
        Cell cell216 = row2.createCell(27);
        Cell cell217 = row2.createCell(28);


        // 加载单元格样式
        cell21.setCellStyle(cellStyle);
        cell21.setCellValue("姓名");
        cell22.setCellStyle(cellStyle);
        cell22.setCellValue("一月");
        cell23.setCellStyle(cellStyle);
        cell23.setCellValue("二月");
        cell24.setCellStyle(cellStyle);
        cell24.setCellValue("三月");
        cell25.setCellStyle(cellStyle);
        cell25.setCellValue("四月");
        cell26.setCellStyle(cellStyle);
        cell26.setCellValue("五月");
        cell27.setCellStyle(cellStyle);
        cell27.setCellValue("六月");
        cell28.setCellStyle(cellStyle);
        cell28.setCellValue("七月");
        cell29.setCellStyle(cellStyle);
        cell29.setCellValue("八月");
        cell210.setCellStyle(cellStyle);
        cell210.setCellValue("九月");
        cell211.setCellStyle(cellStyle);
        cell211.setCellValue("十月");
        cell212.setCellStyle(cellStyle);
        cell212.setCellValue("十一月");
        cell213.setCellStyle(cellStyle);
        cell213.setCellValue("十二月");
        cell214.setCellStyle(cellStyle);
        cell214.setCellValue("开始日期");
        cell215.setCellStyle(cellStyle);
        cell215.setCellValue("结束日期");
        cell216.setCellStyle(cellStyle);
        cell216.setCellValue("毛利费用率");
        cell217.setCellStyle(cellStyle);
        cell217.setCellValue("合计(/元)");

        //三级标题
        Row row3 = sheet.createRow(2);
        Cell cell30 =  row3.createCell(0);
        Cell cell31 =  row3.createCell(1);
        Cell cell32 = row3.createCell(2);
        Cell cell33 = row3.createCell(3);
        Cell cell34 = row3.createCell(4);
        Cell cell35 = row3.createCell(5);
        Cell cell36 = row3.createCell(6);
        Cell cell37 = row3.createCell(7);
        Cell cell38 = row3.createCell(8);
        Cell cell39 = row3.createCell(9);
        Cell cell310 = row3.createCell(10);
        Cell cell311 = row3.createCell(11);
        Cell cell312 = row3.createCell(12);
        Cell cell313 = row3.createCell(13);
        Cell cell314 = row3.createCell(14);
        Cell cell315 = row3.createCell(15);
        Cell cell316 = row3.createCell(16);
        Cell cell317 = row3.createCell(17);
        Cell cell318 = row3.createCell(18);
        Cell cell319 = row3.createCell(19);
        Cell cell320 = row3.createCell(20);
        Cell cell321 =  row3.createCell(21);
        Cell cell322 = row3.createCell(22);
        Cell cell323 = row3.createCell(23);
        Cell cell324 = row3.createCell(24);
        Cell cell325 = row3.createCell(25);
        Cell cell326 = row3.createCell(26);
        Cell cell327 = row3.createCell(27);
        Cell cell328 = row3.createCell(28);

        //加载单元格样式
        cell30.setCellStyle(cellStyle1);
        cell30.setCellValue("");
        cell31.setCellStyle(cellStyle);
        cell31.setCellValue("级别");
        cell32.setCellStyle(cellStyle);
        cell32.setCellValue("预计收入");
        cell33.setCellStyle(cellStyle);
        cell33.setCellValue("级别");
        cell34.setCellStyle(cellStyle);
        cell34.setCellValue("预计收入");
        cell35.setCellStyle(cellStyle);
        cell35.setCellValue("级别");
        cell36.setCellStyle(cellStyle);
        cell36.setCellValue("预计收入");
        cell37.setCellStyle(cellStyle);
        cell37.setCellValue("级别");
        cell38.setCellStyle(cellStyle);
        cell38.setCellValue("预计收入");
        cell39.setCellStyle(cellStyle);
        cell39.setCellValue("级别");
        cell310.setCellStyle(cellStyle);
        cell310.setCellValue("预计收入");
        cell311.setCellStyle(cellStyle);
        cell311.setCellValue("级别");
        cell312.setCellStyle(cellStyle);
        cell312.setCellValue("预计收入");
        cell313.setCellStyle(cellStyle);
        cell313.setCellValue("级别");
        cell314.setCellStyle(cellStyle);
        cell314.setCellValue("预计收入");
        cell315.setCellStyle(cellStyle);
        cell315.setCellValue("级别");
        cell316.setCellStyle(cellStyle);
        cell316.setCellValue("预计收入");
        cell317.setCellStyle(cellStyle);
        cell317.setCellValue("级别");
        cell318.setCellStyle(cellStyle);
        cell318.setCellValue("预计收入");
        cell319.setCellStyle(cellStyle);
        cell319.setCellValue("级别");
        cell320.setCellStyle(cellStyle);
        cell320.setCellValue("预计收入");
        cell321.setCellStyle(cellStyle);
        cell321.setCellValue("级别");
        cell322.setCellStyle(cellStyle);
        cell322.setCellValue("预计收入");
        cell323.setCellStyle(cellStyle);
        cell323.setCellValue("级别");
        cell324.setCellStyle(cellStyle);
        cell324.setCellValue("预计收入");
        cell325.setCellStyle(cellStyle);
        cell325.setCellValue("");
        cell326.setCellStyle(cellStyle);
        cell326.setCellValue("");
        cell327.setCellStyle(cellStyle);
        cell327.setCellValue("");
        cell328.setCellStyle(cellStyle);
        cell328.setCellValue("");

        // 4.操作单元格;将用户列表写入excel
        if (list != null) {
            Perincome perincome = null;
            Monthname monthname = new Monthname();
            EmpSalaryinfo empSalaryinfo1 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo2 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo3 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo4 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo5 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo6 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo7 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo8 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo9 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo10 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo11 = new EmpSalaryinfo();
            EmpSalaryinfo empSalaryinfo12 = new EmpSalaryinfo();
            for (int j = 0; j < list.size(); j++) {
                perincome = list.get(j);
                // 创建数据行,前面有两行,头标题行和列标题行
                Row rowContent1 = sheet.createRow(j + 3);

                Cell cellContent1 = rowContent1.createCell(0);
                cellContent1.setCellStyle(cellStyle1);
                cellContent1.setCellValue((String) perincome.getEmpName());

                monthname = perincome.getMonthname();
                empSalaryinfo1 = monthname.getJanuary();
                empSalaryinfo2 = monthname.getFebruary();
                empSalaryinfo3 = monthname.getMarch();
                empSalaryinfo4 = monthname.getApril();
                empSalaryinfo5 = monthname.getMay();
                empSalaryinfo6 = monthname.getJune();
                empSalaryinfo7 = monthname.getJuly();
                empSalaryinfo8 = monthname.getAugust();
                empSalaryinfo9 = monthname.getSeptember();
                empSalaryinfo10 = monthname.getOctober();
                empSalaryinfo11 = monthname.getNovember();
                empSalaryinfo12 = monthname.getDecember();

                //==============一月==============================
                Cell cellContent2 = rowContent1.createCell(1);
                cellContent2.setCellStyle(cellStyle1);
                cellContent2.setCellValue(empSalaryinfo1.getEmpLevel());

                Cell cellContent3 = rowContent1.createCell(2);
                cellContent3.setCellStyle(cellStyle1);
                cellContent3.setCellValue(empSalaryinfo1.getEmpSalary());

                //==============二月==============================
                Cell cellContent4 = rowContent1.createCell(3);
                cellContent4.setCellStyle(cellStyle1);
                cellContent4.setCellValue(empSalaryinfo2.getEmpLevel());

                Cell cellContent5 = rowContent1.createCell(4);
                cellContent5.setCellStyle(cellStyle1);
                cellContent5.setCellValue(empSalaryinfo2.getEmpSalary());

                //==============三月==============================
                Cell cellContent6 = rowContent1.createCell(5);
                cellContent6.setCellStyle(cellStyle1);
                cellContent6.setCellValue(empSalaryinfo3.getEmpLevel());

                Cell cellContent7 = rowContent1.createCell(6);
                cellContent7.setCellStyle(cellStyle1);
                cellContent7.setCellValue(empSalaryinfo3.getEmpSalary());

                //==============四月==============================
                Cell cellContent8 = rowContent1.createCell(7);
                cellContent8.setCellStyle(cellStyle1);
                cellContent8.setCellValue(empSalaryinfo4.getEmpLevel());

                Cell cellContent9 = rowContent1.createCell(8);
                cellContent9.setCellStyle(cellStyle1);
                cellContent9.setCellValue(empSalaryinfo4.getEmpSalary());

                //==============五月==============================
                Cell cellContent10 = rowContent1.createCell(9);
                cellContent10.setCellStyle(cellStyle1);
                cellContent10.setCellValue(empSalaryinfo5.getEmpLevel());

                Cell cellContent11 = rowContent1.createCell(10);
                cellContent11.setCellStyle(cellStyle1);
                cellContent11.setCellValue(empSalaryinfo5.getEmpSalary());

                //==============六月==============================
                Cell cellContent12 = rowContent1.createCell(11);
                cellContent12.setCellStyle(cellStyle1);
                cellContent12.setCellValue(empSalaryinfo6.getEmpLevel());

                Cell cellContent13 = rowContent1.createCell(12);
                cellContent13.setCellStyle(cellStyle1);
                cellContent13.setCellValue(empSalaryinfo6.getEmpSalary());

                //==============七月==============================
                Cell cellContent14 = rowContent1.createCell(13);
                cellContent14.setCellStyle(cellStyle1);
                cellContent14.setCellValue(empSalaryinfo7.getEmpLevel());

                Cell cellContent15 = rowContent1.createCell(14);
                cellContent15.setCellStyle(cellStyle1);
                cellContent15.setCellValue(empSalaryinfo7.getEmpSalary());

                //==============八月==============================
                Cell cellContent16 = rowContent1.createCell(15);
                cellContent16.setCellStyle(cellStyle1);
                cellContent16.setCellValue(empSalaryinfo8.getEmpLevel());

                Cell cellContent17 = rowContent1.createCell(16);
                cellContent17.setCellStyle(cellStyle1);
                cellContent17.setCellValue(empSalaryinfo8.getEmpSalary());

                //==============九月==============================
                Cell cellContent18 = rowContent1.createCell(17);
                cellContent18.setCellStyle(cellStyle1);
                cellContent18.setCellValue(empSalaryinfo9.getEmpLevel());

                Cell cellContent19 = rowContent1.createCell(18);
                cellContent19.setCellStyle(cellStyle1);
                cellContent19.setCellValue(empSalaryinfo9.getEmpSalary());

                //==============十月==============================
                Cell cellContent20 = rowContent1.createCell(19);
                cellContent20.setCellStyle(cellStyle1);
                cellContent20.setCellValue(empSalaryinfo10.getEmpLevel());

                Cell cellContent21 = rowContent1.createCell(20);
                cellContent21.setCellStyle(cellStyle1);
                cellContent21.setCellValue(empSalaryinfo10.getEmpSalary());

                //==============十一月==============================
                Cell cellContent22 = rowContent1.createCell(21);
                cellContent22.setCellStyle(cellStyle1);
                cellContent22.setCellValue(empSalaryinfo11.getEmpLevel());

                Cell cellContent23 = rowContent1.createCell(22);
                cellContent23.setCellStyle(cellStyle1);
                cellContent23.setCellValue(empSalaryinfo11.getEmpSalary());

                //==============十二月==============================
                Cell cellContent24 = rowContent1.createCell(23);
                cellContent24.setCellStyle(cellStyle1);
                cellContent24.setCellValue(empSalaryinfo12.getEmpLevel());

                Cell cellContent25 = rowContent1.createCell(24);
                cellContent25.setCellStyle(cellStyle1);
                cellContent25.setCellValue(empSalaryinfo12.getEmpSalary());

                Cell cellContent26 = rowContent1.createCell(25);
                cellContent26.setCellStyle(cellStyle1);
                cellContent26.setCellValue(perincome.getEmpStartDate());

                Cell cellContent27 = rowContent1.createCell(26);
                cellContent27.setCellStyle(cellStyle1);
                cellContent27.setCellValue(perincome.getEmpEndDate());

                Cell cellContent28 = rowContent1.createCell(27);
                cellContent28.setCellStyle(cellStyle1);
                cellContent28.setCellValue(perincome.getEmpProfitrate());

                Cell cellContent29 = rowContent1.createCell(28);
                cellContent29.setCellStyle(cellStyle1);
                cellContent29.setCellValue(perincome.getEmpTotalSalary());
            }
        }
        return workbook;
    }

}
