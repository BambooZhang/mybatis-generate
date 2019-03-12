package ${sysConfig.basePackage}.service.impl;

import com.yifu.common.entity.vo.PageSearchVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${sysConfig.daoPackage}.${tableProperty.objName?cap_first}Mapper;
import ${sysConfig.entityPackage}.${tableProperty.objName?cap_first};
import ${sysConfig.basePackage}.service.${tableProperty.objName?cap_first}Service;


/**
* ${tableProperty.tableComment} 业务逻辑实现类
* @author bamboo  <a href=
*     "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
*     Bamboo</a>   
* @date ${currentTime?datetime('yyyy-MM-dd hh:mm:ss')}
* @version V1.0   
*/
@Service
public class ${tableProperty.objName?cap_first}ServiceImpl implements ${tableProperty.objName?cap_first}Service {

	/**
	 * 持久层对象
	 */
 	@Autowired
 	protected ${tableProperty.objName?cap_first}Mapper ${tableProperty.objName}Mapper;
 
	/**
	 * 分页
	 */
	@Override
    public PageInfo<${tableProperty.objName?cap_first}> page(${tableProperty.objName?cap_first} newsCategory, PageSearchVo pageSearchVo) {
		PageHelper.startPage(pageSearchVo.getPage().intValue() + 1, pageSearchVo.getSize());
		List<${tableProperty.objName?cap_first}> newss = ${tableProperty.objName}Mapper.list(newsCategory);
		PageInfo pageInfo = new PageInfo(newss);
		pageInfo.setPageNum(pageSearchVo.getPage().intValue());
        return pageInfo;
    }

	/**
	 * 新增
	 */
    @Override 
    public void add(${tableProperty.objName?cap_first} entity) {
        ${tableProperty.objName}Mapper.add(entity);
    }

	/****
	* 删除方法
	*/
    @Override
    public void del(Long id) {
        ${tableProperty.objName}Mapper.delete(id);
    }

	/**
	 * 修改
	 */
    @Override
    public void update(${tableProperty.objName?cap_first} entity) {
        ${tableProperty.objName}Mapper.update(entity);
    }

	/**
	 * 详情
	 */
    @Override
    public ${tableProperty.objName?cap_first} get(Long id) {
        ${tableProperty.objName?cap_first} news = ${tableProperty.objName}Mapper.get(id);
        return news;
    }

	
}
