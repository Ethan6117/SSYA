package com.empl.mgr.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.empl.mgr.model.TeAccount;
import com.empl.mgr.support.JSONReturn;

public abstract interface AccountService {

	public abstract JSONReturn login(String name, String pass, HttpServletRequest request);

	public abstract JSONReturn findAccountInfo(String acctName);

	public abstract JSONReturn exit(HttpSession httpSession);

	public abstract JSONReturn addAccount(String user, String nick, String pass, String email,String mobile,String telephone,String department,String position, String acctName);

	public abstract JSONReturn findAccountList(int page, String searchValue, String acctName);
	
	public abstract JSONReturn findParticipantsList();
	
	public abstract JSONReturn findManagerList();

	public abstract JSONReturn findAccountPage(int page, String searchValue);

	public abstract JSONReturn delAccount(long id, String acctName);

	public abstract JSONReturn initPassword(long id, String acctName);

	public abstract JSONReturn modifyNickname(long id, String nickname,String email,String mobile,String telephone,String department,String position, String acctName);

	public abstract JSONReturn findAccountById(long id);

	public abstract JSONReturn addAccountRole(long id, String account, boolean add, String acctName);

	public abstract JSONReturn findRole(String acctName);

	public abstract TeAccount findAccountByName(String userName);

	public abstract JSONReturn mdoifyPass(String oldpassword,String password, String acctName);
	
	public abstract JSONReturn findAccountByPosId(long posId);

	public abstract JSONReturn findAccountByPosId(String posIds);

	public abstract JSONReturn findAcctNameByRoleId(long roleId);
	
	public void saveAccount(TeAccount account);
}
