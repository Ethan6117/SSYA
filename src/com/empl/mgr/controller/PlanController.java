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
import com.empl.mgr.dto.PlanListDto;
import com.empl.mgr.service.PlanService;
import com.empl.mgr.support.JSONReturn;

import net.sf.json.JSONObject;

@Scope
@Controller
@RequestMapping(value = "plan")
public class PlanController extends AbstractController {
	
	@Autowired
	private PlanService planService;
		
	@ResponseBody
	@RequestMapping(value = "findPlanList")
	@SecureValid(code = "05002", desc = "获取周计划进展页", type = MethodType.FIND)
	public JSONReturn findPlanList(@RequestParam int page, @RequestParam String date, HttpSession httpSession) {
			return planService.findPlanList(page, date);
		}
	
	@ResponseBody
	@RequestMapping(value = "findPlanCount")
	@SecureValid(code = "05002", desc = "获取周计划进展页分页", type = MethodType.FIND)
	public JSONReturn findPlanCount(@RequestParam int page, @RequestParam String date, HttpSession httpSession) {
		return planService.findPlanCount(page, date);
	}
	
	@ResponseBody
	@RequestMapping(value = "deletePlan")
	@SecureValid(code = "05002", desc = "删除周计划信息", type = MethodType.DELETE)
	public JSONReturn deletePlan(@RequestParam Integer pId, HttpSession httpSession) {
		return planService.deletePlan(pId);
	}
	
	@ResponseBody
	@RequestMapping(value = "findPlanInfo")
	@SecureValid(code = "05002", desc = "根据ID号,获取周计划信息", type = MethodType.FIND)
	public JSONReturn findPlanById(@RequestParam Integer pId, @RequestParam String date) {
		return planService.findPlanById(pId, date); 
	}
	
	@ResponseBody
	@RequestMapping(value = "updatePlan")
	@SecureValid(code = "05002", desc = "修改周计划信息", type = MethodType.MODIFY)
	public JSONReturn updatePlan(@RequestParam String data, @RequestParam String date, HttpSession httpSession) {
		PlanListDto dto = (PlanListDto) JSONObject.toBean(JSONObject.fromObject(data), PlanListDto.class);
		return planService.updatePlan(dto, date);
	}
	
	@ResponseBody
	@RequestMapping(value = "addPlan")
	@SecureValid(code = "05002", desc = "添加周计划信息", type = MethodType.ADD)
	public JSONReturn addPlan(@RequestParam String data, @RequestParam String date, HttpSession httpSession) {
		PlanListDto dto = (PlanListDto) JSONObject.toBean(JSONObject.fromObject(data), PlanListDto.class);
		return planService.savePlan(dto, date);
	}
	
	@ResponseBody
	@RequestMapping(value = "findProjectName")
	@SecureValid(code = "05002", desc = "获取项目名称", type = MethodType.FIND)
	public JSONReturn findProjectName(HttpSession httpSession) {
		return planService.findProjectName();
	}
}
