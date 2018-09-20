package com.spider.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil2 {
    /** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }   
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
    /** 
     * 日期格式字符串转换成时间戳 
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String date2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return String.valueOf(sdf.parse(date_str).getTime()/1000);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
      
    /** 
     * 取得当前时间戳（精确到秒） 
     * @return 
     */  
    public static String timeStamp(){  
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);  
        return t;  
    }  

    public static void main(String[] args) {  
        String timeStamp = timeStamp();  
     //  System.out.println("timeStamp="+timeStamp); //运行输出:timeStamp=1470278082
    //  System.out.println(System.currentTimeMillis());//运行输出:1470278082980
        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数
        
    // String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");  
     // System.out.println("date="+date);//运行输出:date=2016-08-04 10:34:42
      String  date="2015年6月12";//2011年1-5 1294156800  1294156800
      System.out.println(date);//2015年6月12//2016-08-04 10:34:42//2011年1-5//2015年7月12//2011年1-5//2016年10月1//2016-06.17
   // System.out.println(getTimeStamp(date));  
      
    }  
    public static java.sql.Timestamp getTimeStamp(String date) throws IllegalStateException{
    	String timeStamp2=null;
    	try{
    		 String format="";
             if(date.matches("[0-9]{4}年[0-9]{1}月[0-9]{1}")){
           	  format="yyyy年M月d";
             } else if(date.matches("[0-9]{4}年[0-9]{2}月[0-9]{1}")){
           	  format="yyyy年MM月d";
             } else if(date.matches("[0-9]{4}年[0-9]{1}月[0-9]{2}")){
           	  format="yyyy年M月dd";
             } else if(date.matches("[0-9]{4}年[0-9]{2}月[0-9]{2}")){
           	  format="yyyy年MM月dd";
             } else if(date.matches("[0-9]{4}年[0-9]{1}-[0-9]{1}")){
           	  format="yyyy年M-d";
             } else if(date.matches("[0-9]{4}年[0-9]{2}-[0-9]{1}")){
           	  format="yyyy年MM-d";
             } else if(date.matches("[0-9]{4}年[0-9]{1}-[0-9]{2}")){
           	  format="yyyy年M-dd";
             } else if(date.matches("[0-9]{4}年[0-9]{2}-[0-9]{2}")){
           	  format="yyyy年MM-dd";
             } if(date.matches("[0-9]{4}-[0-9]{2}.[0-9]{2}")){
           	  format="yyyy-MM.dd";
             } if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}")){
           	  format="yyyy-MM-dd HH:mm:ss";
             }if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")){
              	  format="yyyy-MM-dd HH:mm";
             } if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")){
           	  format="yyyy-MM-dd";
             } if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{2}")){
           	  format="yyyy-M-dd";
             } if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{1}")){
           	  format="yyyy-MM-d";
             } if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{1}")){
           	  format="yyyy-M-d";//2014-4-9 19:4 
             } if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{1} [0-9]{2}:[0-9]{1}")){
              	  format="yyyy-M-d HH:m";
             }if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{1} [0-9]{1}:[0-9]{1}")){
             	  format="yyyy-M-d H:m";
             }if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{1} [0-9]{1}:[0-9]{2}")){
            	  format="yyyy-M-d H:mm";//2018-07-05 3:56
             }if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{1}:[0-9]{1}")){
           	  format="yyyy-MM-dd H:m";//2017-04-26 1:0
             }if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{2} [0-9]{1}:[0-9]{2}")){
              	  format="yyyy-M-dd H:mm";
             }if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{1}:[0-9]{2}")){
             	  format="yyyy-MM-dd H:mm";
             }if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{2} [0-9]{2}:[0-9]{1}")){
           	  format="yyyy-M-dd HH:m";
             }if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{1} [0-9]{1}:[0-9]{2}")){
              	  format="yyyy-M-d H:mm";//2018-7-10 08:36
             }if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{1} [0-9]{2}:[0-9]{2}")){
             	  format="yyyy-M-d HH:mm";//2018-07-5 17:39
             }if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{2} [0-9]{2}:[0-9]{2}")){
            	  format="yyyy-M-dd HH:mm";//2018-7-31 8:2
             }if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{1} [0-9]{2}:[0-9]{2}")){
           	  format="yyyy-MM-d HH:mm";//2013-12-25 16:5
             }if(date.matches("[0-9]{4}-[0-9]{1}-[0-9]{2} [0-9]{1}:[0-9]{1}")){
              	  format="yyyy-M-dd H:m";
             }if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{1}")){
             	  format="yyyy-MM-dd HH:m";
             }if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{1} [0-9]{1}:[0-9]{1}")){
            	  format="yyyy-MM-d H:m";//2017-16-3 3:2
             }if(date.matches("[0-9]{4}-[0-9]{2}-[0-9]{1} [0-9]{1}:[0-9]{2}")){
          	  format="yyyy-MM-d H:mm";
             }              
           timeStamp2 = date2TimeStamp(date, format);  
           return  new java.sql.Timestamp(Long.parseLong(timeStamp2)* 1000);
    	}catch(Exception e){
    		throw new IllegalStateException(date + "-->时间处理出错");
    	}
    	
       
		
    	
    }
}