<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${sysConfig.daoPackage}.${tableProperty.objName?cap_first}Mapper">
	<!--  set mybatis auto mapping datatbase tale's colum name-->

<!-- 全部字段，一般用于明细查询 -->
<sql id="AllColumnlist">
 <#list columList as columnProperty > <#if columnProperty_index == 0> <#assign java_key_id = "${columnProperty.javaName}" /> </#if> a.${columnProperty.colName}<#if columnProperty_has_next>,</#if></#list>
</sql>

<!-- 查询条件 -->
<sql id="AllColumnWhere">
<#list columList as columnProperty >
	<if test="${columnProperty.javaName}!=null  ">
	AND a.${columnProperty.colName}=${r'#'}${r'{'}${columnProperty.javaName}${r'}'}
	</if>
</#list>
</sql>



<!-- 添加数据,默认列名第一个为主键 useGeneratedKeys="true" keyProperty="activityId"-->
<insert id="add" parameterType="${sysConfig.entityPackage}.${tableProperty.objName?cap_first}"  useGeneratedKeys="true" keyProperty="${java_key_id}">
	insert into ${tableProperty.tableName} (
	 <trim suffix="" suffixOverrides=",">
	<#list columList as columnProperty >
		<if test="${columnProperty.javaName}!=null <#if columnProperty.javaType =='String'> and ${columnProperty.javaName} !='' </#if>">
		${columnProperty.colName}<#if columnProperty_has_next>,</#if>
		</if>
	</#list>
	 </trim>
	) values (
	 <trim suffix="" suffixOverrides=",">
	<#list columList as columnProperty >
		<if test="${columnProperty.javaName}!=null <#if columnProperty.javaType =='String'> and ${columnProperty.javaName} !='' </#if>">
		${r'#'}${r'{'}${columnProperty.javaName},jdbcType = ${columnProperty.jdbcType} ${r'}'}<#if columnProperty_has_next>,</#if>
		</if>
	</#list>
	 </trim>
	)
</insert>


<!--更新数据-->
<update id="update" parameterType="${sysConfig.entityPackage}.${tableProperty.objName?cap_first}">
	update ${tableProperty.tableName} 
	<trim prefix="set" suffixOverrides=",">
	<#list columList as columnProperty >
		<#if columnProperty_index == 0><#assign table_key_id = "${columnProperty.colName}" /><#assign java_key_id = "${columnProperty.javaName}" />
		<#else>
		<if test="${columnProperty.javaName}!=null <#if columnProperty.javaType =='String'> and ${columnProperty.javaName} !='' </#if>">
			${columnProperty.colName}=${r'#'}${r'{'}${columnProperty.javaName},jdbcType = ${columnProperty.jdbcType} ${r'}'},
		</if>
     	</#if>
		
	</#list>
	</trim>
	<where>
			${table_key_id}=${r'#'}${r'{'}${java_key_id}${r'}'}
	</where>
</update>

<!--根据主键获取数据-->
<select id="get" resultType="${sysConfig.entityPackage}.${tableProperty.objName?cap_first}">
	select <include refid="AllColumnlist"/> from ${tableProperty.tableName} a
	<where>
		a.${table_key_id}=${r'#'}${r'{'}0${r'}'}
	</where>
</select>

<!--根据多个ID获取数据-->
<select id="getByIds" resultType="${sysConfig.entityPackage}.${tableProperty.objName?cap_first}">
	select <include refid="AllColumnlist"/> from ${tableProperty.tableName} a
	<where>
		a.${table_key_id} in <foreach item="item" index="index" collection="array" open="(" separator="," close=")">${r'#'}${r'{'}item${r'}'}</foreach>
	</where>
</select>


<!-- 删除记录 -->
<delete id="delete">
	delete from ${tableProperty.tableName}
	<where>
		${table_key_id}=${r'#'}${r'{'}0${r'}'}
	</where>
</delete>


<!-- 列表 分页使用 mybatis-help -->
<select id="list" parameterType="${sysConfig.entityPackage}.${tableProperty.objName?cap_first}"  resultType="${sysConfig.entityPackage}.${tableProperty.objName?cap_first}">
	select <include refid="AllColumnlist"/>  from ${tableProperty.tableName} a
	<where>
		<include refid="AllColumnWhere" />
	</where>
</select>





</mapper>