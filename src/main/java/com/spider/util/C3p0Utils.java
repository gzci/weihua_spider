package com.spider.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
public class C3p0Utils {
	private static ComboPooledDataSource cpds=null;  
	private static final Logger log = LoggerFactory.getLogger(C3p0Utils.class);
    static{
    	init();
    }
    private static void init(){
    	try{
    		ResourceBundle rb = null;
    		if (SystemUtils.IS_OS_LINUX) {
    			rb = ResourceBundle.getBundle("config");
    		}else if(SystemUtils.IS_OS_WINDOWS){
    			rb = ResourceBundle.getBundle("config-test");
    		}
    		cpds = new ComboPooledDataSource(rb.getString("config"));
    	}catch(Exception e){
    		log.error(e.getMessage());
    	}
    	 
    }
    /** 
     * 获得数据库连接 
     * @return   Connection 
     */  
    public static Connection getConnection(){  
        try {  
            return cpds.getConnection();  
        } catch (SQLException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
      
    /** 
     * 数据库关闭操作 
     * @param conn   
     * @param st     
     * @param pst 
     * @param rs 
     */  
    public static void close(Connection conn,PreparedStatement pst,ResultSet rs){  
        if(rs!=null){  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if(pst!=null){  
            try {  
                pst.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
  
        if(conn!=null){  
            try {  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    /** 
     * 测试DBUtil类 
     * @param args 
     */  
    public static void main(String[] args) {  
        Connection conn=getConnection();  
        System.out.println(conn.getClass().getName());
        Statement stmt;
        ResultSet rs=null;
        try {
            stmt = conn.createStatement();
            rs=stmt.executeQuery("select  source_name from seed limit 10");
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       // close(conn,null,null);  
    } 
	

}
