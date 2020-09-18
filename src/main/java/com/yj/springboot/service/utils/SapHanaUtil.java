package com.yj.springboot.service.utils;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @DESC: 连接hana的工具
 * @author YJ
 * @DATE: 2020/7/13
 * @VERSION: 0.0.1
 */
@Component
public class SapHanaUtil {

    private static final String DRIVER = "com.sap.db.jdbc.Driver";  //jdbc 4.0

   // private static final String URL = ContextUtil.getGlobalProperty("IAS_HANA_URL");
   // private static final String USER_NAME = ContextUtil.getGlobalProperty("IAS_HANA_USERNAME");
   // private static final String USER_PASSWORD = ContextUtil.getGlobalProperty("IAS_HANA_PASSWORD");
    //private static final String URL = "jdbc:sap://10.6.254.124:30015?reconnect=true";//测试url
   // private static final String USER_NAME = "FSSC";//测试用户名
   // private static final String USER_PASSWORD="Sap123456";//测试密码
    private static final String URL = "jdbc:sap://10.6.254.120:30015?reconnect=true";//生产url
        private static final String USER_NAME = "fssc2hanaprd";//生产用户名
        private static final String USER_PASSWORD="Fssc2%hanaprd0624";//生产密码  Fssc2%hanaprd0624
//    private static final String URL = "";//生产url
//    private static final String USER_NAME = "";//生产用户名
//    private static final String USER_PASSWORD="";//生产密码  Fssc2%hanaprd0624

    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER_NAME, USER_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return connection;
        }
        return connection;
    }


    /**
     * 查询满足条件的数据列表
     * @param sql
     * @return
     */
    public static List getList(String sql) throws Exception{
        List list = new ArrayList();
        Connection conn=null;
        ResultSet rs = null;

        conn = SapHanaUtil.getConnection();
        rs = conn.createStatement().executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int colNum = rsmd.getColumnCount();
        while (rs.next()){
            Object[]  obj = new Object[colNum];
            for (int i = 0; i < colNum; i++) {
                if (i == 0) {
                    obj[i]=rs.getString(i+1);
                } else {
                    obj[i]=(rs.getString(i+1) == null ? "null" : rs.getString(i+1).trim());
                }
            }
            list.add(obj);
        };
        closeResource(conn, rs);

        return list;
    }

    /**
     * 根据条件查询满足条件的数据条数
     * @param sql
     * @return
     */
    public static Integer getCount(String sql) throws Exception{
        int count = 0;
        Connection conn=null;
        ResultSet rs = null;

        conn = SapHanaUtil.getConnection();
        rs = conn.createStatement().executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int colNum = rsmd.getColumnCount();
        while (rs.next()){
            count = rs.getInt(1);
        };
        closeResource(conn, rs);

        return count;
    }

    public static String call(){
        List list = new ArrayList();
        try {
            Connection conn = SapHanaUtil.getConnection();
            //创建存储过程的对象
            CallableStatement c=conn.prepareCall("{call FSSCCloseAccountCheck(?,?,?,？?,?,?)}");
           // 给存储过程的参数设置值
            c.setString(1,"Check_SD_DuplicatepostingofInvoice");
            c.setString(2,"2020");
            c.setString(3,"4");
            c.setString(4,"4390");
            //注册存储过程的参数(可以不要)
            c.registerOutParameter(5, Types.JAVA_OBJECT);
            c.registerOutParameter(6, Types.JAVA_OBJECT);
            //执行存储过程
            c.execute();
            //得到存储过程的输出参数值
            System.out.println (c.getString(5));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "1";
    }


    /**
     * 调用hana存储过程
     * @param sql
     * @return
     */
    public static List getCall(String sql){
        List list = new ArrayList();
        try {
            Connection conn = SapHanaUtil.getConnection();
            // 创建存储过程的对象
            CallableStatement c=conn.prepareCall(sql);
            // 执行存储过程
            c.execute();
            // 获取参数
            ParameterMetaData parameterMetaData = c.getParameterMetaData();
            // 获取参数个数
            int count = parameterMetaData.getParameterCount();
            boolean first = true;
            for (int i=0;i<count;i++)
            {
                // 如果是Table类型的话，解析成列表出来
                // 参数从1 开始
                if(parameterMetaData.getParameterType(i+1)== Types.REF_CURSOR)
                {
                    if(!first){
                        // 判断是否有下一个结果，并将指针指到下一个
                        c.getMoreResults();
                    }
                    else{
                        first = false;
                    }
                    // 获取结果集
                    ResultSet rs = c.getResultSet();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int colNum = rsmd.getColumnCount();
                    List listTemp = new ArrayList();
                    while (rs.next()) {
                        Object[] obj = new Object[colNum];
                        for (int j = 0; j < colNum; j++) {
                            if (j == 0) {
                                // 列从1开始
                                obj[j] = rs.getString(j + 1);
                            } else {
                                obj[j] = (rs.getString(j + 1) == null ? "null" : rs.getString(j + 1).trim());
                            }
                        }
                        listTemp.add(obj);
                    }
                    rs.close();
                    list.add(listTemp);
                }
                else
                {
                    list.add(c.getObject(i+1));
                }
            }
            close(conn,c);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }




    public static void closeResource(Connection conn, ResultSet rs) throws Exception {
        rs.close();
        conn.close();
    }


    public static void close(Connection conn, CallableStatement cs) throws Exception {
        cs.close();
        conn.close();
    }



    public static void main(String[] args) throws Exception {
        String sql ="{call FSSCCloseAccountCheck('Check_SD_DuplicatepostingofInvoice','2019','01','4390',?,?,?)}";
        List list =  getCall(sql);
        System.out.println("1");
    }

}
