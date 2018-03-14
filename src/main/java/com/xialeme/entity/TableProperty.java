package com.xialeme.entity;

/**
 *   
 * 
 * @Title: TableProperty.java 
 * @Package com.xialeme.entity 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         >Bamboo</a>   
 * @date 2017年4月25日 上午10:25:49 
 * @version V1.0   
 */

public class TableProperty {
	private String tableName; // 表名
	private String tableComment; // 表注释,类注释
	private String objName; // java对象名 默认首字母小写tableProperty.objName

	
	
	
	
	
	public TableProperty(String tableName, String tableComment, String objName) {
		super();
		this.tableName = tableName;
		this.tableComment = tableComment;
		this.objName = objName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	
	
	
	
}
