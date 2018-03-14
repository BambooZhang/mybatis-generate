package ${sysConfig.entityPackage};
import java.io.Serializable;



/**
 * @Title: ${tableProperty.objName?cap_first}.java 
 * @Package ${sysConfig.entityPackage}
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         Bamboo</a>   
 * @date ${currentTime?datetime('yyyy-MM-dd hh:mm:ss')}
 * @version V1.0   
 */
public class ${tableProperty.objName?cap_first}  implements Serializable {
	 /** serialVersionUID. */
 	private static final long serialVersionUID =1493049839167L;
 	
 	<#if columList?exists>  
        <#list columList as columnProperty>  
	private ${columnProperty.javaType} ${columnProperty.javaName};//${columnProperty.colComment}
		</#list>   
	</#if>
	
	
	/**
    * getting setting auto  generate
    */
	<#if columList?exists>  
        <#list columList as columnProperty> 
	public void set${columnProperty.javaName?cap_first} (${columnProperty.javaType} ${columnProperty.javaName}){
		this.${columnProperty.javaName}=${columnProperty.javaName};
	}
	
	public ${columnProperty.javaType} get${columnProperty.javaName?cap_first}(){
		return ${columnProperty.javaName};
	}
	
	
	
		</#list>   
	</#if>
	//generate toString method
	@Override
	public String toString (){
	<#if columList?exists>  
        <#list columList as columnProperty> 
        <#if columnProperty_index == 0>
		return "${tableProperty.objName?cap_first}[${columnProperty.javaName}="+${columnProperty.javaName}
		<#else>
		+",${columnProperty.javaName}="+${columnProperty.javaName}<#if !columnProperty_has_next>+"]";</#if>
     	</#if> 
		</#list>
	</#if>
	}
	
	
}
