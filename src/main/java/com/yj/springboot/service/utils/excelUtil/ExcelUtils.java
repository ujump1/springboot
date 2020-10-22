package com.yj.springboot.service.utils.excelUtil;



import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yj.springboot.entity.ExcelData;
import com.yj.springboot.entity.User;
import com.yj.springboot.service.exception.MessageRuntimeException;
import com.yj.springboot.service.responseModel.ResponseModel;
import com.yj.springboot.service.utils.EmptyUtil;
import com.yj.springboot.service.utils.LogUtil;
import com.yj.springboot.service.utils.ResourceBundleUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @description:
 * @author:Qieqiaoyu
 * @create: 2018/8/27.
 */

public class ExcelUtils {

    private final static String excel2003L = ".xls";    //2003- 版本的excel
    private final static String excel2007U = ".xlsx";   //2007+ 版本的excel

    private final static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @param cell 一个单元格的对象
     * @return 返回该单元格相应的类型的值
     */
    private static String getRightTypeCell(Cell cell) {
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    /**
     * 获取Excel的数据
     *
     * @param filePath 文件路径
     * @param tClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends Object> List<T> getDataFromExcelByPath(String filePath, Class<T> tClass) {
        List<T> result = new ArrayList<>();
        if (!filePath.endsWith(excel2003L) && !filePath.endsWith(excel2007U)) {
            LogUtil.error("文件不是excel类型");
            throw new MessageRuntimeException("文件不是excel类型");
        }
        FileInputStream fis = null;
        Workbook workbook = null;
        int lineNum = 0;
        Sheet sheet = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtil.error("文件路径不存在！");
            throw new MessageRuntimeException("文件路径不存在！");
        }
        if (filePath.endsWith(excel2003L)) {
            try {
                workbook = new HSSFWorkbook(fis);//得到工作簿
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.error("工作簿创建失败！");
                throw new MessageRuntimeException("工作簿创建失败!");
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.error("io流关闭失败!");
                }
            }
        } else {
            try {
                workbook = new XSSFWorkbook(fis);//得到工作簿
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.error("工作簿创建失败！");
                throw new MessageRuntimeException("工作簿创建失败!");
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.error("io流关闭失败1");
                }
            }
        }
        //sheet的数量
        int sheetNum = workbook.getNumberOfSheets();
        //得到一个工作表
        result = toSheet(tClass, result, workbook, sheetNum);
        return result;
    }

    /**
     * @param tClass   数据源类class
     * @param result   返回值
     * @param workbook excel的工作簿
     * @param sheetNum excel当前工作簿 默认为：0
     * @param <T>
     * @return
     */
    private static <T extends Object> List<T> toSheet(Class<T> tClass, List<T> result, Workbook workbook, int sheetNum) {
        Sheet sheet;
        int lineNum;
        for (int i = 0; i < sheetNum; i++) {
            sheet = workbook.getSheetAt(i);
            //获得表头
            // Row row=sheet.getRow(0);
            //列数
            int col = sheet.getRow(0).getPhysicalNumberOfCells();
            //行数
            lineNum = sheet.getLastRowNum();

            if (0 == lineNum) {
                throw new RuntimeException("Excel内无数据！");
            }

            result = getData(sheet, lineNum, col, result, tClass);
        }
        return result;
    }

    public static <T extends Object> List<T> getDataFromExcelByFile(MultipartFile files, Class<T> tClass) {
        List<T> result = new ArrayList<>();
        if (!files.getOriginalFilename().endsWith(excel2003L) && !files.getOriginalFilename().endsWith(excel2007U)) {
            LogUtil.error("文件不是excel类型");
            throw new MessageRuntimeException("文件不是excel类型");
        }
        if (!files.getOriginalFilename().contains(ResourceBundleUtil.getString(tClass.getSimpleName(), "datasource_chinese"))) {
            throw new MessageRuntimeException("导入数据失败，请使用正确的数据源模板");
        }
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            fis = (FileInputStream) files.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error("上传文件失败！");
            throw new MessageRuntimeException("上传文件失败！");
        }

        try {
            if (files.getName().endsWith(excel2003L)) {
                workbook = new HSSFWorkbook(fis);//得到工作簿
            } else {
                workbook = new XSSFWorkbook(fis);//得到工作簿
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error("工作簿为空！");
            new MessageRuntimeException("工作簿为空！");
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.error("io流关闭失败！");
                }
            }
        }
        //sheet的数量
        int sheetNum = workbook.getNumberOfSheets();
        //得到一个工作表
        result = toSheet(tClass, result, workbook, sheetNum);
        return result;
    }

    /**
     * @param sheet
     * @param lineNum 行数
     * @param col     列数
     * @param result  结果集
     * @param tClass  类
     * @param <T>
     * @return
     */
    public static <T extends Object> List<T> getData(Sheet sheet, int lineNum, int col, List<T> result, Class<T> tClass) {
        List<String> tableHead = new ArrayList<>();
        Row row = sheet.getRow(0);
        //表头的获取
        for (int k = 0; k < col; k++) {
            String string = getRightTypeCell(row.getCell(k));
            tableHead.add(string);
        }
        //表的数据的获取
        for (int i = 1; i <= lineNum; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            List<String> objectList = new ArrayList<>();
            for (int j = 0; j < col; j++) {
                //发现单元格为空值后，往list里添加""空字符串
                if (StringUtils.isEmpty(row.getCell(j))) {
                    objectList.add("");
                } else {
                    String str = getRightTypeCell(row.getCell(j));
                    objectList.add(str);
                }
            }
            T o = setValue(tableHead, tClass, objectList);
            result.add(o);

        }
        return result;
    }

    /**
     * 导出数据并生成Excel文档
     *
     * @param sheetName  工作簿名称
     * @param excelData
     * @param sourcePath 文件路径
     */
    public static Workbook exportExcel(String sheetName, List<ExcelData> excelData, String sourcePath) {
        InputStream is = createNewFile(sourcePath);
        Workbook workbook = null;
        Sheet sheet = null;
        //解析sheetName
        try {
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error("工作簿创建失败!");
            throw new MessageRuntimeException("工作簿创建失败!");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!StringUtils.isEmpty(sheetName)) {
            sheetName = ResourceBundleUtil.getString(sheetName, "datasource_chinese");
            sheet = workbook.getSheet(sheetName);
            if (sheet != null) {
                for (ExcelData e : excelData) {
                    String[] cellKey = e.getKey().split("_");
                    Row row = sheet.getRow(Integer.parseInt(cellKey[1]) - 1);
                    Cell cell = row.getCell(letterToNumber(cellKey[0]) - 1);
                    cell.setCellValue(e.getValue());
                }
            }
        } else {
            for (ExcelData e : excelData) {
                EmptyUtil.checkParamIsEmpty(e.getKey(), e.getTableName());
                sheetName = ResourceBundleUtil.getString(e.getTableName(), "datasource_chinese");
                sheet = workbook.getSheet(sheetName);
                String[] cellKey = e.getKey().split("_");
                Row row = sheet.getRow(Integer.parseInt(cellKey[1]) - 1);
                if (row == null) {
                    continue;
                }
                Cell cell = row.getCell(letterToNumber(cellKey[0]) - 1);
                if (cell == null) {
                    continue;
                }
                cell.setCellValue(e.getValue());
            }
        }
        return workbook;

    }

    /**
     * 导出数据并生成Excel文档
     *
     * @param sheetName 工作簿名称
     * @param excelData
     */
    public static Workbook exportExcel(String sheetName, List<ExcelData> excelData, Workbook workbook) {
        Sheet sheet = null;
        if (!StringUtils.isEmpty(sheetName)) {
            sheetName = ResourceBundleUtil.getString(sheetName, "datasource_chinese");
            sheet = workbook.getSheet(sheetName);
            if (sheet != null) {
                for (ExcelData e : excelData) {
                    String[] cellKey = e.getKey().split("_");
                    Row row = sheet.getRow(Integer.parseInt(cellKey[1]) - 1);
                    Cell cell = row.getCell(letterToNumber(cellKey[0]) - 1);
                    cell.setCellValue(e.getValue());
                }
            }
        } else {
            for (ExcelData e : excelData) {
                EmptyUtil.checkParamIsEmpty(e.getKey(), e.getTableName());
                sheetName = ResourceBundleUtil.getString(e.getTableName(), "datasource_chinese");
                String[] cellKey = e.getKey().split("_");
                sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    continue;
                }
                Row row = sheet.getRow(Integer.parseInt(cellKey[1]) - 1);
                if (row == null) {
                    continue;
                }
                Cell cell = row.getCell(letterToNumber(cellKey[0]) - 1);
                if (cell == null) {
                    continue;
                }
                cell.setCellValue(e.getValue());
            }
        }
        return workbook;

    }

    /**
     * 读取文件io流的形式
     *
     * @param sourcePath 模板路径
     * @return
     */
    public static InputStream createNewFile(String sourcePath) {
        Resource resource = new ClassPathResource(sourcePath);
        InputStream inputStream = null;
        try {
            File file = resource.getFile();
            inputStream = new BufferedInputStream(new FileInputStream(file), 1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtil.error("文件路径错误！");
            throw new MessageRuntimeException("文件路径错误！");

        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error("文件路径错误！");
            throw new MessageRuntimeException("文件路径错误！");
        }

        return inputStream;
    }


    /**
     * 根据属性，拿到set方法，并把值set到对象中
     *
     * @param tClass
     * @param value
     */
    private static <T extends Object> T setValue(List<String> tableHeadList, Class<T> tClass, List<String> value) {
        T t = null;
        try {
            t = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //中文属性名称
        List<String> keys = new ArrayList<>(findPropertiesToList(tClass.getSimpleName(), "taxInfo"));
        //属性名称英文
        List<String> propertyKeys = new ArrayList<>(findPropertiesToList(tClass.getSimpleName() + "_en", "taxInfo"));
        String type = null;
        Method method = null;
        if (tableHeadList.size() != propertyKeys.size()) {
            throw new MessageRuntimeException("模板格式错误，请重新下载模板！");
        }
        Map<String, Method> map = new HashMap<>();
        List<Method> methodList = Arrays.asList(tClass.getMethods());
        //优化导入获取set方法并且执行的过程
        for (Method m : methodList) {
            map.put(m.getName(), m);
        }
        for (int i = 0; i < tableHeadList.size(); i++) {
            if ("".equals(value.get(i)) || null == value.get(i)) {
                continue;
            }
            try {
                int indexOf = keys.indexOf(tableHeadList.get(i));
                String methodName = "set" + propertyKeys.get(indexOf).substring(0, 1).toUpperCase() + propertyKeys.get(indexOf).substring(1);
                method = map.get(methodName);
                type = method.getParameters()[0].getType().getSimpleName();
                switch (type) {
                    case "String":
                        method.invoke(t, value.get(i));
                        break;
                    case "int":
                        method.invoke(t, Integer.parseInt(String.valueOf(value.get(i))));
                        break;
                    case "long":
                        method.invoke(t, Long.parseLong(String.valueOf(value.get(i))));
                        break;
                    case "Date":
                        method.invoke(t, parseDate(value.get(i)));
                        break;
                    case "BigDecimal":
                        String replace = value.get(i).replace("%", "");
                        method.invoke(t, new BigDecimal(replace));
                        break;
                    case "Integer":
                        method.invoke(t, Integer.parseInt(String.valueOf(value.get(i))));
                        break;
                    default:
                        break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                LogUtil.error("通过反射获取get方法错误！");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                LogUtil.error("通过反射获取get方法错误！");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new MessageRuntimeException("文件表头错误，请下载最新模板！");
            } catch (NumberFormatException e) {
                throw new MessageRuntimeException("第" + (i + 1) + "列存在错误格式数据,请检查");
            }
        }
        return t;
    }

    /**
     * 格式化String为Date
     *
     * @param dateStr
     * @return date
     */
    private static Date parseDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            try {
                date = simpleDateFormat.parse(dateStr);
            } catch (ParseException e1) {
                throw new MessageRuntimeException("时间格式错误，请参考模板格式");
            }
        }
        return date;
    }

    /**
     * 获取类型，依靠反射执行其get方法
     *
     * @param propertyName
     * @param o
     * @return
     */
    private static Object getValue(String propertyName, Object o) {
        String methodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Object ob = null;
        try {
            Method m = o.getClass().getMethod(methodName);
            ob = m.invoke(o);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ob;
    }

    /**
     * @Description: 数据源导出，并形成Excel文件的形式
     * @Param: [data, exportPath, name, tClass]
     * @return: void
     * @Author: Nieqiaoyu
     * @Date: 2018/9/13
     */
    public static <T> Workbook exportDataSource(List<T> data, String fileName, String type) {
        //配置文件读取的类的属性中文名
        List<String> properties = new ArrayList<>(findPropertiesToList(type, "taxInfo"));
        //配置文件读取的类的属性
        List<String> propertiesEn = new ArrayList<>(findPropertiesToList(type + "_en", "taxInfo"));
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook();
        } else if (excel2007U.equals(fileType)) {
            wb = new SXSSFWorkbook();
        } else {
            throw new MessageRuntimeException("文件类型错误");
        }
        Sheet sheet = wb.createSheet();
        wb.setSheetName(0, fileName.split("\\.")[0]);
        //单元格为纯文本格式
        Cell cell = null;
        CellStyle textStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        //创建表头
        Row headRow = sheet.createRow(0);
        for (int i = 0; i < properties.size(); i++) {
            cell = headRow.createCell(i);
            cell.setCellValue(properties.get(i));
        }
        for (int k = 0; k < properties.size(); k++) {
            sheet.autoSizeColumn(k);
            sheet.setColumnWidth(k, properties.get(k).getBytes().length * 256);
        }
        //循环数据并写入Excel文件内
        Row row = null;
        //Cell cell = null;
        for (int i = 1; i <= data.size(); i++) {
            row = sheet.createRow(i);
            for (int j = 0; j < properties.size(); j++) {
                cell = row.createCell(j);
                Object value = getValue(propertiesEn.get(j), data.get(i - 1));
                String.valueOf(value);
                //设置单元值
                cell.setCellValue(null == value ? "" : value.toString());
                //设置单元格格式
                cell.setCellStyle(textStyle);
            }
        }
        return wb;
    }


    private static List<String> findPropertiesToList(String key, String path) {
        String str = ResourceBundleUtil.getString(key, path);
        String[] tableHead = str.split(",");
        return Arrays.asList(tableHead);

    }

    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {

        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);

        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error("json串解析错误");
            throw new RuntimeException("json串解析错误");
        }

    }

    /**
     * @Description: 把Excel文件中的列是字母的转化为数字
     * @Param: [letter]
     * @return: int
     * @Author: Nieqiaoyu
     * @Date: 2018/9/18
     */
    public static int letterToNumber(String letter) {
        if (letter == null || "".equals(letter)) {
            LogUtil.error("单元格key为空！");
            throw new MessageRuntimeException("key为空！");
        }
        //转大写
        String upperLetter = letter.toUpperCase();
        if (!upperLetter.matches("[A-Z]+")) {
            LogUtil.error("单元格key不符合要求！");
            throw new MessageRuntimeException("key的值不符合要求！");
        }
        // 存放结果数值
        long num = 0;
        long base = 1;
        // 从字符串尾部开始向头部转换
        for (int i = upperLetter.length() - 1; i >= 0; i--) {
            char ch = upperLetter.charAt(i);
            num += (ch - 'A' + 1) * base;
            base *= 26;
            // 防止内存溢出
            if (num > Integer.MAX_VALUE) {
                LogUtil.error("溢出！");
                throw new MessageRuntimeException("内存溢出！");
            }
        }
        return (int) num;
    }

    /**
     * 数字转字母
     *
     * @param num
     * @return
     */
    public static String numberToLetter(int num) {
        if (num <= 0) {
            return null;
        }
        String letter = "";
        num--;
        do {
            if (letter.length() > 0) {
                num--;
            }
            letter = ((char) (num % 26 + (int) 'A')) + letter;
            num = (int) ((num - num % 26) / 26);
        } while (num > 0);

        return letter;
    }

    /**
     * 下载模板
     *
     * @param type
     * @param fileName
     * @return
     */
    public static Workbook dataSourceExcelModel(String type, String fileName) {
        List<String> tableHead = new ArrayList<>(findPropertiesToList(type, "taxInfo"));
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        workbook.setSheetName(0, fileName.split("\\.")[0]);
        Row headRow = sheet.createRow(0);
        Cell cell = null;
        CellStyle textStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        for (int i = 0; i < tableHead.size(); i++) {
            cell = headRow.createCell(i);
            cell.setCellValue(tableHead.get(i));
            sheet.setDefaultColumnStyle(i, textStyle);
        }
        for (int k = 0; k < tableHead.size(); k++) {
            sheet.autoSizeColumn(k);
            sheet.setColumnWidth(k, tableHead.get(k).getBytes().length * 1 * 256);
        }
        return workbook;
    }


    /**
     * 设置头信息
     *
     * @param response
     * @param fileName
     */
    public static void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        String userAgent = request.getHeader("User-Agent");
        // 针对IE或者以IE为内核的浏览器：
        try {
//            if (userAgent.contains("MSIE") || userAgent.contains("Trident") || userAgent.contains("Edge ")) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
//            } else {
//                // 非IE浏览器的处理：
//                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
//            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        //  客户端不缓存 
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }

    /**
     * 输出
     *
     * @param request
     * @param response
     * @param workbook
     * @param fileName
     */
    public static void outputFile(HttpServletRequest request, HttpServletResponse response, Workbook workbook, String fileName) {
        setResponseHeader(request, response, fileName);
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new MessageRuntimeException("下载文件模板异常，写文件流错误！");
        }
    }

    public static void addData(List<ExcelData> excelDataList, Sheet sheet) {
        if (!CollectionUtils.isEmpty(excelDataList)) {
            for (ExcelData e : excelDataList) {
                String[] cellKey = e.getKey().split("_");
                Row row = sheet.getRow(Integer.parseInt(cellKey[1]) - 1);
                if (row == null) {
                    continue;
                }
                Cell cell = row.getCell(ExcelUtils.letterToNumber(cellKey[0]) - 1);
                if (cell == null) {
                    continue;
                }
                cell.setCellValue(e.getValue());
            }
        }
    }

    /**
     * 复制sheet
     *
     * @param sheetFrom
     * @param sheetTo
     */
    public static void copySheet(Sheet sheetFrom, Sheet sheetTo) {
        CellRangeAddress region = null;
        Row rowFrom = null;
        Row rowTo = null;
        Cell cellFrom = null;
        Cell cellTo = null;
        //合并单元格复制
        for (int i = 0; i < sheetFrom.getNumMergedRegions(); i++) {
            region = sheetFrom.getMergedRegion(i);
            if ((region.getFirstColumn() >= sheetFrom.getFirstRowNum())
                    && (region.getLastRow() <= sheetFrom.getLastRowNum())) {
                sheetTo.addMergedRegion(region);
            }
        }
        //行复制
        for (int intRow = sheetFrom.getFirstRowNum(); intRow <= sheetFrom.getLastRowNum(); intRow++) {
            rowFrom = sheetFrom.getRow(intRow);
            rowTo = sheetTo.createRow(intRow);
            if (null == rowFrom) {
                continue;
            }
            rowTo.setHeight(rowFrom.getHeight());
            //单元格复制
            for (int intCol = 0; intCol < rowFrom.getLastCellNum(); intCol++) {
                sheetTo.setDefaultColumnStyle(intCol, sheetFrom.getColumnStyle(intCol));
                sheetTo.setColumnWidth(intCol, sheetFrom.getColumnWidth(intCol));
                cellFrom = rowFrom.getCell(intCol);
                cellTo = rowTo.createCell(intCol);
                if (null == cellFrom) {
                    continue;
                }
                cellTo.setCellStyle(cellFrom.getCellStyle());
                cellTo.setCellType(cellFrom.getCellType());
                if (null != cellFrom.getStringCellValue() && !"".equals(cellFrom.getStringCellValue().trim())) {
                    cellTo.setCellValue(cellFrom.getStringCellValue());
                }
            }
        }
    }

    public static Map<String, ArrayList<ExcelData>> splitByTableName(List<ExcelData> excelDataList) {
        Map<String, ArrayList<ExcelData>> map = new TreeMap<>();
        for (ExcelData e : excelDataList) {
            if (map.containsKey(e.getTableName())) {
                map.get(e.getTableName()).add(e);
            } else {
                List<ExcelData> tem = new ArrayList<>();
                tem.add(e);
                map.put(e.getTableName(), (ArrayList<ExcelData>) tem);
            }
        }
        return map;
    }

    public static Workbook createWorkBook(InputStream is, Workbook workbook) {
        try {
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            LogUtil.error("工作簿创建失败!");
            throw new MessageRuntimeException("工作簿创建失败!");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return workbook;
    }

    public static void copyTableRemark(String tableRemarkPath, Sheet sheet, int rowNum) {
        InputStream is = ExcelUtils.createNewFile(tableRemarkPath);
        try {
            XSSFWorkbook workbookRemark = new XSSFWorkbook(is);
            Sheet tableRemarkSheet = workbookRemark.getSheetAt(0);
            mergerRegion(sheet, tableRemarkSheet, rowNum + 1);
            int firstRow = tableRemarkSheet.getFirstRowNum();
            int lastRow = tableRemarkSheet.getLastRowNum();
            for (int i = firstRow; i < lastRow; i++) {
                Row row = sheet.createRow(rowNum + i + 1);
                Row tableRemarkRow = tableRemarkSheet.getRow(i);
                int firstCell = tableRemarkRow.getFirstCellNum();
                int lastCell = tableRemarkRow.getLastCellNum();
                for (int j = firstCell; j < lastCell; j++) {
                    row.createCell(j);
                    XSSFCellStyle cellStyle = (XSSFCellStyle) row.getCell(j).getCellStyle();
                    cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
                    cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
                    cellStyle.setBorderTop(BorderStyle.THIN);//上边框
                    cellStyle.setBorderRight(BorderStyle.THIN);//右边框
                    //设置边框
                    row.createCell(j).setCellStyle(cellStyle);
                    tableRemarkRow.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    row.createCell(j).setCellValue(tableRemarkRow.getCell(j).getStringCellValue());
                }
            }
        } catch (IOException e) {
            throw new MessageRuntimeException("表格式错误");
        }
    }

    private static void mergerRegion(Sheet sheetCreate, Sheet sheetSource, int rowNum) {
        int sheetMergerCount = sheetSource.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress region = sheetSource.getMergedRegion(i);
            CellRangeAddress cellRangeAddress = new CellRangeAddress(region.getFirstRow() + rowNum, region.getLastRow() + rowNum, region.getFirstColumn(), region.getLastColumn());
            sheetCreate.addMergedRegion(cellRangeAddress);
        }
    }


    /**
     * 计算行号相差的行数
     */
    public static Integer countRows(Integer startRow, Integer endRow) {
        if (endRow == null || endRow.doubleValue() <= startRow.doubleValue()) {
            return 0;
        }
        return endRow - startRow - 1;
    }


    /**
     * 在当前行插入N行
     *
     * @param sheet
     * @param startRow
     * @param countRows
     */
    public static void insertRow(Sheet sheet, Integer startRow, Integer countRows) {
        for (int i = 0; i < countRows; i++) {
            insertRow(sheet, startRow + i);
        }
    }

    /**
     * 在当前行插入一条行
     *
     * @param sheet
     * @param startRow
     */
    public static void insertRow(Sheet sheet, Integer startRow) {
        sheet.shiftRows(startRow, sheet.getLastRowNum(), 1, true, false);
        Row newRow = sheet.createRow(startRow);
        Row oldRow = sheet.getRow(startRow - 1);
        newRow.setRowStyle(oldRow.getRowStyle());
        newRow.setHeight(oldRow.getHeight());
        int cellNum = oldRow.getLastCellNum();
        for (int i = 0; i < cellNum; i++) {
            Cell newCell = newRow.createCell(i);
            if (!ObjectUtils.isEmpty(oldRow.getCell(i))) {
                newCell.setCellStyle(oldRow.getCell(i).getCellStyle());
            }
        }
        //单元格合并
        int regions = sheet.getNumMergedRegions();
        for (int i = 0; i < regions; i++) {
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == oldRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(), (newRow.getRowNum() +
                        (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress
                        .getFirstColumn(), cellRangeAddress.getLastColumn());
                sheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }

    //下载模板
    // @ApiOperation(value = "下载模板", notes = "下载模板")
    @GetMapping(value = "downloadTemplates")
    //@ApiImplicitParams({@ApiImplicitParam(name = "type", value = "模板类型（SalesInvoiceData：销项开票数据,BillingData:记账不开票数据, CertifiedInvoiceData:增值税专用发票认证数据  , CrtifiedPassgerTransportTaxData:增值税旅客运输扣税凭证数据  ,DeductibleInputTax:待抵扣进项税额 ,IncomeOutData:进项转出数据 ,NoticeAbnormalData:稽核结果通知书异常数据 , NoticeMatchData:稽核结果通知书相符数据, NoticeStayData:稽核结果通知书留待继续比对数据 ,ScarletLetter:红字通知单 ,TaxableItem:营改增税负明细表应税项目,TaxReliefType：减免税分类，LandAssets：城镇土地使用税主数据,EconomicAgreement:经济类合同,LoanAgreement:借款合同,PolicyManagementAnalysis:政策解读管理)")})
    public void downExcelModel(String type, HttpServletResponse response, HttpServletRequest request) {
        String fileChineseName = ResourceBundleUtil.getString(type, "datasource_chinese") + ".xlsx";
        Workbook workbook = ExcelUtils.dataSourceExcelModel(type, fileChineseName);
        ExcelUtils.outputFile(request, response, workbook, fileChineseName);
    }

    /**
     * 导入数据源
     * @param files
     */
    @PostMapping(value = "importUser")
    // @ApiOperation(value = "导入数据源", notes = "导入数据源")
    // @ApiImplicitParams(@ApiImplicitParam(name = "files",value = "上传文件流"))
    // 如果是多个的话，请用List<MultipartFile> files
    // 传参多个文档(参考学习文档）用
    // formData
    //  files:(binary)
    //  files:(binary)
    public ResponseModel importUser(MultipartFile files){
        List<User> dataList = ExcelUtils.getDataFromExcelByFile(files, User.class);
        // todo 保存dataList
        return ResponseModel.SUCCESS();
    }

}