package com.empl.mgr.service.impl;

import java.util.List;


import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.PlanDetailsDao;
import com.empl.mgr.dto.PlanDetailsListDto;
import com.empl.mgr.service.PlanDetailsService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.utils.PageUtils;

@Scope
@Service
@Transactional(readOnly = true)
public class PlanDetailsServiceImpl implements PlanDetailsService {

	@Autowired
	private PlanDetailsDao planDetailsDao;


	public JSONReturn findPlanDetailsList(int page, String weekOfDay) {
		// TODO Auto-generated method stub
		List<PlanDetailsListDto> dtoList = planDetailsDao.findPlanDetailsList(page, weekOfDay);
		if (CollectionUtils.isEmpty(dtoList))
			return JSONReturn.buildFailure("未获取到数据!");
		for (PlanDetailsListDto dto : dtoList) {
			dto.setStrState(dto.setStateToStr(dto.getState()));
		}
		return JSONReturn.buildSuccess(dtoList);
	}
	public JSONReturn findPlanDetailsCount(int page, String weekOfDay) {
		// TODO Auto-generated method stub
		int count = planDetailsDao.findPlanDetailsCount(weekOfDay);
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, count, PageConstant.PAGE_LIST));
	}
	
	/*@Transactional
	public JSONReturn saveProjectInfo(ProjectListDto dto, String acctName) {
		// TODO Auto-generated method stub
		if (CompareUtil.isEmpty(dto))
			return JSONReturn.buildFailure("项目保存失败!");
		TeProject project=new TeProject();
		project.setProjectName(dto.getProjectName());
		project.setClient(dto.getClient());
		project.setPartner(dto.getPartner());
		project.setLinkman(dto.getLinkman());
		project.setPhone(dto.getPhone());
		project.setManager(dto.getManager());
		project.setParticipants(dto.getParticipants());
		project.setDomainName(dto.getDomainName());
		project.setServerAndLogin(dto.getserverAndLogin());
		project.setPath(dto.getPath());
		project.setSummary(dto.getSummary());
		project.setBoss(dto.getBoss());
		project.setState(dto.getState());
		project.setAddTime(DateTimeUtil.getCurrentTime());
		project.setUpdateTime(DateTimeUtil.getCurrentTime());
		project.setDeleteTag(0);
		projectDao.save(project);
		return JSONReturn.buildSuccess("添加成功!");
	}
	
	@Transactional
	public JSONReturn modifyProjectInfo(ProjectListDto dto, String acctName) {
		if (CompareUtil.isEmpty(dto))
			return JSONReturn.buildFailure("项目保存失败!");
		TeProject project=projectDao.findUniqueByProperty("id", dto.getId());
		project.setId(dto.getId());
		project.setProjectName(dto.getProjectName());
		project.setClient(dto.getClient());
		project.setPartner(dto.getPartner());
		project.setLinkman(dto.getLinkman());
		project.setPhone(dto.getPhone());
		project.setManager(dto.getManager());
		project.setParticipants(dto.getParticipants());
		project.setDomainName(dto.getDomainName());
		project.setPath(dto.getPath());
		project.setServerAndLogin(dto.getserverAndLogin());
		project.setSummary(dto.getSummary());
		project.setBoss(dto.getBoss());
		project.setState(dto.getState());
		project.setUpdateTime(DateTimeUtil.getCurrentTime());
		projectDao.save(project);
		return JSONReturn.buildSuccess("修改成功");
	}
	
	public JSONReturn findProjectById(Integer proId) {
		TeProject project = projectDao.findUniqueByProperty("id", proId);
		if (CompareUtil.isEmpty(project))
			return JSONReturn.buildFailure("获取源数据失败!");
		return JSONReturn.buildSuccess(project);
	}
	
	
	
	@Transactional
	public JSONReturn deleteProject(Integer proId) {
		projectDao.deleteProject(proId);
		return JSONReturn.buildSuccess("删除成功!");
	}*/
}
