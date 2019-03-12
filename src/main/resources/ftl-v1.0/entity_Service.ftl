package ${sysConfig.basePackage}.service;


import com.yifu.common.entity.vo.PageSearchVo;
import com.github.pagehelper.PageInfo;

import ${sysConfig.entityPackage}.${tableProperty.objName?cap_first};


/**
* ${tableProperty.tableComment}Service
* @author bamboo  <a href=
*     "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
*     Bamboo</a>   
* @date ${currentTime?datetime('yyyy-MM-dd hh:mm:ss')}
* @version V1.0   
*/
public interface ${tableProperty.objName?cap_first}Service {

    PageInfo<${tableProperty.objName?cap_first}> page(${tableProperty.objName?cap_first} entity, PageSearchVo pageSearchVo);

    void add(${tableProperty.objName?cap_first} entity);

    void del(Long id);

    void update(${tableProperty.objName?cap_first} entity);

    ${tableProperty.objName?cap_first} get(Long id);

}
