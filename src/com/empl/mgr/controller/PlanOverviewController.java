package com.empl.mgr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empl.mgr.annotation.SecureValid;
import com.empl.mgr.constant.MethodType;
import com.empl.mgr.controller.support.AbstractController;
import com.empl.mgr.service.PlanOverviewService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "plan")
public class PlanOverviewController extends AbstractController {
	@Autowired
	private PlanOverviewService planOverviewService;
	
	@ResponseBody
	@RequestMapping(value = "findPlanOverviewList")
	@SecureValid(code = "05002", desc = "获取周计划列表", type = MethodType.FIND)
	public JSONReturn findPlanOverviewList(@RequestParam int page, @RequestParam String year, HttpSession httpSession) {
		return planOverviewService.findPlanOverviewList(page, year);
	}
	
	@ResponseBody
	@RequestMapping(value = "findPlanOverviewCount")
	@SecureValid(code = "05002", desc = "获取周计划分页", type = MethodType.FIND)
	public JSONReturn findPlanOverviewCount(@RequestParam int page, @RequestParam String year, HttpSession httpSession) {
		return planOverviewService.findPlanOverviewCount(page, year);
	}
}
