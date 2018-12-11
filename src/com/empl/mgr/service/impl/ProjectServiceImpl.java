package com.empl.mgr.service.impl;

import java.util.List;


import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.ProjectDao;
import com.empl.mgr.dto.ProjectListDto;
import com.empl.mgr.model.TeProject;
import com.empl.mgr.service.ProjectService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.utils.CompareUtil;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.PageUtils;

@Scope
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	/*@Autowired
	private AccountDao accountDao;
	@Autowired
	private SpecialScoreDao specialScoreDao;
	@Autowired
	private SpecialDesignDao specialDesignDao;*/

	public JSONReturn findProjectList(int page, String searchValue,String state) {
		// TODO Auto-generated method stub
		List<ProjectListDto> dtoList = projectDao.findProjectList(page, searchValue,state);
		if (CollectionUtils.isEmpty(dtoList))
			return JSONReturn.buildFailure("未获取到数据!");
		for (ProjectListDto dto : dtoList) {
			dto.setStrState(dto.setStateToStr(dto.getState()));
		}
		return JSONReturn.buildSuccess(dtoList);
	}
	public JSONReturn findProjectCount(int page, String searchValue,String state) {
		// TODO Auto-generated method stub
		int count = projectDao.findProjectCount(searchValue, state);
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, count, PageConstant.PAGE_LIST));
	}
	
	@Transactional
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
		project.setServerAndLogin(dto.getServerAndLogin());
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
		project.setServerAndLogin(dto.getServerAndLogin());
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
		ProjectListDto dto=new ProjectListDto();
		dto.setProjectName(project.getProjectName());
		dto.setClient(project.getClient());
		dto.setPartner(project.getPartner());
		dto.setLinkman(project.getLinkman());
		dto.setPhone(project.getPhone());
		dto.setManager(project.getManager());
		dto.setParticipants(project.getParticipants());
		dto.setDomainName(project.getDomainName());
		dto.setPath(project.getPath());
		dto.setServerAndLogin(project.getServerAndLogin());
		dto.setSummary(project.getSummary());
		dto.setBoss(project.getBoss());
		dto.setState(project.getState());
		dto.setStrState(dto.setStateToStr(project.getState()));
		return JSONReturn.buildSuccess(dto);
	}
	
	
	
	@Transactional
	public JSONReturn deleteProject(Integer proId) {
		projectDao.deleteProject(proId);
		return JSONReturn.buildSuccess("删除成功!");
	}
	
	/*@Transactional
	public JSONReturn saveSpecialDesgin(Integer speId, String url,String imgdesc){
		TeSpecialDesign specialDesign=new TeSpecialDesign();
		specialDesign.setSpeId(speId);
		specialDesign.setDesignUrl(url);
		specialDesign.setImgdesc(imgdesc);
		specialDesignDao.save(specialDesign);
		return JSONReturn.buildSuccess("");
	}
	
	public List<TeSpecialDesign> findSpecialDesignsById(Integer speId) {
		return specialDesignDao.findByProperty("speId", speId);
	}*/
}
