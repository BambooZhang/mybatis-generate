package ${sysConfig.daoPackage};

import com.beske.b2bweb.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import ${sysConfig.entityPackage}.${tableProperty.objName?cap_first};

/**
 * @Title: ${tableProperty.objName?cap_first}Dao.java 
 * @Package ${sysConfig.entityPackage}
 * @Description: ${tableProperty.tableComment}
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         Bamboo</a>   
 * @date ${currentTime?datetime('yyyy-MM-dd hh:mm:ss')}
 * @version V1.0   
 */
@Mapper
public interface ${tableProperty.objName?cap_first}Dao extends BaseDao<${tableProperty.objName?cap_first},Long> {

	
}
