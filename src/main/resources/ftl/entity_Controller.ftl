package ${sysConfig.basePackage}.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xialeme.common.core.entity.PageFinder;
import com.xialeme.common.core.entity.Query;
import com.xialeme.common.core.result.ResultInfo;
import ${sysConfig.basePackage}.entity.${tableProperty.objName?cap_first};
import ${sysConfig.basePackage}.service.${tableProperty.objName?cap_first}Service;



/**
 * @Title: ${tableProperty.objName?cap_first}Controller.java 
 * @Package ${sysConfig.entityPackage}.controller
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         Bamboo</a>   
 * @date ${currentTime?datetime('yyyy-MM-dd hh:mm:ss')}
 * @version V1.0   
 */
@Controller
@RequestMapping("/${sysConfig.moduleName}/${tableProperty.objName}")
public class ${tableProperty.objName?cap_first}Controller {

	@Resource
	private ${tableProperty.objName?cap_first}Service ${tableProperty.objName}Service;

	
	
	/*********
	 * 视图
	 * @return
	 */
	//@RequiresPermissions("${sysConfig.moduleName}:${tableProperty.objName}:view")
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	Object toView () {
		return "models/${sysConfig.moduleName}/${tableProperty.objName}";
	}
	

	/*********
	 * 添加
	 * @return
	 */
	//@RequiresPermissions("${sysConfig.moduleName}:${tableProperty.objName}:add")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	ResultInfo add${tableProperty.objName?cap_first}(@ModelAttribute("${tableProperty.objName} ") ${tableProperty.objName?cap_first} ${tableProperty.objName} ) {
		ResultInfo resultInfo = new ResultInfo();
		if (${tableProperty.objName} == null) {
			resultInfo = new ResultInfo(0, "数据不能为空", null);
			return resultInfo;
		}
		
		${tableProperty.objName}Service.saveOrUpdate(${tableProperty.objName});
		resultInfo = new ResultInfo(1, "添加数据成功", null);
		return resultInfo;
	}
	
	/*********
	 * 修改
	 * @return
	 */
	//@RequiresPermissions("${sysConfig.moduleName}:${tableProperty.objName}:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	ResultInfo put${tableProperty.objName?cap_first}(@ModelAttribute("${tableProperty.objName} ") ${tableProperty.objName?cap_first} ${tableProperty.objName} ) {
		ResultInfo resultInfo = new ResultInfo();
		/*if (${tableProperty.objName} == null || ${tableProperty.objName}.getId()==null) {
			resultInfo = new ResultInfo(0, "数据和ID不能为空", null);
			return resultInfo;
		}*/
		
		${tableProperty.objName}Service.saveOrUpdate(${tableProperty.objName});
		resultInfo = new ResultInfo(1, "修改数据成功", ${tableProperty.objName});
		return resultInfo;
	}
	
	/*********
	 * 详情
	 * @return
	 */
	//@RequiresPermissions("${sysConfig.moduleName}:${tableProperty.objName}:detail")
	@RequestMapping(value = "/{keyId}", method = RequestMethod.GET)
	@ResponseBody
	ResultInfo get${tableProperty.objName?cap_first}(@PathVariable Long keyId ) {
		ResultInfo resultInfo = new ResultInfo();
		if (keyId == null || StringUtils.isEmpty(keyId)) {
			resultInfo = new ResultInfo(0, "数据ID不能为空", null);
			return resultInfo;
		}
		
		
		resultInfo =${tableProperty.objName}Service.get(keyId);
		//resultInfo = new ResultInfo(1, "获取该数据成功", ${tableProperty.objName});
		return resultInfo;
	}
	
	/*********
	 * 删除
	 * @return
	 */
	//@RequiresPermissions("${sysConfig.moduleName}:${tableProperty.objName}:delete")
	@RequestMapping(value = "/{keyId}", method = RequestMethod.DELETE)
	@ResponseBody
	ResultInfo delete${tableProperty.objName?cap_first}(@PathVariable Long keyId ) {
		ResultInfo resultInfo = new ResultInfo();
		if (keyId == null || StringUtils.isEmpty(keyId)) {
			resultInfo = new ResultInfo(0, "数据ID不能为空", null);
			return resultInfo;
		}
		${tableProperty.objName}Service.delete(keyId);
		resultInfo = new ResultInfo(1, "删除该数据成功", null);
		return resultInfo;
	}
	
	
	/*********
	 * 分页
	 * @return
	 */
	//@RequiresPermissions("${sysConfig.moduleName}:${tableProperty.objName}:pageList")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	ResultInfo page${tableProperty.objName?cap_first}(Query q,${tableProperty.objName?cap_first} ${tableProperty.objName}){
		ResultInfo resultInfo = new ResultInfo();
		
		//当为0时默认值分页为10
		Query query = new Query(q.getPageNo(), q.getPageSize(), ${tableProperty.objName});
		resultInfo = ${tableProperty.objName}Service.getPage(query);
		
		return resultInfo;
	}
	


}
