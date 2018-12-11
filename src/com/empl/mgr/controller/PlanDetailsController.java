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
import com.empl.mgr.service.PlanDetailsService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "planDetails")
public class PlanDetailsController extends AbstractController {

	@Autowired
	private PlanDetailsService planDetailsService;

	@ResponseBody
	@RequestMapping(value = "findPlanDetailsList")
	@SecureValid(code = "05003", desc = "获取详细计划列表", type = MethodType.FIND)
	public JSONReturn findPlanDetailsList(@RequestParam int page, @RequestParam String weekOfYear, HttpSession httpSession) {
		return planDetailsService.findPlanDetailsList(page, weekOfYear);
	}

	@ResponseBody
	@RequestMapping(value = "findPlanDetailsCount")
	@SecureValid(code = "05003", desc = "获取详细计划列表分页", type = MethodType.FIND)
	public JSONReturn findPlanDetailsCount(@RequestParam int page, @RequestParam String weekOfYear) {
		return planDetailsService.findPlanDetailsCount(page, weekOfYear);
	}
	
	/*@ResponseBody
	@RequestMapping(value = "findAccountByRoleId")
	@SecureValid(code = "05001", desc = "根据角色ID,获取账号信息", type = MethodType.FIND)
	public JSONReturn findAccountByRoleId(@RequestParam long roleId) {
		return accountService.findAcctNameByRoleId(roleId);
	}

	@ResponseBody
	@RequestMapping(value = "findProjectInfo")
	@SecureValid(code = "05001", desc = "根据ID号,获取项目信息", type = MethodType.FIND)
	public JSONReturn findProjectById(@RequestParam Integer proId) {
		return projectService.findProjectById(proId); 
	}

	@ResponseBody
	@RequestMapping(value = "modifyProjectInfo")
	@SecureValid(code = "05001", desc = "修改项目信息", type = MethodType.MODIFY)
	public JSONReturn modifyProjectInfo(@RequestParam String data, HttpSession httpSession) {
		ProjectListDto dto = (ProjectListDto) JSONObject
				.toBean(JSONObject.fromObject(data), ProjectListDto.class);
		return projectService.modifyProjectInfo(dto, acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "saveProjectInfo")
	@SecureValid(code = "05001", desc = "添加项目", type = MethodType.ADD)
	public JSONReturn addProject(@RequestParam String data, HttpSession httpSession) {
		ProjectListDto dto = (ProjectListDto) JSONObject.toBean(JSONObject.fromObject(data), ProjectListDto.class);
		return projectService.saveProjectInfo(dto, acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "deleteProject")
	@SecureValid(code = "05001", desc = "删除项目信息", type = MethodType.DELETE)
	public JSONReturn deleteProject(@RequestParam Integer proId, HttpSession httpSession) {
		return projectService.deleteProject(proId);
	}*/
}
