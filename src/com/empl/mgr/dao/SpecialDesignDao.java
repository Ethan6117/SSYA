package com.empl.mgr.dao;

import org.springframework.stereotype.Repository;

import com.empl.mgr.dao.support.AbstractDao;
import com.empl.mgr.model.TeSpecialDesign;

@Repository
public class SpecialDesignDao extends AbstractDao<TeSpecialDesign> {

	@Override
	public Class<TeSpecialDesign> getEntityClass() {
		// TODO Auto-generated method stub
		return TeSpecialDesign.class;
	}

	/*@SuppressWarnings("unchecked")
	public List<SpecialListDto> findSpecialList(int page, String searchValue,String area,String state,String strTime,String endTime) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.SpecialListDto");
		query.append("(spec.id, spec.specialName, spec.editors, spec.area, spec.addTime, spec.startTime,spec.state,spec.sj,spec.qd,spec.hd,spec.sjTime,spec.qdTime,spec.hdTime,spec.completeTime) ");
		query.append("from TeSpecial spec where 1=1 ");
		query.append(StringUtils.isNotBlank(searchValue) ? " and specialName like '%" + searchValue + "%'" : "");
		query.append(StringUtils.isNotBlank(area) ? " and area = '" + area + "'" : "");
		query.append(StringUtils.isNotBlank(state) ? " and state = " + state + "" : " and state != 3 ");
		query.append(StringUtils.isNotBlank(strTime)?" and startTime > '"+strTime+"'":"");
		query.append(StringUtils.isNotBlank(endTime)?" and startTime < '"+endTime+"'":"");
		query.append("order by id desc");
		return findSession().createQuery(query.toString()).setFirstResult((page - 1) * PageConstant.PAGE_LIST)
				.setMaxResults(PageConstant.PAGE_LIST).list();
	}

	public int findSpecialCount(String searchValue, String area,String state,String strTime,String endTime) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from TeSpecial where 1=1");
		query.append(StringUtils.isNotBlank(searchValue) ? " and specialName like '%" + searchValue + "%'" : "");
		query.append(StringUtils.isNotBlank(area) ? " and area = '" + area + "'" : "");
		query.append(StringUtils.isNotBlank(state) ? " and state = " + state + "" : " and state != 3 ");
		query.append(StringUtils.isNotBlank(strTime)?" and startTime > '"+strTime+"'":"");
		query.append(StringUtils.isNotBlank(endTime)?" and startTime < '"+endTime+"'":"");
		query.append("order by id desc");
		return Integer.parseInt(findSession().createQuery(query.toString()).uniqueResult().toString());
	}*/

}
