package com.empl.mgr.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.empl.mgr.constant.PageConstant;
import com.empl.mgr.dao.support.AbstractDao;

import com.empl.mgr.dto.SpecialListDto;
import com.empl.mgr.model.TeSpecial;;

@Repository
public class SpecialDao extends AbstractDao<TeSpecial> {

	@Override
	public Class<TeSpecial> getEntityClass() {
		// TODO Auto-generated method stub
		return TeSpecial.class;
	}

	@SuppressWarnings("unchecked")
	public List<SpecialListDto> findSpecialTable(String addTime, String state) {
		StringBuffer query = new StringBuffer();
		query.append(
				"select new com.empl.mgr.dto.SpecialListDto(MONTH(addTime), SUM(spec.qdTime+spec.sjTime+spec.hdTime), spec.area) FROM TeSpecial spec where 1=1");
		// query.append(StringUtils.isNotBlank(addTime) ? " and addTime = '" +
		// addTime + "'" : "");
		// query.append(StringUtils.isNotBlank(state) ? " and state = '" + state
		// + "'" : "");
		query.append(" and YEAR(addTime) = '2018'");
		query.append(" and state = '2'");
		query.append("GROUP BY area, MONTH(addTime) order by MONTH(addTime)");
		return findSession().createQuery(query.toString()).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SpecialListDto> findSpecialList(String searchValue, String area, String state, String strTime,
			String endTime) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.SpecialListDto");
		query.append(
				"(spec.id, spec.specialName, spec.editors, spec.area, spec.addTime, spec.startTime,spec.state,spec.sj,spec.qd,spec.hd,spec.sjTime,spec.qdTime,spec.hdTime,spec.completeTime) ");
		query.append("from TeSpecial spec where 1=1 ");
		query.append(StringUtils.isNotBlank(searchValue) ? " and specialName like '%" + searchValue + "%'" : "");
		query.append(StringUtils.isNotBlank(area) ? " and area = '" + area + "'" : "");
		query.append(StringUtils.isNotBlank(state) ? " and state = " + state + "" : " and state != 3 ");
		query.append(StringUtils.isNotBlank(strTime) ? " and startTime > '" + strTime + "'" : "");
		query.append(StringUtils.isNotBlank(endTime) ? " and startTime < '" + endTime + "'" : "");
		query.append("order by id desc");
		return findSession().createQuery(query.toString()).list();
	}

	@SuppressWarnings("unchecked")
	public List<SpecialListDto> findSpecialList(int page, String searchValue, String area, String state, String strTime,
			String endTime) {
		// TODO Auto-generated method stub
		StringBuffer query = new StringBuffer();
		query.append("select new com.empl.mgr.dto.SpecialListDto");
		query.append(
				"(spec.id, spec.specialName, spec.editors, spec.area, spec.addTime, spec.startTime,spec.state,spec.sj,spec.qd,spec.hd,spec.sjTime,spec.qdTime,spec.hdTime,spec.completeTime) ");
		query.append("from TeSpecial spec where 1=1 ");
		query.append(StringUtils.isNotBlank(searchValue) ? " and specialName like '%" + searchValue + "%'" : "");
		query.append(StringUtils.isNotBlank(area) ? " and area = '" + area + "'" : "");
		query.append(StringUtils.isNotBlank(state) ? " and state = " + state + "" : " and state != 3 ");
		query.append(StringUtils.isNotBlank(strTime) ? " and startTime > '" + strTime + "'" : "");
		query.append(StringUtils.isNotBlank(endTime) ? " and startTime < '" + endTime + "'" : "");
		query.append("order by id desc");
		return findSession().createQuery(query.toString()).setFirstResult((page - 1) * PageConstant.PAGE_LIST)
				.setMaxResults(PageConstant.PAGE_LIST).list();
	}

	public int findSpecialCount(String searchValue, String area, String state, String strTime, String endTime) {
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from TeSpecial where 1=1");
		query.append(StringUtils.isNotBlank(searchValue) ? " and specialName like '%" + searchValue + "%'" : "");
		query.append(StringUtils.isNotBlank(area) ? " and area = '" + area + "'" : "");
		query.append(StringUtils.isNotBlank(state) ? " and state = " + state + "" : " and state != 3 ");
		query.append(StringUtils.isNotBlank(strTime) ? " and startTime > '" + strTime + "'" : "");
		query.append(StringUtils.isNotBlank(endTime) ? " and startTime < '" + endTime + "'" : "");
		query.append("order by id desc");
		return Integer.parseInt(findSession().createQuery(query.toString()).uniqueResult().toString());
	}

}
