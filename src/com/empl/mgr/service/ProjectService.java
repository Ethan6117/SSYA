package com.empl.mgr.service;

import com.empl.mgr.dto.ProjectListDto;
import com.empl.mgr.support.JSONReturn;

public abstract interface ProjectService {

	public abstract JSONReturn findProjectList(int page, String searchValue,String state);
	public abstract JSONReturn findProjectCount(int page, String searchValue,String state);
	public abstract JSONReturn saveProjectInfo(ProjectListDto dto, String acctName);
	public abstract JSONReturn modifyProjectInfo(ProjectListDto dto, String acctName);
	public abstract JSONReturn findProjectById(Integer proId);
	public abstract JSONReturn deleteProject(Integer proId);
	/*public abstract JSONReturn saveSpecialDesgin(Integer speId, String url,String imgdesc);
	public abstract List<TeSpecialDesign> findSpecialDesignsById(Integer speId);*/
}
