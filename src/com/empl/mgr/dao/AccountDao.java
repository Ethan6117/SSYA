package com.empl.mgr.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.empl.mgr.constant.AccountDeleteState;
import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.dto.AccountListDto;
import com.empl.mgr.model.TeAccount;

@Repository
public class AccountDao extends AbstractDao<TeAccount> {

	@Override
	public Class<TeAccount> getEntityClass() {
		// TODO Auto-generated method stub
		return TeAccount.class;
	}

	// long acctId, String acctName, String acctNickname, Date createTime, String creator

	@SuppressWarnings("unchecked")
	public List<AccountListDto> findAccountList(int page, String val) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.AccountListDto ");
		query.append("(te.acctId, te.acctName, te.acctNickname,te.email,te.mobile,te.telephone,te.department,te.position, te.createTime, te.creator, te.acctSuper) ");
		query.append("from TeAccount te where te.acctDeleteState = ? ");
		query.append(StringUtils.isEmpty(val) ? "" : " and (te.acctNickname like '%" + val
				+ "%' or te.acctName like '%" + val + "%')");
		query.append("order by te.acctId desc");
		return findSession().createQuery(query.toString()).setBoolean(0, AccountDeleteState.NO_DELETE)
				.setFirstResult((page - 1) * PageConstant.PAGE_LIST).setMaxResults(PageConstant.PAGE_LIST).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountListDto> findParticipantsList() {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.AccountListDto ");
		query.append("(te.acctNickname) ");
		query.append("from TeAccount te where te.acctDeleteState = ? and te.department = 2");
		query.append("order by te.acctId desc");
		return findSession().createQuery(query.toString()).setBoolean(0, AccountDeleteState.NO_DELETE).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountListDto> findManagerList() {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.AccountListDto(c.acctNickname) "
				+ "from TeRole a,TeAccountRole b,TeAccount c "
				+ "where c.acctDeleteState = ? and a.roleId=25 and a.roleLabel=b.roleLabel and b.acctName=c.acctName "
				+ "order by c.acctId desc");
		return findSession().createQuery(query.toString()).setBoolean(0, AccountDeleteState.NO_DELETE).list();
	}

	public int findAccountPage(String val) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select count(te.acctId) from TeAccount te where te.acctDeleteState = ? ");
		query.append(StringUtils.isEmpty(val) ? "" : " and (te.acctNickname like '%" + val
				+ "%' or te.acctName like '%" + val + "%')");
		return Integer.parseInt(findSession().createQuery(query.toString()).setBoolean(0, AccountDeleteState.NO_DELETE)
				.uniqueResult().toString());
	}
}
