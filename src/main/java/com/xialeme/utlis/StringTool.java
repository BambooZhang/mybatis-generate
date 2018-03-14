package com.xialeme.utlis;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.xialeme.GenerateCode;


/**   
 * @Title: StringTool.java 
 * @Package com.xialeme.utlis 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href="mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题：">Bamboo</a>   
 * @date 2017年4月25日 下午3:06:08 
 * @version V1.0   
 */

public class StringTool {
    /** 
     * 功能：下横线转驼峰  abc_str:abcStr
     * @param str 
     * @return 
     */  
    public static String underlineToCamel(String param){  
        if (param==null||"".equals(param.trim())){  
            return "";  
        }  
        int len=param.length();  
        StringBuilder sb=new StringBuilder(len);  
        for (int i = 0; i < len; i++) {  
            char c=Character.toLowerCase(param.charAt(i));
            if (c=='_'){
               if (++i<len){  
                   sb.append(Character.toUpperCase(param.charAt(i)));  
               }  
            }else{  
                sb.append(c);  
            }  
        }  
        return sb.toString();  
    } 
    /** 
     * 功能：根据mysql_jdbc类型获取java类型简称String
     * @param sqlType 
     * @return 
     */  
    public static String fullType2JavaType(String fullType) {
    	String str=null;
    	if(fullType==null){
    		return null;
    	}else{
    		String [] arry=fullType.split("\\.");
    		str=arry[2];
    	}
        return str;
    }
    
    /** 
     * 功能：根据mysql dataType类型获取VARCHAR_java.lang.String
     * @param sqlType 
     * @return 
     */  
    public static String sqlType2JavaFullType(String sqlType) {  
    	Map<String, String> typeMap=StringTool.getPropertiesValues("mysql.properties");
    	
       /* if(sqlType.equalsIgnoreCase("bit")){  
            return "boolean";  
        }else if(sqlType.equalsIgnoreCase("tinyint")){  
            return "byte";  
        }else if(sqlType.equalsIgnoreCase("smallint")){  
            return "short";  
        }else if(sqlType.equalsIgnoreCase("int")){  
            return "int";  
        }else if(sqlType.equalsIgnoreCase("bigint")){  
            return "long";  
        }else if(sqlType.equalsIgnoreCase("float")){  
            return "float";  
        }else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")   
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")   
                || sqlType.equalsIgnoreCase("smallmoney")|| sqlType.equalsIgnoreCase("double")){  
            return "double";  
        }else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")   
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")   
                || sqlType.equalsIgnoreCase("text")){  
            return "String";  
        }else if(sqlType.equalsIgnoreCase("datetime")){  
            return "Date";  
        }else if(sqlType.equalsIgnoreCase("image")){  
            return "Blod";  
        } */ 
          
        return typeMap.get(sqlType.toUpperCase());
    }  
    
    
    //读取propertiesfile属性key-value;
    @SuppressWarnings("unused")
	public static Map<String, String> getPropertiesValues(String propertiesfile) {
    	Map<String, String> keyMap=new HashMap<String, String>();
    	
    	InputStream in = GenerateCode.class.getClassLoader().getResourceAsStream(propertiesfile);
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
        Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();  
        while (it.hasNext()) {  
            Entry<Object, Object> entry = it.next();  
            String key = entry.getKey().toString();  
            String value = entry.getValue()==null?null:entry.getValue().toString();  
            keyMap.put(key,value);
          //  System.out.println(key+"=" + value);  
        }  
        
        return keyMap;
    }  
    
    
    
    public static void main(String[] args) {
    	//getPropertiesValues("mysql.properties");
    	System.out.println(sqlType2JavaFullType("int"));
    	System.out.println(fullType2JavaType("INTEGER_java.lang.Integer"));
	}
}


