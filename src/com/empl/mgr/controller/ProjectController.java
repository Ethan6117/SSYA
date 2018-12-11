package com.empl.mgr.controller;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.empl.mgr.annotation.SecureValid;
import com.empl.mgr.constant.MethodType;
import com.empl.mgr.controller.support.AbstractController;
import com.empl.mgr.dto.ProjectListDto;
import com.empl.mgr.service.AccountService;
import com.empl.mgr.service.ProjectService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "project")
public class ProjectController extends AbstractController {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private AccountService accountService;

	@ResponseBody
	@RequestMapping(value = "findProjectList")
	@SecureValid(code = "05001", desc = "获取项目列表", type = MethodType.FIND)
	public JSONReturn findProjectList(@RequestParam int page, @RequestParam String searchValue,@RequestParam String state,
			HttpSession httpSession) {
		return projectService.findProjectList(page, searchValue,state);
	}

	@ResponseBody
	@RequestMapping(value = "findProjectCount")
	@SecureValid(code = "05001", desc = "获取项目分页", type = MethodType.FIND)
	public JSONReturn findProjectCount(@RequestParam int page, @RequestParam String searchValue,@RequestParam String state) {
		return projectService.findProjectCount(page, searchValue,state);
	}
	
	@ResponseBody
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
	}

	/*@ResponseBody
	@RequestMapping(value = "findDeptEmplList")
	@SecureValid(code = "03001", desc = "获取部门员工下拉框列表信息", type = MethodType.FIND)
	public JSONReturn findDeptEmplList(@RequestParam long deptId, HttpSession httpSession) {
		return departmentService.findDeptEmplList(deptId, acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "setPrincipal")
	@SecureValid(code = "03001", desc = "设置部门经理", type = MethodType.MODIFY)
	public JSONReturn setPrincipal(@RequestParam long deptId, @RequestParam long emplId, HttpSession httpSession) {
		return departmentService.setPrincipal(deptId, emplId, acctName(httpSession));
	}*/

}
