package com.empl.mgr.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.dto.PlanDetailsListDto;
import com.empl.mgr.model.TePlanDetails;

@Repository
public class PlanDetailsDao extends AbstractDao<TePlanDetails> {

	@Override
	public Class<TePlanDetails> getEntityClass() {
		// TODO Auto-generated method stub
		return TePlanDetails.class;
	}
	
	/*public void deleteProject(Integer proId){
		StringBuffer query = new StringBuffer();
		query.append("UPDATE TeProject pro set pro.deleteTag = 1 WHERE id = " + proId);
		findSession().createQuery(query.toString()).executeUpdate();
	}*/

	@SuppressWarnings("unchecked")
	public List<PlanDetailsListDto> findPlanDetailsList(int page, String weekOfYear) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.PlanDetailsListDto");
		query.append("(pd.id, pd.projectName, pd.districts, pd.task, pd.description, pd.planOrAdjust, pd.staff, pd.state, pd.reason, pd.weekday, pd.time) ");
		query.append("from TePlanDetails pd where 1=1 and deleteTag = 0 ");
		query.append(StringUtils.isNotBlank(weekOfYear) ? "and weekOfYear = " + weekOfYear + " " : "");
		query.append("order by id desc");
		return findSession().createQuery(query.toString()).setFirstResult((page - 1) * PageConstant.PAGE_LIST)
				.setMaxResults(PageConstant.PAGE_LIST).list();
	}

	public int findPlanDetailsCount(String weekOfYear) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from TePlanDetails where 1=1");
		query.append(StringUtils.isNotBlank(weekOfYear) ? " and weekOfYear = " + weekOfYear : "");
		return Integer.parseInt(findSession().createQuery(query.toString()).uniqueResult().toString());
	}
}
