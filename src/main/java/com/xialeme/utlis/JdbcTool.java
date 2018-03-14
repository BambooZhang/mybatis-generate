package com.xialeme.utlis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import com.xialeme.GenerateCode;
import com.xialeme.entity.SysConfig;
import com.xialeme.entity.TableProperty;

/**
 *   
 * 
 * @Title: jdbcTool.java 
 * @Package com.xialeme.utlis 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         >Bamboo</a>   
 * @date 2017年4月25日 下午3:06:58 
 * @version V1.0   
 */

public class JdbcTool {
	
	//"/application.properties"
	 public static Connection getConnection() {
		// 创建连接
		Connection con = null;
		// 类初始化后加载配置文件
		Map<String, String> configMap=StringTool.getPropertiesValues("application.properties");
		String server = configMap.get("datasource.url");
		String username = configMap.get("datasource.username");
		String password = configMap.get("datasource.password");
		String driver = configMap.get("datasource.driver-class-name");

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			con = DriverManager.getConnection(server, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("连接上数据库>>>>>>>>>>>>");
		
		return con;

	}
	
	
	
}
