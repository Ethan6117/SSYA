package com.empl.mgr.service;

import com.empl.mgr.support.JSONReturn;

public abstract interface PlanDetailsService {

	public abstract JSONReturn findPlanDetailsList(int page, String weekOfYear);
	public abstract JSONReturn findPlanDetailsCount(int page, String weekOfYear);
	/*public abstract JSONReturn saveProjectInfo(ProjectListDto dto, String acctName);
	public abstract JSONReturn modifyProjectInfo(ProjectListDto dto, String acctName);
	public abstract JSONReturn findProjectById(Integer proId);
	public abstract JSONReturn deleteProject(Integer proId);*/
}
