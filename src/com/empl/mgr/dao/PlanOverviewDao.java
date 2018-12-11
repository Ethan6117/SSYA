package com.empl.mgr.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.dto.PlanOverviewListDto;
import com.empl.mgr.model.TePlanOverview;

@Repository
public class PlanOverviewDao extends AbstractDao<TePlanOverview> {
	@Override
	public Class<TePlanOverview> getEntityClass() {
		// TODO Auto-generated method stub
		return TePlanOverview.class;
	}

	@SuppressWarnings("unchecked")
	public List<PlanOverviewListDto> findPlanOverviewList(int page,String year) {
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.PlanOverviewListDto");
		query.append("(po.id, po.year, po.weekOfYear, po.date, po.state)");
		query.append("from TePlanOverview po where 1=1");
		query.append(StringUtils.isNotBlank(year) ? " and year = '" + year + "'" : "");
		query.append("order by year desc, weekOfYear desc");
		return findSession().createQuery(query.toString()).setFirstResult((page - 1) * PageConstant.PAGE_LIST)
				.setMaxResults(PageConstant.PAGE_LIST).list();
	}
	
	public int findPlanOverviewCount(String year) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*)");
		query.append("from TePlanOverview where 1=1");
		query.append(StringUtils.isNotBlank(year) ? " and year = '" + year + "'" : "");
		return Integer.parseInt(findSession().createQuery(query.toString()).uniqueResult().toString());
	}
	
	public void updateState(int id,int state){
		StringBuffer query = new StringBuffer();
		query.append("update TePlanOverview po set po.state = "+ state +" where id = " + id);
		findSession().createQuery(query.toString()).executeUpdate();
	}
}
