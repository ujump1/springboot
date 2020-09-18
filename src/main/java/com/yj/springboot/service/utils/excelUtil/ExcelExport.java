package com.yj.springboot.service.utils.excelUtil;


import com.yj.springboot.service.exception.MessageRuntimeException;
import com.yj.springboot.service.utils.ResourceBundleUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelExport {
    //类型，条件，例子，请求
    public void exportDataSourceToExcel(String type, String search, HttpServletResponse response, String json, HttpServletRequest request) {
        //JSONObject jsonObject=JSONObject.parseObject(json);
        String dataSourceName = ResourceBundleUtil.getString(type, "datasource_chinese");
        Date date = new Date();
        String fileName = dataSourceName + date + ".xlsx";
        List list = null;
        Workbook workbook = null;
        switch (type) {
            case "SalesInvoiceDataExport":
                if (!StringUtils.isEmpty(json)) {
                    //todo json也可以用来过滤条件
                }
                //Todo 寻找导出的数据
                list = new ArrayList();
                break;
            case "BillingData":
                if (!StringUtils.isEmpty(json)) {
                    //todo json也可以用来过滤条件
                }
                //Todo 寻找导出的数据
                list = new ArrayList();
                break;
            default:
                break;
        }
        if (CollectionUtils.isEmpty(list)) {
            throw new MessageRuntimeException("没有导出数据！");
        }
        workbook = ExcelUtils.exportDataSource(list, fileName, type);
        if (null == workbook) {
            throw new MessageRuntimeException("文件不存在！");
        }
        ExcelUtils.outputFile(request, response, workbook, fileName);
    }
}
