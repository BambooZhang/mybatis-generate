package com.xialeme.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *   
 * 
 * @Title: CloumProperty.java 
 * @Package com.xialeme.entity 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         Bamboo</a   
 * @date 2017年4月25日 上午10:03:18 
 * @version V1.0   
 */

public class ColumnProperty {

	private String colName; // 列名
	private String colType; // 列名类型
	private Integer colLength; // 列名类型长度
	private String colComment; // 列注释,属性注释
	private String javaName; // java属性名
	private String jdbcType; // jdbcType mybatis 数据类型
	private String javaType; // java类型缩写:String
	private String javaFullType; // java类型全路径:java.lang.String

	public ColumnProperty(String colName, String colType, Integer colLength,String colComment,
			String javaName, String jdbcType, String javaType, String javaFullType) {
		super();
		this.colName = colName;
		this.colType = colType;
		this.colLength = colLength;
		this.colComment = colComment;
		this.javaName = javaName;
		this.jdbcType = jdbcType;
		this.javaType = javaType;
		this.javaFullType = javaFullType;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public String getColComment() {
		return colComment;
	}

	public void setColComment(String colComment) {
		this.colComment = colComment;
	}

	public String getJavaName() {
		return javaName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaFullType() {
		return javaFullType;
	}

	public void setJavaFullType(String javaFullType) {
		this.javaFullType = javaFullType;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public Integer getColLength() {
		return colLength;
	}

	public ColumnProperty setColLength(Integer colLength) {
		this.colLength = colLength;
		return this;
	}
}
