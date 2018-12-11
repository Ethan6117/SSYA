package com.empl.mgr.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.dto.ProjectListDto;

import com.empl.mgr.model.TeProject;

@Repository
public class ProjectDao extends AbstractDao<TeProject> {

	@Override
	public Class<TeProject> getEntityClass() {
		// TODO Auto-generated method stub
		return TeProject.class;
	}
	
	public void deleteProject(Integer proId){
		StringBuffer query = new StringBuffer();
		query.append("UPDATE TeProject pro set pro.deleteTag = 1 WHERE id = " + proId);
		findSession().createQuery(query.toString()).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<ProjectListDto> findProjectList(int page, String searchValue, String state) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.ProjectListDto");
		query.append("(pro.id, pro.state, pro.projectName, pro.client, pro.partner, pro.linkman, pro.phone, pro.manager, pro.participants, pro.domainName, pro.serverAndLogin, pro.path, pro.summary, pro.boss)");
		query.append("from TeProject pro where 1=1 and deleteTag = 0");
		query.append(StringUtils.isNotBlank(searchValue) ? " and projectName like '%" + searchValue + "%'" : "");
		query.append(StringUtils.isNotBlank(state) ? " and state = " + state + "" : "");
		query.append("order by id desc");
		return findSession().createQuery(query.toString()).setFirstResult((page - 1) * PageConstant.PAGE_LIST)
				.setMaxResults(PageConstant.PAGE_LIST).list();
	}

	public int findProjectCount(String searchValue, String state) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from TeProject where 1=1 and deleteTag = 0");
		query.append(StringUtils.isNotBlank(searchValue) ? " and projectName like '%" + searchValue + "%'" : "");
		query.append(StringUtils.isNotBlank(state) ? " and state = " + state + "" : "");
		return Integer.parseInt(findSession().createQuery(query.toString()).uniqueResult().toString());
	}

}
