package com.empl.mgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empl.mgr.constant.AccountDeleteState;
import com.empl.mgr.constant.AccountInitPassword;
import com.empl.mgr.constant.AccountState;
import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.constant.SessionKey;
import com.empl.mgr.constant.TimeFormatConstant;
import com.empl.mgr.dao.AccountDao;
import com.empl.mgr.dao.AccountRoleDao;
import com.empl.mgr.dao.DepartmentDao;
import com.empl.mgr.dao.PositionDao;
import com.empl.mgr.dao.RoleDao;
import com.empl.mgr.dto.AccountListDto;
import com.empl.mgr.dto.RoleDto;
import com.empl.mgr.field.TeAccountField;
import com.empl.mgr.field.TeRoleField;
import com.empl.mgr.model.TeAccount;
import com.empl.mgr.model.TeAccountRole;
import com.empl.mgr.model.TeRole;
import com.empl.mgr.service.AccountService;
import com.empl.mgr.support.JSONReturn;
import com.empl.mgr.utils.CompareUtil;
import com.empl.mgr.utils.DateTimeUtil;
import com.empl.mgr.utils.EncryptUtil;
import com.empl.mgr.utils.PageUtils;

@Scope
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AccountRoleDao accountRoleDao;
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private PositionDao positionDao;

	public JSONReturn login(String name, String pass, HttpServletRequest request) {
		// TODO Auto-generated method stub
		TeAccount account = accountDao.findUniqueByProperty(TeAccountField.ACCT_NAME, name);
		if (CompareUtil.isEmpty(account) || account.getAcctDeleteState())
			return JSONReturn.buildFailure("登录失败, 用户不存在");
		if (!account.getAcctPassword().equals(EncryptUtil.encodeMD5String(pass)))
			return JSONReturn.buildFailure("登录失败, 用户名或密码错误!");
		request.getSession().setAttribute(SessionKey.MODULEACCTNAME, account.getAcctName());
		return JSONReturn.buildSuccess("登录成功!");
	}

	public JSONReturn findAccountInfo(String acctName) {
		// TODO Auto-generated method stub
		TeAccount account = accountDao.findUniqueByProperty(TeAccountField.ACCT_NAME, acctName);
		if (CompareUtil.isEmpty(account) || account.getAcctDeleteState())
			return JSONReturn.buildFailure("未获取到账户信息");
		account.setAcctPassword("");
		return JSONReturn.buildSuccess(account);
	}
	
	

	public JSONReturn exit(HttpSession httpSession) {
		// TODO Auto-generated method stub
		String acctName = (String) httpSession.getAttribute(SessionKey.MODULEACCTNAME);
		if (StringUtils.isEmpty(acctName))
			return JSONReturn.buildFailure("操作失败!");
		httpSession.removeAttribute(SessionKey.MODULEACCTNAME);
		return JSONReturn.buildSuccessWithEmptyBody();
	}

	@Transactional
	public JSONReturn addAccount(String user, String nick, String pass, String email,String mobile,String telephone,String department,String position, String acctName) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(user) || StringUtils.isEmpty(nick) || StringUtils.isEmpty(pass))
			return JSONReturn.buildFailure("添加失败!");
		TeAccount account = accountDao.findUniqueByProperty(TeAccountField.ACCT_NAME, user);
		if (CompareUtil.isNotEmpty(account))
			return JSONReturn.buildFailure("添加失败, 用户名重复!");
		account = new TeAccount();
		account.setAcctName(user);
		account.setAcctNickname(nick);
		account.setAcctPassword(EncryptUtil.encodeMD5String(pass));
		account.setEmail(email);
		account.setMobile(mobile);
		account.setTelephone(telephone);
		account.setDepartment(StringUtils.isNotBlank(department)?Long.valueOf(department):0);
		account.setPosition(StringUtils.isNotBlank(position)?Long.valueOf(position):0);
		account.setAcctState(AccountState.ACCOUNT_GENERAL);
		account.setAcctSuper(false);
		account.setCreateTime(DateTimeUtil.getCurrentTime());
		account.setCreator(acctName);
		account.setAcctDeleteState(AccountDeleteState.NO_DELETE);
		accountDao.save(account);
		return JSONReturn.buildSuccess("添加成功!");
	}

	public JSONReturn findAccountList(int page, String searchValue, String acctName) {
		// TODO Auto-generated method stub
		List<AccountListDto> dtoList = accountDao.findAccountList(page, searchValue);
		if (CollectionUtils.isEmpty(dtoList))
			return JSONReturn.buildFailure("未获取到数据!");
		for (AccountListDto dto : dtoList)
			dto.setTime(DateTimeUtil.conversionTime(dto.getDate(), TimeFormatConstant.YYYY_MM_DD));
		return JSONReturn.buildSuccess(dtoList);
	}

	public JSONReturn findParticipantsList() {
		// TODO Auto-generated method stub
		List<AccountListDto> dtoList = accountDao.findParticipantsList();
		if (CollectionUtils.isEmpty(dtoList))
			return JSONReturn.buildFailure("未获取到数据!");
		return JSONReturn.buildSuccess(dtoList);
	}
	
	public JSONReturn findManagerList() {
		// TODO Auto-generated method stub
		List<AccountListDto> dtoList = accountDao.findManagerList();
		if (CollectionUtils.isEmpty(dtoList))
			return JSONReturn.buildFailure("未获取到数据!");
		return JSONReturn.buildSuccess(dtoList);
	}
	
	public JSONReturn findAccountPage(int page, String searchValue) {
		// TODO Auto-generated method stub
		return JSONReturn.buildSuccess(PageUtils.calculatePage(page, accountDao.findAccountPage(searchValue),
				PageConstant.PAGE_LIST));
	}

	@Transactional
	public JSONReturn delAccount(long id, String acctName) {
		// TODO Auto-generated method stub
		TeAccount account = accountDao.findUniqueByProperty(TeAccountField.ACCT_ID, id);
		if (CompareUtil.isEmpty(account) || account.getAcctDeleteState())
			return JSONReturn.buildFailure("操作失败, 源数据不存在!");
		if (account.getAcctSuper())
			return JSONReturn.buildFailure("非法操作!");
		// account.setAcctDeleteState(AccountDeleteState.IS_DELETE); // 标识账户信息为删已经
		accountDao.delete(account); // 直接删除帐户信息 
		return JSONReturn.buildSuccess("删除成功!");
	}

	@Transactional
	public JSONReturn initPassword(long id, String acctName) {
		// TODO Auto-generated method stub
		TeAccount account = accountDao.findUniqueByProperty(TeAccountField.ACCT_ID, id);
		if (CompareUtil.isEmpty(account) || account.getAcctDeleteState())
			return JSONReturn.buildFailure("操作失败, 源数据不存在!");
		account.setAcctPassword(EncryptUtil.encodeMD5String(AccountInitPassword.ACCOUNT_INIT_PASSWORD));
		return JSONReturn.buildSuccess("操作成功, 初始化密码为" + AccountInitPassword.ACCOUNT_INIT_PASSWORD + "!");
	}

	@Transactional
	public JSONReturn modifyNickname(long id, String nickname,String email,String mobile,String telephone,
			String department,String position,  String acctName) {
		// TODO Auto-generated method stub
		TeAccount account = accountDao.findUniqueByProperty(TeAccountField.ACCT_ID, id);
		if (CompareUtil.isEmpty(account) || account.getAcctDeleteState())
			return JSONReturn.buildFailure("操作失败, 源数据不存在!");
		account.setAcctNickname(nickname);
		account.setEmail(email);
		account.setMobile(mobile);
		account.setTelephone(telephone);
		if (StringUtils.isNotEmpty(department)) {
			account.setDepartment(Long.valueOf(department));
		}
		if (StringUtils.isNotEmpty(position)) {
			account.setPosition(Long.valueOf(position));
		}
		return JSONReturn.buildSuccess("修改成功!");
	}

	public JSONReturn findAccountById(long id) {
		// TODO Auto-generated method stub
		TeAccount acct = accountDao.findUniqueByProperty(TeAccountField.ACCT_ID, id);
		if (CompareUtil.isEmpty(acct) || acct.getAcctDeleteState())
			return JSONReturn.buildFailure("操作失败, 源数据不存在");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("info", new AccountListDto(acct.getAcctId(), acct.getAcctName(), acct.getAcctNickname(),
				acct.getEmail(), acct.getMobile(), acct.getTelephone(), acct.getDepartment(), acct.getPosition(),
				acct.getCreateTime(), acct.getCreator(), acct.getAcctSuper()));
		map.put("departments", departmentDao.findAllDepartment());
		if (acct.getDepartment() > 0)
			map.put("positions", positionDao.findByDeptId(acct.getDepartment()));
		return JSONReturn.buildSuccess(map);
	}

	public JSONReturn findRole(String acctName) {
		// TODO Auto-generated method stub
		List<TeRole> roleList = roleDao.findAll();
		if (CollectionUtils.isEmpty(roleList))
			return JSONReturn.buildFailure("未获取到角色信息!");
		List<RoleDto> dtoList = new ArrayList<RoleDto>();
		for (TeRole ro : roleList) {
			RoleDto dto = new RoleDto();
			dto.setId(ro.getRoleId());
			dto.setRoleName(ro.getRoleName());
			dto.setOpt(CollectionUtils.isNotEmpty(accountRoleDao.findByAcctNameAndRoleLabel(acctName, ro.getRoleLabel())));
			dto.setAcctName(acctName);
			dtoList.add(dto);
		}
		return JSONReturn.buildSuccess(dtoList);
	}

	@Transactional
	public JSONReturn addAccountRole(long id, String account, boolean add, String acctName) {
		// TODO Auto-generated method stub
		TeRole teRole = roleDao.findUniqueByProperty(TeRoleField.ROLE_ID, id);
		if (CompareUtil.isEmpty(teRole))
			return JSONReturn.buildFailure("操作失败, 数据源不存在!");
		if (!add) {
			accountRoleDao.delByAcctNameAndRoleLabel(account, teRole.getRoleLabel());
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		if (CollectionUtils.isNotEmpty(accountRoleDao.findByAcctNameAndRoleLabel(account, teRole.getRoleLabel()))) {
			return JSONReturn.buildFailure("添加失败, 重复添加!");
		}
		accountRoleDao.save(new TeAccountRole(account, teRole.getRoleLabel()));
		return JSONReturn.buildSuccessWithEmptyBody();
	}

	public TeAccount findAccountByName(String userName) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(userName))
			return null;
		return accountDao.findUniqueByProperty(TeAccountField.ACCT_NAME, userName);
	}

	@Transactional
	public JSONReturn mdoifyPass(String oldpassword,String password, String acctName) {
		// TODO Auto-generated method stub
		TeAccount acct = accountDao.findUniqueByProperty(TeAccountField.ACCT_NAME, acctName);
		if (!EncryptUtil.encodeMD5String(oldpassword).equals(acct.getAcctPassword()))
			return JSONReturn.buildFailure("修改密码失败, 原始密码不正确!");
		if (CompareUtil.isEmpty(acct))
			return JSONReturn.buildFailure("修改密码失败, 用户不存在!");
		acct.setAcctPassword(EncryptUtil.encodeMD5String(password));
		return JSONReturn.buildSuccess("密码修改成功!");
	}
	
	public JSONReturn findAccountByPosId(long posId) {
		// TODO Auto-generated method stub
		List<TeAccount> acctList = accountDao.findByProperty(TeAccountField.ACCT_POSITION, posId);
		if (CollectionUtils.isEmpty(acctList))
			return JSONReturn.buildFailure("没有获取相关记录信息!!!");
		return JSONReturn.buildSuccess(acctList);
	}
	
	public JSONReturn findAccountByPosId(String posIds) {
		// TODO Auto-generated method stub
		String[] str=posIds.split(",");
		Long[] num = new Long[str.length];
        for (int idx = 0; idx < str.length; idx++ ) {
            num[idx] = Long.parseLong(str[idx]);
        }
		List<TeAccount> acctList = accountDao.findByPropertyIn(TeAccountField.ACCT_POSITION, num);
		if (CollectionUtils.isEmpty(acctList))
			return JSONReturn.buildFailure("没有获取相关记录信息!!!");
		return JSONReturn.buildSuccess(acctList);
	}

	public JSONReturn findAcctNameByRoleId(long roleId) {
		List<String> accountRoles=accountRoleDao.findAcctNameByRoleId(roleId);
		if (CollectionUtils.isEmpty(accountRoles))
			return JSONReturn.buildFailure("没有获取相关记录信息!!!");
		return JSONReturn.buildSuccess(accountRoles);
	}

	@Transactional
	public void saveAccount(TeAccount account1) {
		TeAccount account = accountDao.findUniqueByProperty(TeAccountField.ACCT_ID, account1.getAcctId());
		account.setValidataCode(account1.getValidataCode());
		account.setOutDate(account1.getOutDate());
		account.setAcctPassword(account1.getAcctPassword());
	}
}
