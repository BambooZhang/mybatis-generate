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
public class Xls2table {

	
	static Map<String, String>  tmpMap=new HashMap<>();
	
	
	//获取表列名
		public static void getColumnsByTable(Connection con,String outputFilePath) {
			try {
				Statement stmt = con.createStatement();
				String phone="";
				String nickName="";
				Iterator<Map.Entry<String, String>> entries = tmpMap.entrySet().iterator();  
				while(entries.hasNext()) {
					 Map.Entry<String, String> entry = entries.next();
					ResultSet rs = stmt.executeQuery("select * from xm_user where phone='"+entry.getKey()+"' " );
					if(rs.next()){
						//已经存在了
						System.out.println("已经存在"+entry.getKey());
						entries.remove();
					} 
					rs.close();
				}
				
				 
				Integer leng=0;
				StringBuffer sb= new StringBuffer();
				for (Entry<String, String> entry : tmpMap.entrySet()) {  
					  
					String tem=" NULL, '"+entry.getKey();
					if(entry.getValue()!=null&&!StringUtils.isEmptyOrWhitespaceOnly(entry.getValue())){
						tem=" '"+entry.getValue()+"', '"+entry.getKey();
					}
					String sql="INSERT INTO `xm_user` (  `nickname`, `phone`, `pwd`, `gender`, `emali`, `openid`, `wallet`, `my_code`, `invitation_uid`, `invitation_code`, `create_time`, `update_time`, `status`, `avatar`, `user_type_id`, `top_invitation_uid`, `layer`) VALUES ("
							+tem+"','375bd9d75f2ebd6fbc002213417eb758', 1, NULL, NULL, 0.00, 'MN', NULL, NULL, unix_timestamp(now())+round(RAND( )*10000), unix_timestamp(now()), 0, NULL, 0, NULL, NULL);";
					
					
				    System.out.println( sql);  
				    sb.append(sql+"\r\n");
				    leng++;
				  
				}  
				
				System.out.println( "-- 总计"+leng);  
				//stmt.execute();
				
				try {
				File f = new File(outputFilePath);  
		           if (f.exists()) {  
		               System.out.println("文件存在"+outputFilePath);  
		           } else {  
		               System.out.println("文件不存在"+outputFilePath);  
		               f.createNewFile();// 不存在则创建  
		               System.out.println("文件创建成功"+outputFilePath); 
		           } 
		   		BufferedWriter output = new BufferedWriter(new FileWriter(f));  
		           output.write(sb.toString());  
		           
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		File excelFile = new File("D:/tep/2017-06-05.xls"); //创建文件对象
    	FileInputStream is = new FileInputStream(excelFile); //文件流
    	tmpMap=EexcelTool.readExcel(is);
    	System.out.println("读完EXCEL>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	Connection con= JdbcTool.getConnection();
    	getColumnsByTable(con,"D:/tep/2017-06-05.sql");
	}
}

