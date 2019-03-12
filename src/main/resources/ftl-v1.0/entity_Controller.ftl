package ${sysConfig.basePackage}.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;


import com.yifu.common.constant.PublicCode;
import com.yifu.common.entity.vo.PageSearchVo;
import com.yifu.common.exception.YfException;
import com.yifu.common.http.response.MultiResponse2;

import ${sysConfig.entityPackage}.${tableProperty.objName?cap_first};
import ${sysConfig.basePackage}.service.${tableProperty.objName?cap_first}Service;



/**
 * @Title: ${tableProperty.objName?cap_first}Controller.java 
 * @Package ${sysConfig.entityPackage}.controller
 * @Description:  ${tableProperty.tableComment} controller
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         Bamboo</a>   
 * @date ${currentTime?datetime('yyyy-MM-dd hh:mm:ss')}
 * @version V1.0   
 */
@RestController
@RequestMapping("/admin/${sysConfig.moduleName}/${tableProperty.objName}")
public class ${tableProperty.objName?cap_first}Controller {

	@Autowired
	private ${tableProperty.objName?cap_first}Service ${tableProperty.objName}Service;


   /**
     * 分页
     * @param vo
     * @param page
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Object page(${tableProperty.objName?cap_first} vo, PageSearchVo page) {
        PageInfo<${tableProperty.objName?cap_first}> ades = ${tableProperty.objName}Service.page( vo,page);
        return MultiResponse2.builder().success().add("${tableProperty.objName}s", ades);
    }

    /**
    * 详情
    * @param id
    * @return
    */
    @GetMapping(value = "/{id}")
    public Object get(@PathVariable("id") Long id) {
        if (Objects.isNull(id))
            throw new YfException(PublicCode.params_empty_error.error());
        ${tableProperty.objName?cap_first} ad = ${tableProperty.objName}Service.get(id);
        return MultiResponse2.builder().success().addData("${tableProperty.objName}", ad);
    }

    /**
    *删除信息
    * @param id
    * @return
    */
    @DeleteMapping(value = "/{id}")
    public Object del(@PathVariable("id") Long id) {
        if (Objects.isNull(id))
              throw new YfException(PublicCode.params_empty_error.error());
        ${tableProperty.objName}Service.del(id);
        return MultiResponse2.builder().success();
    }

    /**
    * 修改数据
    * @param vo
    * @return
    */
    @PutMapping(value = "")
    public Object update(@RequestBody ${tableProperty.objName?cap_first} vo) {
        if (Objects.isNull(vo) || Objects.isNull(vo.getId()))
            throw new YfException(PublicCode.params_empty_error.error());
        ${tableProperty.objName}Service.update(vo);
        return MultiResponse2.builder().success();
    }

    /**
    * 新增数据
    * @param vo
    * @return
    */
    @PostMapping(value = "")
    public Object add(@RequestBody ${tableProperty.objName?cap_first} vo) {
        if (Objects.isNull(vo))
             throw new YfException(PublicCode.params_empty_error.error());
        ${tableProperty.objName}Service.add(vo);
        return MultiResponse2.builder().success();
    }






}
