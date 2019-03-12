package com.xialeme;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.StringUtils;
import com.xialeme.entity.ColumnProperty;
import com.xialeme.entity.SysConfig;
import com.xialeme.entity.TableProperty;
import com.xialeme.utlis.JdbcTool;
import com.xialeme.utlis.StringTool;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 *   
 * 
 * @Title: Main.java 
 * @Package com.xialeme.main 
 * @Description: 根据Mysql表生成java代码：
 * 0.生成mapping,dao,bean,servcice,vue.js
 * 1.支持多个表生成源码，
 * 2.INT(>11)映射成Long类型
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         >Bamboo</a>   
 * @since  2017年4月25日 下午2:53:46 
 * 		2019年3月12日 下午2:53:46 
 * @version V1.1   
 */

public class GenerateCode {
	static SysConfig sysConfig;
	static Connection conn;
	static List<TableProperty> tablePropertyList=new ArrayList<TableProperty>();//表名
	/***
	 * 资源配置初始化
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
		//产生包名等配置信息
		entity_package=entity_package!=null&&entity_package.length()>1?entity_package:base_package+"entity";
		dao_package=dao_package!=null&&dao_package.length()>1?dao_package:dao_package+"dao";
		sysConfig=new SysConfig(server, username, password,driver, ftl,projectAbsolutePath,base_package,entity_package,dao_package);
		conn = JdbcTool.getConnection();
		
		
		String[] taleArry = tablenameStr.split(",");
		
		for (String tablename : taleArry) {
			//去掉前缀的对象
			String objName=tablename;
			if(!StringUtils.isNullOrEmpty(tablename_prefix)){
				objName=tablename.replace(tablename_prefix, "");
			}
			System.out.println(objName);
			
			String strtmp=StringTool.underlineToCamel(objName);
			objName=strtmp.substring(0,1).toLowerCase()+strtmp.substring(1,strtmp.length());//类名

			//查询表名的注释信息
			String tableComment="";
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("show table status WHERE name= '" + tablename+"'");
				if (rs != null && rs.next()) {
					tableComment = rs.getString("Comment");
					//String comment = parse(createDDL);
				}
				rs.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//表信息
			tablePropertyList.add(new TableProperty(tablename,tableComment,objName));
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
				String [] colTemp = rs.getString("Type").split("\\(");
				String colType = colTemp[0].toUpperCase();
				Integer colLength = null;
				if(colTemp.length>1){
					colLength = Integer.parseInt(colTemp[1].replaceAll("\\) *\\w*",""));
				}

				String colComment = rs.getString("Comment");
				String tempType = StringTool.sqlType2JavaFullType(colType,colLength);
				String [] tempArry=tempType.split("_");
				String jdbcType = tempArry[0];
				String javaFullType = tempArry[1];
				String javaType =StringTool.fullType2JavaType(javaFullType);
				
				System.out.println(colname+"\t"+colType+"\t"+javaType+"\t"+javaFullType);
				
				ColumnProperty columnProperty = new ColumnProperty(colname,
						colType,colLength, colComment, StringTool.underlineToCamel(colname),jdbcType,
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

	//映射关键字段到源码模板中
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


			Template daoTemplate = configuration.getTemplate("entity_dao.ftl");
			writer = new StringWriter();
			daoTemplate.process(context, writer);
			createFile("dao",tableProperty.getObjName(),writer.toString());//dao
			
			
			Template controllerTemplate = configuration.getTemplate("entity_Controller.ftl");
			writer = new StringWriter();
			controllerTemplate.process(context, writer);
			createFile("controller",tableProperty.getObjName(),writer.toString());//生产Controller文件

			Template serviceTemplate = configuration.getTemplate("entity_Service.ftl");
			writer = new StringWriter();
			serviceTemplate.process(context, writer);
			createFile("service",tableProperty.getObjName(),writer.toString());//生产service文件

			Template serviceImpTemplate = configuration.getTemplate("entity_ServiceImpl.ftl");
			writer = new StringWriter();
			serviceImpTemplate.process(context, writer);
			createFile("serviceImpl",tableProperty.getObjName(),writer.toString());//生产serviceImpl文件
		
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
	
	
	
	

	//创建文件
	public static void createFile(String fileType,String entityName,String content) {
		 try {  
		String projectAbsolutePath=sysConfig.getProjectAbsolutePath();
       	StringBuffer currentFileDir= new StringBuffer();
			 currentFileDir.append(projectAbsolutePath.endsWith("/")?projectAbsolutePath:(projectAbsolutePath+"/"));
			 currentFileDir.append(sysConfig.getBasePackage().replace(".", "/")+"/");
   		String sufix= ".txt" ;
   		String objName=entityName.substring(0,1).toUpperCase()+entityName.substring(1,entityName.length());
   		switch (fileType) {
		case "entity":
			currentFileDir.append("entity/");
			sufix= objName+".java";
			break;
		case "xml":
			currentFileDir.append("xml/");
			sufix= objName+"Mapper.xml";
			break;
		case "dao":
			currentFileDir.append("mapper/");
			sufix= objName+"Mapper.java";
			break;
		case "controller":
			currentFileDir.append("controller/");
			sufix= objName+"Controller.java";
			break;
		case "service":
			currentFileDir.append("service/");
			sufix= objName+"Service.java";
			break;
		case "serviceImpl":
			currentFileDir.append("service/impl/");
			sufix= objName+"ServiceImpl.java";
			break;
		case "html":
			currentFileDir.append("html/");
			sufix= entityName+".html";
			break;
		case "js":
			currentFileDir.append("js/");
			sufix= entityName+".js";
			break;
		default:
			sufix= ".txt";
			break;
		}
		 File fileDir = new File(currentFileDir.toString());
		 if (!fileDir.exists()) {
			 fileDir.mkdirs();
		 }
		String outputFilePath =currentFileDir+sufix;
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
			System.out.println(tableProperty.getTableComment());
			getColumnsByTable(con,tableProperty);
		}

	}

}
