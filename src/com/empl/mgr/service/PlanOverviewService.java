package com.empl.mgr.service;

import com.empl.mgr.support.JSONReturn;

public interface PlanOverviewService {
	public abstract JSONReturn findPlanOverviewList(int page,String year);
	public abstract JSONReturn findPlanOverviewCount(int page,String year);
}
