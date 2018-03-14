package com.xialeme;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.mysql.jdbc.StringUtils;
import com.xialeme.utlis.EexcelTool;
import com.xialeme.utlis.JdbcTool;

/**   
 * @Title: Xls2user.java 
 * @Package com.xialeme 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href="zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题：">Bamboo</a>   
 * @date 2017年6月2日 下午7:22:38 
 * @version V1.0   
 */
public class Xls2user {

	
	static Map<String, String>  tmpMap=new HashMap<>();
	
	
	//获取表列名
		public static void getColumnsByTable(Connection con) {
			try {
				Statement stmt = con.createStatement();
				String phone="";
				String nickName="";
				Iterator<Map.Entry<String, String>> entries = tmpMap.entrySet().iterator(); 
				Integer leng=0;
				while(entries.hasNext()) {
					 Map.Entry<String, String> entry = entries.next();
					 String nameStr=StringUtils.isNullOrEmpty(entry.getValue())?",NULL":",'"+entry.getValue()+"'";
					 boolean rs = stmt.execute("INSERT INTO `t_phone` (`phone`,`name`)   VALUES ('"+entry.getKey()+"'"+nameStr+") " );
					
					leng++;
				}
				System.out.println( "-- 总计"+leng);  
				
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		File excelFile = new File("D:/tep/2017-06-13.xls"); //创建文件对象
    	FileInputStream is = new FileInputStream(excelFile); //文件流
    	tmpMap=EexcelTool.readExcel(is);
    	System.out.println("读完EXCEL>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	Connection con= JdbcTool.getConnection();
    	getColumnsByTable(con);
	}
}

