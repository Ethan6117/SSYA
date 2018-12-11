package com.empl.mgr.service;

import com.empl.mgr.dto.PlanListDto;
import com.empl.mgr.support.JSONReturn;

public interface PlanService {
	
	public abstract JSONReturn findPlanList(int page, String date);
	public abstract JSONReturn findPlanCount(int page, String date);
	public abstract JSONReturn deletePlan(Integer pId);
	public abstract JSONReturn findPlanById(Integer pId, String date);
	public abstract JSONReturn updatePlan(PlanListDto dto, String date);
	public abstract JSONReturn savePlan(PlanListDto dto, String date);
	public abstract JSONReturn findProjectName();
}
