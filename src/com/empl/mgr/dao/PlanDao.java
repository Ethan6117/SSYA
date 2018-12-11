package com.empl.mgr.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.dto.PlanListDto;
import com.empl.mgr.dto.ProjectListDto;
import com.empl.mgr.model.TePlan;

@Repository
public class PlanDao extends AbstractDao<TePlan> {

	@Override
	public Class<TePlan> getEntityClass() {
		// TODO Auto-generated method stub
		return TePlan.class;
	}

	@SuppressWarnings("unchecked")
	public List<PlanListDto> findPlanList(int page, String date) {
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.PlanListDto"
				+ "(p.id, p.projectName, p.manager, p.participants, p.lastWeekPercent, p.percent, p.state, p.progress, p.estimatedTime, p.completeTime, p.date) "
				+ "from TePlan p where 1=1 ");
		query.append(StringUtils.isNotBlank(date) ? " and p.date = "+date+" ": "");
		query.append("and p.deleteTag = 0 ");
		query.append("order by p.state");
		return findSession().createQuery(query.toString()).setFirstResult((page - 1) * PageConstant.PAGE_LIST)
				.setMaxResults(PageConstant.PAGE_LIST).list();
	}
	
	public int findPlanCount(String date) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*)");
		query.append("from TePlan where 1=1");
		query.append(StringUtils.isNotBlank(date) ? " and date = '" + date + "'" : "");
		return Integer.parseInt(findSession().createQuery(query.toString()).uniqueResult().toString());
	}
	
	
	public PlanListDto findPlanById(Integer pId) {
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.PlanListDto"
				+ "(p.id, p.projectName, p.manager, p.participants, p.lastWeekPercent, p.percent, p.state, p.progress, p.estimatedTime, p.completeTime, p.date) "
				+ "from TePlan p ");
		query.append("where p.id = "+ pId +" ");
		query.append("and p.deleteTag = 0");
		return (PlanListDto)findSession().createQuery(query.toString()).uniqueResult();
	}
	
	public void deletePlan(Integer pId){
		StringBuffer query = new StringBuffer();
		query.append("UPDATE TePlan p set p.deleteTag = 1 WHERE id = " + pId);
		findSession().createQuery(query.toString()).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectListDto> findProjectName() {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.ProjectListDto");
		query.append("(pro.projectName)");
		query.append("from TeProject pro where deleteTag = 0");
		query.append("order by id desc");
		return findSession().createQuery(query.toString()).list();
	}
}
