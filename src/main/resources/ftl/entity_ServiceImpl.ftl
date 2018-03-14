package ${sysConfig.basePackage}.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.xialeme.common.core.constan.Constant;
import com.xialeme.common.core.entity.PageFinder;
import com.xialeme.common.core.entity.Query;
import com.xialeme.common.core.result.ResultInfo;
import ${sysConfig.basePackage}.dao.${tableProperty.objName?cap_first}Dao;
import ${sysConfig.basePackage}.entity.${tableProperty.objName?cap_first};
import ${sysConfig.basePackage}.service.${tableProperty.objName?cap_first}Service;



@Service
public class ${tableProperty.objName?cap_first}ServiceImpl implements ${tableProperty.objName?cap_first}Service {

	/**
	 * 持久层对象
	 */
 	@Resource
 	protected ${tableProperty.objName?cap_first}Dao dao;
 	private ResultInfo resultInfo = new ResultInfo();
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public ResultInfo get(Long id) {
		// TODO Auto-generated method stub
		try{
			${tableProperty.objName?cap_first} entity=dao.get(id);
			resultInfo.setCode(Constant.SUCCESS);
			resultInfo.setMsg("获取数据详情成功");
			resultInfo.setData(entity);
		} catch (Exception e) {
			logger.error(e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}
	
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public ResultInfo getList(${tableProperty.objName?cap_first}  entity) {
		try{
			List<${tableProperty.objName?cap_first}> entityList =dao.getList(entity);
			resultInfo.setCode(Constant.SUCCESS);
			resultInfo.setMsg("获取数据列表成功");
			resultInfo.setData(entityList);
		} catch (Exception e) {
			logger.error(e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
		
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public ResultInfo getPage(Query q) {
		try{
			PageFinder<${tableProperty.objName?cap_first}> page = new PageFinder<${tableProperty.objName?cap_first}>();
	
			List<${tableProperty.objName?cap_first}> list = null;
			long rowCount = 0L;
			// 调用dao查询满足条件的分页数据
			list = dao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = dao.count(q);
			// 如list为null时，则改为返回一个空列表
			list = list == null ? new ArrayList<${tableProperty.objName?cap_first}>(0) : list;
	
			// 将分页数据和记录总数设置到分页结果对象中
			page.setData(list);
			page.setPageParam(q,rowCount);
		
			resultInfo.setCode(Constant.SUCCESS);
			resultInfo.setMsg("获取数据分页列表成功");
			
			resultInfo.setData(page);
		} catch (Exception e) {
			logger.error(e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public ResultInfo saveOrUpdate(${tableProperty.objName?cap_first}  entity) {
		
		try{
			if (entity.get${primaryKey?cap_first}()==null){
				entity.setCreateTime(new Date());
				entity.setUpdateTime(new Date());
				dao.add(entity);
				resultInfo.setMsg("新增数据成功");
			}else{
				entity.setUpdateTime(new Date());
				dao.update(entity);
				resultInfo.setMsg("更新数据成功");
			}
		
			resultInfo.setCode(Constant.SUCCESS);
			resultInfo.setData(entity);
		} catch (Exception e) {
			logger.error(e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}

	
	/****
	 * 删除方法
	 */
 	public ResultInfo delete(Long id) {
		// TODO Auto-generated method stub
		try{
	 		${tableProperty.objName?cap_first} entity=dao.get(id);
	 		entity.setIsDeleted(1);
			dao.update(entity);
			resultInfo.setCode(Constant.SUCCESS);
			resultInfo.setMsg("删除数据成功");
			resultInfo.setData(entity);
	 	} catch (Exception e) {
	 		logger.error(e.toString());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getMessage());
		}
		return resultInfo;
	}
	
	
	
}
