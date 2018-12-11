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
import com.empl.mgr.service.AccountService;
import com.empl.mgr.support.JSONReturn;

@Scope
@Controller
@RequestMapping(value = "account")
public class AccountController extends AbstractController {

	@Autowired
	private AccountService accountService;

	@ResponseBody
	@RequestMapping(value = "findAccountInfo")
	public JSONReturn findAccountInfo(HttpSession httpSession) {
		return accountService.findAccountInfo(acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "addAccount")
	@SecureValid(code = "04001", desc = "添加账户信息", type = MethodType.ADD)
	public JSONReturn addAccount(@RequestParam String user, @RequestParam String nick, @RequestParam String pass,
			@RequestParam String email,@RequestParam String mobile,@RequestParam String telephone,@RequestParam String department,
			@RequestParam String position,
			HttpSession httpSession) {
		return accountService.addAccount(user, nick, pass,email,mobile,telephone,department,position, acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "findAccountList")
	@SecureValid(code = "04001", desc = "获取账户列表", type = MethodType.FIND)
	public JSONReturn findAccountList(@RequestParam int page, @RequestParam String searchValue, HttpSession httpSession) {
		return accountService.findAccountList(page, searchValue, acctName(httpSession));
	}
	
	@ResponseBody
	@RequestMapping(value = "findParticipantsList")
	public JSONReturn findParticipantsList(HttpSession httpSession) {
		return accountService.findParticipantsList();
	}
	
	@ResponseBody
	@RequestMapping(value = "findManagerList")
	public JSONReturn findManagerList(HttpSession httpSession) {
		return accountService.findManagerList();
	}

	@ResponseBody
	@RequestMapping(value = "findAccountPage")
	@SecureValid(code = "04001", desc = "获取账户列表分页", type = MethodType.FIND)
	public JSONReturn findAccountPage(@RequestParam int page, @RequestParam String searchValue) {
		return accountService.findAccountPage(page, searchValue);
	}

	@ResponseBody
	@RequestMapping(value = "delAccount")
	@SecureValid(code = "04001", desc = "删除账户信息", type = MethodType.DELETE)
	public JSONReturn delAccount(@RequestParam long id, HttpSession httpSession) {
		return accountService.delAccount(id, acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "initPassword")
	@SecureValid(code = "04001", desc = "重置密码", type = MethodType.MODIFY)
	public JSONReturn initPassword(@RequestParam long id, HttpSession httpSession) {
		return accountService.initPassword(id, acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "modifyNickname")
	@SecureValid(code = {"04001","06001"}, desc = "修改昵称", type = MethodType.MODIFY)
	public JSONReturn modifyNickname(@RequestParam long id, @RequestParam String nickname,
			@RequestParam String email,@RequestParam String mobile,@RequestParam String telephone,
			@RequestParam String department,@RequestParam String position,HttpSession httpSession) {
		return accountService.modifyNickname(id, nickname, email,mobile,telephone,department,position,acctName(httpSession));
	}

	@ResponseBody
	@RequestMapping(value = "findAccountById")
	@SecureValid(code = "04001", desc = "修改昵称", type = MethodType.FIND)
	public JSONReturn findAccountById(@RequestParam long id) {
		return accountService.findAccountById(id);
	}

	@ResponseBody
	@RequestMapping(value = "findRole")
	@SecureValid(code = "04001", desc = "获取所有角色", type = MethodType.MODIFY)
	public JSONReturn findRole(@RequestParam String acctName) {
		return accountService.findRole(acctName);
	}

	@ResponseBody
	@RequestMapping(value = "addAccountRole")
	@SecureValid(code = "04001", desc = "修改角色与帐号之间的关联", type = MethodType.MODIFY)
	public JSONReturn addAccountRole(@RequestParam long id, @RequestParam String account, @RequestParam boolean add,
			HttpSession httpSession) {
		return accountService.addAccountRole(id, account, add, acctName(httpSession));
	}
	
	@ResponseBody
	@RequestMapping(value = "findAccountByPosId")
	@SecureValid(code = { "04001", "04002" }, desc = "根据职位ID,获取员工信息", type = MethodType.FIND)
	public JSONReturn findAccountByPosId(@RequestParam long posId) {
		return accountService.findAccountByPosId(posId);
	}
	
	@ResponseBody
	@RequestMapping(value = "findAccountInPosId")
	@SecureValid(code = { "04001", "04002" }, desc = "根据职位ID,获取员工信息", type = MethodType.FIND)
	public JSONReturn findAccountInPosId(@RequestParam String posIds) {
		return accountService.findAccountByPosId(posIds);
	}
}
