package com.xialeme.entity;


/**   
 * @Title: Config.java 
 * @Package com.xialeme.entity 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href;//"mailto:zjcjava@163.com?subject;//hello,bamboo&body;//Dear Bamboo:%0d%0a描述你的问题：">Bamboo</a>   
 * @date 2017年4月25日 下午3:31:01 
 * @version V1.0   
 */

public class SysConfig {
	String server ;// props.getProperty("datasource.url");
	String username ;// props.getProperty("datasource.username");
	String password ;// props.getProperty("datasource.password");
	String driver ;// props.getProperty("datasource.driver-class-name");
	String ftl ;// props.getProperty("datasource.driver-class-name");
	String projectAbsolutePath ;// 项目src路径
	String basePackage ;// jabva base  Package
	String entityPackage ;// jabvabena Package
	String daoPackage; // java daoPackage路径
	String controllerPackage; // java  路径
	String xmlPackage; // xml  路径
	String servicePackage; // service 路径
	String jsPackage; // js 路径



	String moduleName ;//模块名称根据basePackage最后一个字母获取
	
	
	
	
	public SysConfig(String server, String username, String password,
			String driver, String ftl, String projectAbsolutePath,String basePackage,
			String entityPackage,String daoPackage) {
		super();
		this.server = server;
		this.username = username;
		this.password = password;
		this.driver = driver;
		this.ftl = ftl;
		this.projectAbsolutePath = projectAbsolutePath;
		this.basePackage = basePackage;
		this.entityPackage = entityPackage;
		this.daoPackage = daoPackage;
		String [] baseArry= this.basePackage.split("\\.");
		this.moduleName = baseArry[baseArry.length-1];
	}
	public SysConfig(String server, String username, String password, String driver) {
		this.server = server;
		this.username = username;
		this.password = password;
		this.driver = driver;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getFtl() {
		return ftl;
	}
	public void setFtl(String ftl) {
		this.ftl = ftl;
	}
	public String getProjectAbsolutePath() {
		return projectAbsolutePath;
	}
	public void setProjectAbsolutePath(String projectAbsolutePath) {
		this.projectAbsolutePath = projectAbsolutePath;
	}
	
	public String getBasePackage() {
		return basePackage;
	}
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	public String getEntityPackage() {
		return entityPackage;
	}
	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}
	public String getDaoPackage() {
		return daoPackage;
	}
	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	
	
	
	
	
	
	
	
	
	
}


