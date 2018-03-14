package com.xialeme;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.mysql.jdbc.StringUtils;
import com.xialeme.entity.ColumnProperty;
import com.xialeme.entity.SysConfig;
import com.xialeme.entity.TableProperty;
import com.xialeme.utlis.JdbcTool;
import com.xialeme.utlis.StringTool;

import junit.framework.Test;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.StringUtil;

/**
 *   
 * 
 * @Title: Main.java 
 * @Package com.xialeme.main 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         >Bamboo</a>   
 * @date 2017年4月25日 下午2:53:46 
 * @version V1.0   
 */

public class GenerateCode {
	static SysConfig sysConfig;
	static Connection conn;
	static List<TableProperty> tablePropertyList=new ArrayList<TableProperty>();//表名
	/***
	 * 资源配置初始化
	 * @param applicationConfig
	 */
	public static void init() {
		// 类初始化后加载配置文件
		Map<String, String> configMap=StringTool.getPropertiesValues("application.properties");
		String server = configMap.get("datasource.url");
		String username = configMap.get("datasource.username");
		String password = configMap.get("datasource.password");
		String driver = configMap.get("datasource.driver-class-name");
		
		String ftl = configMap.get("freemark.ftl");
		String projectAbsolutePath = configMap.get("freemark.project_absolutePath");
		String tablenameStr = configMap.get("freemark.tablename");
		String tablename_prefix = configMap.get("freemark.tablename_prefix");
		String base_package = configMap.get("freemark.base_package");
		String entity_package = configMap.get("freemark.entity_package");
		String dao_package = configMap.get("freemark.dao_package");

		
		
		//系统配置信息
		entity_package=entity_package!=null&&entity_package.length()>1?entity_package:base_package+"entity";
		dao_package=dao_package!=null&&dao_package.length()>1?dao_package:dao_package+"dao";
		sysConfig=new SysConfig(server, username, password,driver, ftl,projectAbsolutePath,base_package,entity_package,dao_package);
		conn = JdbcTool.getConnection();
		
		
		String[] taleArry = tablenameStr.split(",");
		
		for (String tablename : taleArry) {
			//去掉前缀的对象
			String objName=null;
			if(!StringUtils.isNullOrEmpty(tablename_prefix)){
				objName=tablename.replace(tablename_prefix, "");
			}
			System.out.println(objName);
			
			String strtmp=StringTool.underlineToCamel(objName);
			objName=strtmp.substring(0,1).toLowerCase()+strtmp.substring(1,strtmp.length());
			//表信息
			tablePropertyList.add(new TableProperty(tablename,"表名",objName));
		}
		
		
		
		
	}

	
	//获取表列名
	public static void getColumnsByTable(Connection con, TableProperty tableProperty ) {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("show full columns from " + tableProperty.getTableName());
			System.out.println("【" + tableProperty.getTableName() + "】");
			List<ColumnProperty> columList = new ArrayList<ColumnProperty>();
			
			while (rs.next()) {
				String colname = rs.getString("Field");
				String colType = rs.getString("Type").split("\\(")[0].toUpperCase();
				String colComment = rs.getString("Comment");
				String tempType = StringTool.sqlType2JavaFullType(colType);
				String [] tempArry=tempType.split("_");
				String jdbcType = tempArry[0];
				String javaFullType = tempArry[1];
				String javaType =StringTool.fullType2JavaType(javaFullType);
				
				System.out.println(colname+"\t"+colType+"\t"+javaType+"\t"+javaFullType);
				
				ColumnProperty columnProperty = new ColumnProperty(colname,
						colType, colComment, StringTool.underlineToCamel(colname),jdbcType,
						javaType, javaFullType);
				columList.add(columnProperty);
			}
			rs.close();
			//解析模板内容
			mappingProcess(tableProperty,columList,null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void mappingProcess(TableProperty tableProperty,List<ColumnProperty> columList,String ftl) {
		Configuration configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setTemplateLoader(new ClassTemplateLoader(GenerateCode.class,sysConfig.getFtl()));
		try {
			
			
			Map<String, Object> context = new HashMap<String, Object>();

			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			context.put("currentTime", dateString);

			
			context.put("sysConfig", sysConfig);//系统文件配置
			context.put("tableProperty", tableProperty);//表属性
			context.put("columList", columList);//列属性
			context.put("primaryKey", columList.get(0).getJavaName());//默认主键
			
			
			StringWriter writer = new StringWriter();

			Template entityTemplate = configuration.getTemplate("entity_bean.ftl");
			entityTemplate.process(context, writer);
			createFile("entity",tableProperty.getObjName(),writer.toString());//生产javabean文件


			Template xmlTemplate = configuration.getTemplate("mapper_xml.ftl");
			writer = new StringWriter();
			xmlTemplate.process(context, writer);
			createFile("xml",tableProperty.getObjName(),writer.toString());//生产xml文件
			
			
			Template controllerTemplate = configuration.getTemplate("entity_Controller.ftl");
			writer = new StringWriter();
			controllerTemplate.process(context, writer);
			createFile("controller",tableProperty.getObjName(),writer.toString());//生产Controller文件
			
			Template serviceImpTemplate = configuration.getTemplate("entity_ServiceImpl.ftl");
			writer = new StringWriter();
			serviceImpTemplate.process(context, writer);
			createFile("serviceImpl",tableProperty.getObjName(),writer.toString());//生产service文件
		
			Template htmlTemplate = configuration.getTemplate("entity_html.ftl");
			writer = new StringWriter();
			htmlTemplate.process(context, writer);
			createFile("html",tableProperty.getObjName(),writer.toString());//生产html文件
			
			
			Template jsTemplate = configuration.getTemplate("entity_js.ftl");
			writer = new StringWriter();
			jsTemplate.process(context, writer);
			createFile("js",tableProperty.getObjName(),writer.toString());//生产js文件
			//System.out.println(writer.toString());
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	

	public static void createFile(String fileType,String entityName,String content) {
		 try {  
		String projectAbsolutePath=sysConfig.getProjectAbsolutePath();
       	String currentFileDir=projectAbsolutePath.endsWith("/")?projectAbsolutePath:(projectAbsolutePath+"/")+sysConfig.getEntityPackage().replace(".", "/")+"/";
       	File fileDir = new File(currentFileDir);
   		if (!fileDir.exists()) {
   			fileDir.mkdirs();
   		}
   		String sufix= ".txt";
   		String objName=entityName.substring(0,1).toUpperCase()+entityName.substring(1,entityName.length());
   		String outputFilePath =currentFileDir+objName+sufix;
   		switch (fileType) {
		case "entity":
			sufix= objName+".java";
			break;
		case "xml":
			sufix= objName+"Mapper.xml";
			break;
		case "controller":
			sufix= objName+"Controller.java";
			break;
		case "serviceImpl":
			sufix= objName+"ServiceImpl.java";
			break;
		case "html":
			sufix= entityName+".html";
			break;
		case "js":
			sufix= entityName+".js";
			break;
		default:
			sufix= ".txt";
			break;
		}
   		outputFilePath =currentFileDir+sufix;
   		System.out.println(outputFilePath);
   		
   		File f = new File(outputFilePath);  
           if (f.exists()) {  
               System.out.println("文件存在"+outputFilePath);  
           } else {  
               System.out.println("文件不存在"+outputFilePath);  
               f.createNewFile();// 不存在则创建  
               System.out.println("文件创建成功"+outputFilePath); 
           } 
   		BufferedWriter output = new BufferedWriter(new FileWriter(f));  
           output.write(content);  
           output.close(); 
       } catch (IOException e) {
           e.printStackTrace();  
       }  
	}
	
	
	public static void main(String[] args) {
		init();
		Connection con= JdbcTool.getConnection();
		for (TableProperty tableProperty : tablePropertyList) {
			getColumnsByTable(con,tableProperty);
		}
		
		
	}

}
