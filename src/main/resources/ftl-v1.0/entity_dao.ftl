package ${sysConfig.daoPackage};

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import ${sysConfig.entityPackage}.${tableProperty.objName?cap_first};

/**
 * @Title: ${tableProperty.objName?cap_first}Mapper.java 
 * @Package ${sysConfig.entityPackage}
 * @Description: ${tableProperty.tableComment}
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         Bamboo</a>   
 * @date ${currentTime?datetime('yyyy-MM-dd hh:mm:ss')}
 * @version V1.0   
 */
@Mapper
public interface ${tableProperty.objName?cap_first}Mapper {

    /**
	 * 增加对象
	 * @param obj
	 */
	public void add(${tableProperty.objName?cap_first} obj);

	/**
	 * 修改对象
	 * @param obj
	 */
	public int update(${tableProperty.objName?cap_first} obj);

	/**
	 * 根据主键删除对象
	 * @param pk
	 */
	public int delete(Long pk);

	/**
	 * 根据主键得到某个对象
	 * @param pk
	 */
	public ${tableProperty.objName?cap_first} get(Long pk);

	/**
	 * 根据一组主键（数组），得到多个对象，以列表形式返回
	 * @param pks
	 * @return
	 */
	public List<${tableProperty.objName?cap_first}> getByIds(Long[] pks);


     /**
     * 根据条件查询数据
     * @param obj
     * @return
     */
     List<${tableProperty.objName?cap_first}> list(${tableProperty.objName?cap_first} obj);
	
}
